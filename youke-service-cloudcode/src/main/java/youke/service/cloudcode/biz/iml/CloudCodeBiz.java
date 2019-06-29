package youke.service.cloudcode.biz.iml;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.TCloudCode;
import youke.common.model.TCloudCodeQrCode;
import youke.common.model.TCloudCodeRecord;
import youke.common.model.TCloudCodeRule;
import youke.common.model.vo.param.cloudcode.*;
import youke.common.model.vo.result.cloudcode.*;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.common.utils.JsonUtils;
import youke.service.cloudcode.biz.ICloudCodeBiz;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class CloudCodeBiz extends Base implements ICloudCodeBiz {

    @Resource
    private ICloudCodeDao cloudCodeDao;
    @Resource
    private ICloudCodeRuleDao cloudCodeRuleDao;
    @Resource
    private ICloudCodeQrCodeDao cloudCodeQrCodeDao;
    @Resource
    private ICloudCodeRecordDao cloudCodeRecordDao;
    @Resource
    private IConfigDao configDao;

    @Override
    public Long saveCloudCode(CloudCodeSaveVo params) {
        JSONObject obj1;
        JSONObject obj2;
        CloudCodeRuleSaveVo ruleSaveVo = null;
        CloudCodeQrCodeSaveVo qrCodeSaveVo = null;
        TCloudCodeRule rule;
        TCloudCodeQrCode qrCode;
        TCloudCode cloudCode;
        List<Long> codeIds = new ArrayList<>();
        if (params.getRules().size() >= 2) {//多规则检测时间是否重叠
            for (int i = 0; i <= params.getRules().size() - 1; i++) {
                obj1 = JSONObject.fromObject(params.getRules().get(i));
                for (int j = i + 1; j <= params.getRules().size() - 1; j++) {
                    obj2 = JSONObject.fromObject(params.getRules().get(j));
                    if (DateUtil.isOverride(obj1.getString("begTime"), obj1.getString("endTime"), obj2.getString("begTime"), obj2.getString("endTime"))) {
                        throw new BusinessException("时间段出现重叠");
                    }
                }
            }
        }
        if (params.getId() == -1) {
            cloudCode = new TCloudCode();
            cloudCode.setAppid(params.getAppId());
            cloudCode.setYoukeid(params.getDykId());
            cloudCode.setState(0);
            cloudCode.setCreatetime(new Date());
        } else {
            cloudCode = cloudCodeDao.selectByPrimaryKey(params.getId());
            cloudCodeRuleDao.deleteByCloudCodeId(params.getId());
        }
        cloudCode.setTitle(params.getTitle());
        if (params.getId() == -1) {
            cloudCodeDao.insertSelective(cloudCode);
        } else {
            cloudCodeDao.updateByPrimaryKeySelective(cloudCode);
        }
        for (Object object : params.getRules()) {
            try {
                ruleSaveVo = JsonUtils.JsonToBean(object.toString(), CloudCodeRuleSaveVo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (ruleSaveVo != null) {
                rule = new TCloudCodeRule();
                rule.setCloudid(cloudCode.getId());
                rule.setBegintime(ruleSaveVo.getBegTime());
                rule.setEndtime(ruleSaveVo.getEndTime());
                rule.setDaytimes(ruleSaveVo.getDayTimes());
                rule.setInorder(ruleSaveVo.getInOrder());
                rule.setIsrandom(ruleSaveVo.getIsRandom());
                rule.setTotaltimes(ruleSaveVo.getTotalTimes());
                rule.setYoukeid(params.getDykId());
                cloudCodeRuleDao.insertSelective(rule);
                for (Object obj : ruleSaveVo.getQrcodes()) {
                    try {
                        qrCodeSaveVo = JsonUtils.JsonToBean(obj.toString(), CloudCodeQrCodeSaveVo.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (qrCodeSaveVo != null) {
                        if (qrCodeSaveVo.getId() == -1) {
                            qrCode = new TCloudCodeQrCode();
                            qrCode.setScantimes(0);
                            qrCode.setYoukeid(params.getDykId());
                        } else {
                            qrCode = cloudCodeQrCodeDao.selectByPrimaryKey(qrCodeSaveVo.getId());
                        }
                        qrCode.setCloudid(cloudCode.getId());
                        qrCode.setRemark(qrCodeSaveVo.getRemark());
                        qrCode.setUrl(qrCodeSaveVo.getUrl());
                        qrCode.setRuleid(rule.getId());
                        if (qrCodeSaveVo.getId() == -1) {
                            cloudCodeQrCodeDao.insertSelective(qrCode);
                        } else {
                            cloudCodeQrCodeDao.updateByPrimaryKeySelective(qrCode);
                        }
                        codeIds.add(qrCode.getId());
                    }
                }
            }
        }
        if (codeIds.size() > 0 && params.getId() != -1) {//删除无效二维码
            cloudCodeQrCodeDao.updateInvalidCode(codeIds, params.getDykId(), params.getId());
        }
        return cloudCode.getId();
    }

    @Override
    public PageInfo<CloudCodeQueryRetVo> getCloudCodeList(CloudCodeQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<CloudCodeQueryRetVo> list = cloudCodeDao.selectCloudCodeList(params);
        return new PageInfo<>(list);
    }

    @Override
    public CloudCodeAuditRetVo getCloudCodeData(Long id) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        CloudCodeAuditRetVo vo = new CloudCodeAuditRetVo();
        TCloudCode cloudCode = cloudCodeDao.selectByPrimaryKey(id);
        if (cloudCode != null) {
            List<CloudCodeRuleAuditRetVo> rules = cloudCodeRuleDao.selectRulesAndQrCodesByCloudId(id);
            for (CloudCodeRuleAuditRetVo rule : rules) {
                try {
                    rule.setTimeBeg(format.parse(rule.getBegTime()).getTime());
                    rule.setTimeEnd(format.parse(rule.getEndTime()).getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            vo.setId(cloudCode.getId());
            vo.setTitle(cloudCode.getTitle());
            vo.setRules(rules);
            return vo;
        } else {
            throw new BusinessException("该云码不存在");
        }
    }

    @Override
    public void doDropCloudCode(Long id) {
        TCloudCode cloudCode = cloudCodeDao.selectByPrimaryKey(id);
        if (notEmpty(cloudCode)) {
            cloudCode.setState(1);
            cloudCodeDao.updateByPrimaryKeySelective(cloudCode);
        } else {
            throw new BusinessException("该云码不存在");
        }
    }

    @Override
    public void deleteCloudCode(Long id) {
        TCloudCode cloudCode = cloudCodeDao.selectByPrimaryKey(id);
        if (notEmpty(cloudCode)) {
            if (cloudCode.getState() != 1) {
                throw new BusinessException("只能删除已作废的云码");
            } else {
                cloudCode.setState(2);
                cloudCodeDao.updateByPrimaryKeySelective(cloudCode);
            }
        } else {
            throw new BusinessException("该云码不存在");
        }
    }

    @Override
    public PageInfo<CloudCodeRecordQueryRetVo> getRecordList(CloudCodeRecordQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<CloudCodeRecordQueryRetVo> list = cloudCodeDao.selectRecordList(params);
        return new PageInfo<>(list);
    }

    @Override
    public H5CloudCodeQrCodeRetVo doGetCloudCodeAndSaveRecord(String openId, Long id) {
        Long codeId;
        Long lastCodeId;
        Integer lastIndex;
        Integer currentIndex;
        List<Long> qrcodes;
        TCloudCodeRecord record;
        TCloudCode cloudCode = cloudCodeDao.selectByPrimaryKey(id);
        if (notEmpty(cloudCode)) {
            if (cloudCode.getState() != 0) {
                throw new BusinessException("该二维码已过期");
            } else {
                List<TCloudCodeRule> rules = cloudCodeRuleDao.selectByCloudId(id);
                if (rules.size() > 0) {
                    for (TCloudCodeRule rule : rules) {
                        //筛选规则
                        if (DateUtil.isInDate(new Date(), rule.getBegintime(), rule.getEndtime())) {
                            //获取匹配本规则的所有有效二维码
                            qrcodes = cloudCodeQrCodeDao.selectFilterQrCodes(id, rule.getId(), rule.getDaytimes(), rule.getTotaltimes());
                            if (qrcodes.size() > 0) {
                                if (rule.getInorder() == 1) {//顺序返回
                                    //本规则的上次最后被扫码的二维码Id
                                    lastCodeId = cloudCodeQrCodeDao.selectLastScanQrCodeId(cloudCode.getId(), rule.getId());
                                    if (empty(lastCodeId)) {//如果没有扫码记录,说明是第一次匹配到本规则
                                        codeId = qrcodes.get(0);//那么就取本规则的第一个二维码
                                        currentIndex = 0;
                                    } else {//如果存在扫码记录
                                        if (qrcodes.contains(lastCodeId)) {//如果本规则匹配到的二维码中包含上次扫描的二维码
                                             lastIndex = qrcodes.indexOf(lastCodeId);//获取上次扫描二维码在本次匹配到的所有二维码中的索引
                                            if (lastIndex == qrcodes.size()-1) {//如果获取到的索引等同于集合的长度
                                                currentIndex = 0;//获取第一条数据
                                            } else {//索引位置小于集合长度
                                                currentIndex = lastIndex + 1;//获取上次索引的下一条索引
                                            }
                                            codeId = qrcodes.get(currentIndex);
                                        } else {//如果本规则匹配到的二维码中不包含上次扫描的二维码
                                            //获取缓存中保存的上一次索引
                                            lastIndex = (Integer) RedisSlaveUtil.hget("CLOUD_SCAN_RECORD",cloudCode.getId()+"-"+rule.getId());
                                            if (lastIndex==qrcodes.size()-1){//如果上次索引等同于当前集合的长度
                                                currentIndex = qrcodes.size()-1;//获取当前集合长度索引位置的数据
                                            }else if (lastIndex ==0){//如果上次索引为0
                                                currentIndex = 0;//返回索引为0的数据
                                            }else{
                                                currentIndex = lastIndex>qrcodes.size()-1?0:lastIndex;
                                            }
                                            codeId = qrcodes.get(currentIndex);
                                        }
                                    }
                                } else {//随机返回
                                    currentIndex = new Random().nextInt(qrcodes.size()) % (qrcodes.size() + 1);
                                    codeId = qrcodes.get(currentIndex);
                                }
                                record = new TCloudCodeRecord();
                                record.setCloudid(id);
                                record.setOpenid(openId);
                                record.setYoukeid(cloudCode.getYoukeid());
                                record.setScantime(new Date());
                                record.setCodeid(codeId);
                                cloudCodeRecordDao.insertSelective(record);
                                cloudCodeQrCodeDao.updateScanTimesAndLastScanTime(codeId);
                                RedisUtil.hset("CLOUD_SCAN_RECORD",cloudCode.getId()+"-"+rule.getId(),currentIndex);
                                return cloudCodeQrCodeDao.selectCodeByPrimary(codeId);
                            } else {
                                throw new BusinessException("添加人数已满，请明天再试");
                            }
                        }
                    }
                }
            }
        }
        throw new BusinessException("添加失败，请稍后再试");
    }

    @Override
    public String getConfig(String key) {
        return configDao.selectVal(key);
    }
}
