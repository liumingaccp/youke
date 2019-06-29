package youke.service.fans.provider;

import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.github.pagehelper.PageInfo;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.stereotype.Service;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.model.TShopFans;
import youke.common.model.TShopFansImport;
import youke.common.model.vo.file.SerializeMultipartFile;
import youke.common.model.vo.param.ShopFansQueryVo;
import youke.common.model.vo.result.BuyerOrderVo;
import youke.common.model.vo.result.BuyerVo;
import youke.common.model.vo.result.ShopFansImportVo;
import youke.common.model.vo.result.ShopFansVo;
import youke.common.oss.FileUpOrDwUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.HttpConnUtil;
import youke.common.utils.StringUtil;
import youke.common.utils.excel.EasyExcel;
import youke.facade.fans.provider.IShopFansService;
import youke.service.fans.biz.IFansShopBiz;
import youke.service.fans.queue.message.FansImportMessage;
import youke.service.fans.queue.producer.FansImportQueue;
import youke.service.fans.vo.ShopFansExcelVo;

import javax.annotation.Resource;
import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class ShopFansService implements IShopFansService {

    @Resource
    private IFansShopBiz fansShopBiz;
    @Resource
    private FansImportQueue fansImportQueue;

    public PageInfo<ShopFansVo> getShopfansList(ShopFansQueryVo qo) {
        PageInfo<ShopFansVo> page = fansShopBiz.getList(qo);
        return page;
    }


    public void deleteTags(long fansId, String tags) {
        fansShopBiz.deleteTags(fansId, tags);
    }

    public void addTags(String fansIds, String tags, String youkeId) {
        if (fansIds == null) {
            throw new BusinessException("请选择需要添加标签的粉丝");
        }
        List<Long> ids = new ArrayList<Long>();
        try {
            String[] split = fansIds.split(",");
            if (split != null && split.length > 0) {
                for (String str : split) {
                    ids.add(Long.parseLong(str));
                }
            }
            if (ids.size() > 0) {
                for (Long id : ids) {
                    fansShopBiz.addTags(id, tags, youkeId);
                }
            }
        } catch (Exception e) {
            throw new BusinessException("传入了非法的粉丝id");
        }
    }

    public void saveImportFansFromFile(int shopId, SerializeMultipartFile sFile, String youkeId) {

        //调用上传接口
        String originalFilename = sFile.getOriginalFilename();
        String extensionName = FileUpOrDwUtil.getExtensionName(originalFilename);

        if (!Arrays.asList(ApiCodeConstant.IMPORT).contains(extensionName)) {
            return;
        }
        String url = null;
        File tempFile = null;
        String uuName = UUID.randomUUID().toString();
        int importID = 0;
        BufferedOutputStream bufferedOutput = null;
        FileOutputStream stream = null;
        try {
            tempFile = File.createTempFile(uuName, "." + extensionName);
            stream = new FileOutputStream(tempFile);
            bufferedOutput = new BufferedOutputStream(stream);
            //获取扩展名当做前缀
            bufferedOutput.write(sFile.getBytes());
            bufferedOutput.close();
            stream.close();

            url = FileUpOrDwUtil.uploadFileOrStream(tempFile, extensionName + "/" + tempFile.getName(), false);
            importID = fansShopBiz.saveImportFansFromFile(shopId, url, originalFilename, youkeId);

            //发送队列消息,异步处理
            fansImportQueue.send("fans.queue", JSON.toJSONString(new FansImportMessage(url, shopId, youkeId, importID)));
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        } finally {
            tempFile.delete();
        }

    }

    public void saveRemark(long fansId, String remark) {
        fansShopBiz.saveRemark(fansId, remark);
    }

    public PageInfo<ShopFansImportVo> getImportList(int page, int limit, String youkeId) {

        return fansShopBiz.getImportList(page, limit, youkeId);
    }

    public String getSyncTime() {
        //String date = (String) RedisSlaveUtil.get("");
        return DateUtil.getDateTime();
    }

    public void sync() {
        //todo 发送异步任务
    }

    public void doImportFans(String url, int shopId, String youkeId, int importId) {
        if (url == null) {
            return;
        }
        if (url.endsWith("csv")) {
            try {
                doCSV(url, shopId, youkeId, importId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (url.contains(".xls") || url.contains(".xlsx")) {
            try {
                doXls(url, shopId, youkeId, importId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public BuyerVo getFriendBuyerInfo(String friendId, String weixinId,String youkeId) {
        return fansShopBiz.getFriendBuyerInfo(friendId, weixinId,youkeId);
    }

    @Override
    public List<BuyerOrderVo> getShopOrderList(String friendId, String weixinId,String youkeId) {
        return fansShopBiz.getShopOrderList(friendId, weixinId,youkeId);
    }

    @Override
    public void bindBuyerName(String friendId, String buyerName, String weixinId,String youkeId) {
        fansShopBiz.saveBuyerName(friendId, buyerName, weixinId,youkeId);
    }

    @Override
    public void bindMobile(String friendId, String mobile, String weixinId) {
        fansShopBiz.saveFriendMobile(friendId,mobile,weixinId);
    }


    public void doCSV(String url, int shopId, String youkeId, int importId) throws IOException {

        File file = File.createTempFile(UUID.randomUUID().toString(), ".csv");
        String key = url.substring(ApiCodeConstant.AUTH_DOMAIN.length() + 1);
        InputStream inputStream = FileUpOrDwUtil.downloadIo(key);

        CsvReader csvReader = new CsvReader(inputStream, Charset.forName("GBK"));

        //表头文件
        FileUpOrDwUtil.downloadFile("csv/" + "模板头文件.csv", file);
        //写入临时文件
        CsvWriter csvWriter = new CsvWriter(new FileWriter(file), ',');
        //导入的失败数量
        int count = 0;
        List<String[]> csvInsertList = new ArrayList<String[]>(); //用来保存读取的数据

        List<String[]> csvList = new ArrayList<String[]>(); //用来保存读取的数据

        if (!csvReader.readHeaders()) {
            //修改
            TShopFansImport tShopFansImport = new TShopFansImport();
            tShopFansImport.setSuccessnum(0);
            tShopFansImport.setState(2);
            tShopFansImport.setFailcsvurl(url);
            tShopFansImport.setId(importId);
            fansShopBiz.updateImmport(tShopFansImport);
            return;
            //throw new BusinessException("数据文件格式错误,请检查文件是否存在表头");
        } else {
            int headerCount = csvReader.getHeaderCount();
            if (headerCount != ApiCodeConstant.IMPORT_FANS_CSV_HEADER_SIZE) {
                //修改
                TShopFansImport tShopFansImport = new TShopFansImport();
                tShopFansImport.setSuccessnum(0);
                tShopFansImport.setState(2);
                tShopFansImport.setFailcsvurl(url);
                tShopFansImport.setId(importId);
                fansShopBiz.updateImmport(tShopFansImport);
                return;
                //throw new BusinessException("数据文件格式错误,请检查文件是否是模板文件");
            }
        }

        //删除文件
        FileUpOrDwUtil.deleteFile(key);

        while (csvReader.readRecord()) {
            csvList.add(csvReader.getValues());
        }
        csvReader.close();

        for (int row = 0; row < csvList.size(); row++) {
            String[] strs = csvList.get(row);
            String nickname = strs[0];  //昵称 取得第row行第0列的数据
            String mobile = strs[1];    //手机号码
            String province = strs[2];  //省份
            String city = strs[3];      //城市
            String county = strs[4];    //县/区
            String street = strs[5];    //街道
            String fansname = strs[6];  //买家姓名
            String emaili = strs[7];    //邮箱,选填
            String hobby = strs[8];     //爱好,选填
            String sex = strs[9];       //性别
            String age = strs[10];      //年龄

            if (!StringUtil.hasLength(nickname)
                    || !StringUtil.hasLength(mobile)
                    || !StringUtil.hasLength(province)
                    || !StringUtil.hasLength(city)
                    || !StringUtil.hasLength(county)
                    || !StringUtil.hasLength(street)
                    || !StringUtil.hasLength(fansname)
                    ) {
                count++;
                csvInsertList.add(strs);
            } else {
                TShopFans fans = new TShopFans();
                fans.setNickname(nickname);
                fans.setMobile(mobile);
                fans.setProvince(province);
                fans.setCity(city);
                fans.setShopid(shopId);
                fans.setYoukeid(youkeId);
                if (!StringUtil.hasLength(sex)) {
                    fans.setSex(-1);
                } else {
                    fans.setSex(Integer.parseInt(sex));
                }
                fans.setTruename(fansname);
                fans.setComefrom(1);

                fans.setIntegral(0);
                fans.setExperience(0);
                fans.setLoginCount(0);
                Date date = new Date();
                fans.setRegtime(date);
                fans.setLasttime(date);
                fans.setState(0);
                fans.setDealtotal(0.0);
                fans.setAvgdealtotal(0.0);
                fans.setDealnum(0);

                int insert = fansShopBiz.add(fans);
                if (insert != 1) {
                    count++;
                    csvInsertList.add(strs);
                }
            }

        }

        //修改
        TShopFansImport tShopFansImport = new TShopFansImport();
        tShopFansImport.setSuccessnum(csvList.size() - count);
        tShopFansImport.setCompletetime(new Date());
        if (count != csvList.size()) {
            tShopFansImport.setState(1);
        }
        tShopFansImport.setId(importId);
        tShopFansImport.setFailnum(count);
        if (count > 0) {
            for (String[] contents : csvInsertList) {
                csvWriter.writeRecord(contents);
            }
            csvWriter.close();
            String oss_url = FileUpOrDwUtil.uploadFileStream(file, "csv/" + UUID.randomUUID() + ".csv");
            tShopFansImport.setFailcsvurl(oss_url);
            file.delete();
        }
        fansShopBiz.updateImmport(tShopFansImport);
    }

    public void doXls(String url, int shopId, String youkeId, int importId) throws IOException {
        if (url == null) {
            return;
        }
        InputStream fansInput = HttpConnUtil.getStreamFromNetByUrl(url);
        //粉丝数据表格
        EasyExcel fansSheet = null;
        InputStream tempInput = null;
        try {
            fansSheet = new EasyExcel(fansInput);
            fansSheet.setSheetName("Sheet1");
            List<ShopFansExcelVo> voList = fansSheet.parse(ShopFansExcelVo.class);
            List<ShopFansExcelVo> failList = new ArrayList<>();
            if (voList != null && voList.size() > 0) {
                for (ShopFansExcelVo vo : voList) {
                    String nickName = vo.getNickName();
                    boolean flag = false;
                    int sex = -1;
                    int age = 0;
                    if (!StringUtil.hasLength(vo.getNickName())
                            || !StringUtil.hasLength(vo.getPhone())
                            || !StringUtil.hasLength(vo.getProvince())
                            || !StringUtil.hasLength(vo.getCity())
                            || !StringUtil.hasLength(vo.getAddress())
                            || !StringUtil.hasLength(vo.getStreet())
                            || !StringUtil.hasLength(vo.getName())
                            ) {
                        flag = true;
                    }
                    if (StringUtil.hasLength(vo.getSex())) {
                        String sexStr = vo.getSex().substring(0, vo.getSex().indexOf("."));
                        if (this.isInteger(sexStr)) {
                            sex = Integer.parseInt(sexStr);
                        } else {
                            flag = true;
                        }
                    }
                    if (StringUtil.hasLength(vo.getAge())) {
                        String ageStr = vo.getAge().substring(0, vo.getSex().indexOf("."));
                        if (this.isInteger(ageStr)) {
                            age = Integer.parseInt(ageStr);
                        } else {
                            flag = true;
                        }
                    }
                    if (flag) {
                        failList.add(vo);
                    } else {
                        Integer id = fansShopBiz.selectByNickName(nickName, youkeId);
                        if (id == null || id == 0) {
                            TShopFans fans = new TShopFans();
                            fans.setNickname(nickName);
                            fans.setMobile(vo.getPhone());
                            fans.setProvince(vo.getProvince());
                            fans.setCity(vo.getCity());
                            fans.setShopid(shopId);
                            fans.setYoukeid(youkeId);
                            fans.setSex(sex);
                            fans.setEmail(vo.getEmail());
                            fans.setTruename(vo.getName());
                            fans.setComefrom(1);
                            fans.setIntegral(0);
                            fans.setExperience(0);
                            fans.setLoginCount(0);
                            Date date = new Date();
                            fans.setRegtime(date);
                            fans.setLasttime(date);
                            fans.setState(0);
                            fans.setDealtotal(0.0);
                            fans.setAvgdealtotal(0.0);
                            fans.setDealnum(0);
                            fansShopBiz.add(fans);
                        }else{
                            failList.add(vo);
                        }
                    }
                }

            }

            //修改
            TShopFansImport fansImport = new TShopFansImport();
            fansImport.setSuccessnum(voList.size());
            fansImport.setFailnum(0);
            fansImport.setState(1);
            if (failList.size() > 0) {
                String extensionName = FileUpOrDwUtil.getExtensionName(url);
                String filePath = UUID.randomUUID().toString()+"." + extensionName;
                EasyExcel fastExcel = new EasyExcel(filePath);
                fastExcel.createExcel(failList);
                fastExcel.close();
                File file = new File(filePath);
                String failUrl = FileUpOrDwUtil.uploadLocalFile(file, extensionName + "/" + filePath);
                boolean delete = file.delete();
                fansImport.setFailcsvurl(failUrl);
                fansImport.setFailnum(failList.size());
                fansImport.setState(2);
                if(failList.size() == voList.size()){
                    fansImport.setState(3);
                }
                fansImport.setSuccessnum(voList.size() - failList.size());
            }
            fansImport.setCompletetime(new Date());
            fansImport.setId(importId);
            fansShopBiz.updateImmport(fansImport);

        } catch (Exception e) {
            e.printStackTrace();
            //修改
            TShopFansImport fansImport = new TShopFansImport();
            fansImport.setSuccessnum(0);
            fansImport.setFailnum(0);
            fansImport.setCompletetime(new Date());
            fansImport.setFailcsvurl(url);
            fansImport.setId(importId);
            fansImport.setState(3);
            fansShopBiz.updateImmport(fansImport);
        } finally{
            if (fansInput != null) {
                fansInput.close();
            }
        }

    }

    private boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    @Override
    public void saveFans(ShopFansVo fans) {
        if(fans.getId() == null || fans.getId() < 1){
            throw new BusinessException("编辑数据有误");
        }
        fansShopBiz.saveFans(fans);
    }

    @Override
    public void openOrdeleteFans(String ids, int state) {
        if(state ==0 || state ==1){
            try {
                String[] split = ids.split(",");
                List<String> list = Arrays.asList(split);
                if(list.size() > 0){
                    fansShopBiz.updateFansState(list, state);
                }
            }catch (Exception e){
                throw new BusinessException("客户数据选择有误,请检查参数");
            }
        }
    }
}
