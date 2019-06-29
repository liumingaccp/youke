package youke.service.mass.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobKey;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.*;
import youke.common.exception.BusinessException;
import youke.common.model.*;
import youke.common.model.vo.page.PageModel;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.MassRecordRetVo;
import youke.common.model.vo.result.MassTempListVo;
import youke.common.model.vo.result.MassTemplateVo;
import youke.common.model.vo.result.MobcodeRecordVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.*;
import youke.facade.mass.vo.MassConstant;
import youke.facade.mass.vo.MassSMSMessage;
import youke.service.mass.biz.IMassSMSBiz;
import youke.service.mass.provider.SchedulerService;
import youke.service.mass.queue.producer.QueueSender;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MassSMSBiz extends Base implements IMassSMSBiz {

    @Resource
    private IMobcodeTempleteDao mobcodeTempleteDao;
    @Resource
    private IMobcodeRecordDao mobcodeRecordDao;
    @Resource
    private IShopFansDao shopFansDao;
    @Resource
    private IMobcodeFansDao mobcodeFansDao;
    @Resource
    private ISubscrFansDao subscrFansDao;
    @Resource
    private IMobcodeTaskDao mobcodeTaskDao;
    @Resource
    private IMobcodeDao mobcodeDao;
    @Resource
    private IMobcodeDetailDao mobcodeDetailDao;
    @Resource
    private SchedulerService schedulerService;
    @Resource
    private QueueSender queueSender;
    @Resource
    private IShopDao shopDao;

    @Override
    public void deleteRecord(int id) {
        TMobcodeRecord record = mobcodeRecordDao.selectByPrimaryKey(id);
        if (empty(record))
            throw new BusinessException("记录不存在");
        if (record.getState() == 1 || record.getState() == 3 || record.getState() == 4) {
            int count = mobcodeRecordDao.deleteByPrimaryKey(id);
            if (count == 0) {
                throw new BusinessException("记录删除失败");
            }
        } else {
            throw new BusinessException("该记录尚未生效");
        }
    }

    @Override
    public void deleteTemplate(int id) {
        TMobcodeTemplete template = mobcodeTempleteDao.selectByPrimaryKey(id);
        if (empty(template))
            throw new BusinessException("模板不存在");
        if (template.getRole() == 0)
            throw new BusinessException("系统模板无法删除");
        int count = mobcodeTempleteDao.deleteByPrimaryKey(id);
        if (count == 0) {
            throw new BusinessException("模板删除失败");
        }
    }

    public void saveTemplate(MassTemplateParamVo params, String dykId) {
        int num = mobcodeTempleteDao.selectTemplateByName(params.getName(), dykId);
        if (num > 0) {
            throw new BusinessException("重复的模板名称!");
        }
        TMobcodeTemplete templete = new TMobcodeTemplete();
        templete.setYoukeid(dykId);
        templete.setType(params.getType());
        templete.setRole(params.getRole());
        templete.setName(params.getName());
        templete.setContent(params.getContent());
        mobcodeTempleteDao.insertSelective(templete);
    }

    public PageInfo<TMobcodeTemplete> getTemplateList(MassSMSQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<TMobcodeTemplete> list = mobcodeTempleteDao.getTemplateList(params);
        PageModel<TMobcodeTemplete> pageModel = new PageModel<TMobcodeTemplete>((Page) list);
        for (TMobcodeTemplete tMobcodeTemplete : list) {
            TMobcodeTemplete templete = new TMobcodeTemplete();
            templete.setId(tMobcodeTemplete.getId());
            templete.setContent(tMobcodeTemplete.getContent());
            templete.setName(tMobcodeTemplete.getName());
            templete.setRole(tMobcodeTemplete.getRole());
            templete.setType(tMobcodeTemplete.getType());
            templete.setYoukeid(tMobcodeTemplete.getYoukeid());
            pageModel.getResult().add(templete);
        }
        return new PageInfo<>(pageModel);
    }

    public List<String> getTemplateNames(MassSMSQueryVo params) {
        return mobcodeTempleteDao.getTemplateNames(params);
    }

    public PageInfo<MobcodeRecordVo> getRecordList(MassSMSQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<TMobcodeRecord> list = mobcodeRecordDao.getRecordList(params);
        PageModel<MobcodeRecordVo> pageModel = new PageModel<>((Page) list);
        for (TMobcodeRecord tMobcodeRecord : list) {
            MobcodeRecordVo record = new MobcodeRecordVo();
            record.setId(tMobcodeRecord.getId());
            record.setContent(tMobcodeRecord.getContent());
            record.setLabel(tMobcodeRecord.getLabel());
            record.setCostNum(tMobcodeRecord.getSuccessnum());
            record.setSendNum(tMobcodeRecord.getSendnum());
            record.setSendType(tMobcodeRecord.getSendtype());
            record.setState(tMobcodeRecord.getState());
            record.setTaskTime(tMobcodeRecord.getTasktime());
            record.setSuccessNum(tMobcodeRecord.getSuccessnum());
            pageModel.getResult().add(record);
        }
        return new PageInfo<>(pageModel);
    }

    public PageInfo<TMobcodeDetail> getRecordDetailList(MassRecordDetailQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<TMobcodeDetail> list = mobcodeDetailDao.getRecordDetailListByRecordIdAndDykId(params);
        return new PageInfo<>(list);
    }

    /**
     * 会员群发获取发送明细
     *
     * @param params
     * @return
     */
    public PageInfo<MassTempListVo> getShopSendList(MassSMSParamVo params) {
        PageInfo<MassTempListVo> pageInfo = new PageInfo<>();
        if (params.getShopList().size() == 0 && params.getShopIds() != null && !"".equals(params.getShopIds())) {
            params.setShopList(Arrays.asList(params.getShopIds().split(",")));
        }
        if (params.getTagList().size() == 0 && params.getTagIds() != null && !"".equals(params.getTagIds())) {
            params.setTagList(Arrays.asList(params.getTagIds().split(",")));
            List<Long> ids = shopFansDao.selectTagFilterFansList(params);
            if (ids != null && ids.size() > 0) {
                params.setFansList(ids);
            } else {
                pageInfo.setList(new ArrayList<>());
                return pageInfo;
            }
        }
        if (params.getShopList().size() > 0) {
            if (params.getFilterDay() > 0) {
                List<String> mobiles = mobcodeFansDao.selectFilterMobiles(params.getDykId(), params.getFilterDay());
                params.getMobileList().addAll(mobiles);
            }
            if (params.getFilterWxFans() == 1) {
                List<String> mobiles = subscrFansDao.selectMobilesByAppId(params.getAppId());
                params.getMobileList().addAll(mobiles);
            }
            PageHelper.startPage(params.getPage(), params.getLimit());
            List<MassTempListVo> list = shopFansDao.getShopSendList(params);
            return new PageInfo<>(list);
        } else {
            pageInfo.setList(new ArrayList<>());
            return pageInfo;
        }
    }

    /**
     * 会员群发获取发送人
     *
     * @param params
     * @return
     */
    private List<MassTempListVo> getMemberSendList(MassSMSParamVo params) {
        if (params.getShopList().size() == 0 && params.getShopIds() != null && !"".equals(params.getShopIds())) {
            params.setShopList(Arrays.asList(params.getShopIds().split(",")));
        }
        if (params.getTagList().size() == 0 && params.getTagIds() != null && !"".equals(params.getTagIds())) {
            params.setTagList(Arrays.asList(params.getTagIds().split(",")));
            List<Long> ids = shopFansDao.selectTagFilterFansList(params);
            if (ids != null && ids.size() > 0) {
                params.setFansList(ids);
            } else {
                return new ArrayList<>();
            }
        }
        if (params.getShopList().size() > 0) {
            if (params.getFilterDay() > 0) {
                List<String> mobiles = mobcodeFansDao.selectFilterMobiles(params.getDykId(), params.getFilterDay());
                params.getMobileList().addAll(mobiles);
            }
            if (params.getFilterWxFans() == 1) {
                List<String> mobiles = subscrFansDao.selectMobilesByAppId(params.getAppId());
                params.getMobileList().addAll(mobiles);
            }
            return shopFansDao.getShopSendList(params);
        } else {
            return new ArrayList<>();
        }
    }

    /**
     * 普通群发获取发送人
     *
     * @param params
     * @return
     */
    private List<String> getSendList(MassSMSParamVo params) {
        List<String> mobileList = params.getMobileList();
        mobileList.removeIf(s -> !PhoneCheckUtil.isPhoneSimple(s));
        if (notEmpty(mobileList) && params.getFilterDay() > 0) {//日期过滤
            List<String> mobiles = mobcodeFansDao.selectFilterMobiles(params.getDykId(), params.getFilterDay());
            if (mobiles.size() > 0) {
                for (int i = mobileList.size() - 1; i >= 0; i--) {
                    if (mobiles.contains(mobileList.get(i))) {
                        mobileList.remove(i);
                    }
                }
            }
        }
        if (notEmpty(mobileList) && params.getFilterWxFans() == 1) {//公众号过滤
            List<String> mobiles = subscrFansDao.selectMobilesByAppId(params.getAppId());
            if (mobiles.size() > 0) {
                for (int j = mobileList.size() - 1; j >= 0; j--) {
                    if (mobiles.contains(mobileList.get(j))) {
                        mobileList.remove(j);
                    }
                }
            }
        }
        return mobileList;
    }


    /**
     * 会员群发
     *
     * @param params
     */
    public void saveMemberSendTask(MassSMSParamVo params) {
        if (empty(params.getLabel())) {
            throw new BusinessException("短信签名不能为空。");
        }
        if (params.getLabel().length() < 2 || params.getLabel().length() > 10) {
            throw new BusinessException("请将短信签名的字数控制在2到10个字符之间（括号不计算字符数）");
        }
        if (empty(params.getContent())) {
            throw new BusinessException("短信正文不能为空");
        }
        if (params.getContent().length() > 400) {
            throw new BusinessException("请将短信内容（加上签名）控制在400个字符以内。");
        }
        List<String> variates = StringUtil.getTextVariates(params.getContent());
        Map<String, Long> fansIdsMap = new HashMap<>();//获取粉丝id用
        List<String> sendMobiles = new ArrayList<>();
        if (params.getShopIds() != null && !"".equals(params.getShopIds()) && params.getShopIds().split(",").length > 0) {
            List<MassTempListVo> list = getMemberSendList(params);
            if (list.size() > 0) {
                for (MassTempListVo vo : list) {
                    if (PhoneCheckUtil.isPhoneSimple(vo.getMobile())) {
                        sendMobiles.add(vo.getMobile());
                        fansIdsMap.put(vo.getMobile(), vo.getId());
                    }
                }
            }
            if (notEmpty(params.getMymobile()) && PhoneCheckUtil.isPhoneSimple(params.getMymobile())) {
                sendMobiles.add(params.getMymobile());
            }
        } else {
            throw new BusinessException("请选择您的店铺!");
        }
        if (sendMobiles.size() > 0) {
            TMobcode mobcode = mobcodeDao.selectByPrimaryKey(params.getDykId());
            if (sendMobiles.size() > mobcode.getCount()) {
                throw new BusinessException("您的账户余额已用尽或您的余额不支持本次的请求数量。如需继续，请到短信充值页面购买更多发送许可后重试。");
            } else {
                mobcode.setIcecount(mobcode.getIcecount() + sendMobiles.size());
                mobcode.setCount(mobcode.getCount() - sendMobiles.size());
                mobcodeDao.updateByPrimaryKeySelective(mobcode);//冻结数目增加,可用数目减少

                TMobcodeTask task = new TMobcodeTask();
                task.setContent(params.getContent());
                task.setLabel(params.getLabel());
                task.setYoukeid(params.getDykId());
                task.setFilterday(params.getFilterDay());
                task.setFilterwxfans(params.getFilterWxFans());
                task.setTasktime(DateUtil.parseDate(params.getTaskTime()));
                task.setMobiles(StringUtils.join(sendMobiles.toArray(), ","));
                task.setShopids(params.getShopIds());
                task.setState(0);
                task.setSendtype(params.getType());
                if (notEmpty(params.getLastTimeBeg()))
                    task.setLasttimebeg(DateUtil.parseDate(params.getLastTimeBeg()));
                if (notEmpty(params.getLastTimeEnd()))
                    task.setLasttimeend(DateUtil.parseDate(params.getLastTimeEnd()));
                task.setAvgdealtotalbeg(params.getAvgDealTotalBeg());
                task.setAvgdealtotalend(params.getAvgDealTotalEnd());
                task.setMymobile(params.getMymobile());
                task.setHasnickvar(variates.contains("{买家昵称}") ? 1 : 0);
                task.setHasshopvar(variates.contains("{店铺名称}") ? 1 : 0);
                mobcodeTaskDao.insertSelective(task);

                TMobcodeRecord record = new TMobcodeRecord();
                record.setTaskid(task.getId());
                record.setYoukeid(params.getDykId());
                record.setLabel(params.getLabel());
                record.setSendnum(sendMobiles.size());
                record.setTasktime(DateUtil.parseDate(params.getTaskTime()));
                record.setContent(params.getContent());
                record.setSuccessnum(0);
                record.setFailnum(0);
                record.setState(0);
                record.setSendtype(params.getType());
                mobcodeRecordDao.insertSelective(record);

                TMobcodeDetail detail;
                int textLen = params.getContent().length() + params.getLabel().length();
                for (String sendMobile : sendMobiles) {
                    detail = new TMobcodeDetail();
                    detail.setState(0);
                    detail.setTextlen(textLen);
                    detail.setContent(params.getContent());
                    detail.setYoukeid(params.getDykId());
                    detail.setRecordid(record.getId());
                    detail.setMobile(sendMobile);
                    detail.setTaskid(task.getId());
                    mobcodeDetailDao.insertSelective(detail);
                }
                MassSMSMessage message = new MassSMSMessage();
                message.setContent(params.getContent());
                message.setDykId(params.getDykId());
                message.setLabel(params.getLabel());
                message.setTaskTime(params.getTaskTime());
                message.setMobiles(sendMobiles);
                message.setTaskId(task.getId());
                message.setFansIdsMap(fansIdsMap);
                message.setSendType(params.getType());
                message.setMyMobile(params.getMymobile());
                queueSender.send("massSend.queue", message);
            }
        } else {
            throw new BusinessException("根据您的筛选条件，所有客户均被过滤，暂无可用号码用来接收短信!");
        }
    }

    /**
     * 普通群发
     *
     * @param params
     */
    public void saveSendTask(MassSMSParamVo params) {
        if (empty(params.getLabel())) {
            throw new BusinessException("短信签名不能为空。");
        }
        if (params.getLabel().length() < 2 || params.getLabel().length() > 10) {
            throw new BusinessException("请将短信签名的字数控制在2到10个字符之间（括号不计算字符数）");
        }
        if (empty(params.getContent())) {
            throw new BusinessException("短信正文不能为空");
        }
        if (params.getContent().length() > 400) {
            throw new BusinessException("请将短信内容（加上签名）控制在400个字符以内。");
        }
        List<String> variates = StringUtil.getTextVariates(params.getContent());
        if (variates.size() > 0) {
            throw new BusinessException("依据当地法律法规，以下’{xxx}’词或类似短语不能出现在短信中。");
        }
        List<String> mobileList = new ArrayList<>();
        if (notEmpty(params.getFileUrl())) {
            List<String[]> csvFileList = ExportUtil.doParseCsv(params.getFileName(), params.getFileUrl());
            for (String[] fileList : csvFileList) {
                if (notEmpty(fileList[0]) && PhoneCheckUtil.isPhoneSimple(fileList[0])) {
                    mobileList.add(fileList[0]);
                }
            }
            params.setMobileList(mobileList);
        }
        if (params.getMobiles() != null && !"".equals(params.getMobiles()) && params.getMobiles().split(",").length > 0) {
            params.getMobileList().addAll(new ArrayList<>(new HashSet<>(Arrays.asList(params.getMobiles().split(",")))));
        }
        List<String> sendMobiles = new ArrayList<>(getSendList(params));
        if (sendMobiles.size() > 0) {
            TMobcode mobcode = mobcodeDao.selectByPrimaryKey(params.getDykId());
            if (sendMobiles.size() > mobcode.getCount()) {
                throw new BusinessException("您的账户余额已用尽或您的余额不支持本次的请求数量。如需继续，请到短信充值页面购买更多发送许可后重试。");
            } else {
                mobcode.setIcecount(mobcode.getIcecount() + sendMobiles.size());
                mobcode.setCount(mobcode.getCount() - sendMobiles.size());
                mobcodeDao.updateByPrimaryKeySelective(mobcode);//冻结数目增加,可用数目减少

                TMobcodeTask task = new TMobcodeTask();
                task.setContent(params.getContent());
                task.setLabel(params.getLabel());
                task.setYoukeid(params.getDykId());
                task.setFilterday(params.getFilterDay());
                task.setFilterwxfans(params.getFilterWxFans());
                task.setTasktime(DateUtil.parseDate(params.getTaskTime()));
                task.setMobiles(StringUtils.join(sendMobiles.toArray(), ","));
                task.setState(0);
                task.setSendtype(params.getType());
                mobcodeTaskDao.insertSelective(task);

                TMobcodeRecord record = new TMobcodeRecord();
                record.setTaskid(task.getId());
                record.setYoukeid(params.getDykId());
                record.setLabel(params.getLabel());
                record.setSendnum(sendMobiles.size());
                record.setTasktime(DateUtil.parseDate(params.getTaskTime()));
                record.setContent(params.getContent());
                record.setSuccessnum(0);
                record.setFailnum(0);
                record.setState(0);
                record.setSendtype(params.getType());
                mobcodeRecordDao.insertSelective(record);

                TMobcodeDetail detail;
                int textLen = params.getContent().length() + params.getLabel().length();
                for (String sendMobile : sendMobiles) {
                    detail = new TMobcodeDetail();
                    detail.setTextlen(textLen);
                    detail.setContent(params.getContent());
                    detail.setYoukeid(params.getDykId());
                    detail.setRecordid(record.getId());
                    detail.setMobile(sendMobile);
                    detail.setTaskid(task.getId());
                    detail.setState(0);
                    mobcodeDetailDao.insertSelective(detail);
                }
                MassSMSMessage message = new MassSMSMessage();
                message.setContent(params.getContent());
                message.setDykId(params.getDykId());
                message.setLabel(params.getLabel());
                message.setTaskTime(params.getTaskTime());
                message.setMobiles(sendMobiles);
                message.setTaskId(task.getId());
                message.setSendType(params.getType());
                message.setMyMobile(params.getMymobile());
                queueSender.send("massSend.queue", message);
            }
        } else {
            throw new BusinessException("根据您的筛选条件，所有客户均被过滤，暂无可用号码用来接收短信!");
        }
    }

    @Override
    public void doPostTemplate(MassSMSMessage message) {
        TMobcodeRecord record = mobcodeRecordDao.getRecordByYoukeIdAndTaskId(message.getDykId(), message.getTaskId());
        if (notEmpty(record)) {
            TMobcodeTask task = mobcodeTaskDao.selectByPrimaryKey(record.getTaskid());
            if (notEmpty(task)) {
                String content = message.getContent();
                if (task.getHasnickvar() == 1) {
                    String regex = "\\{" + "买家昵称" + "\\}";
                    content = content.replaceAll(regex, "@var(nickName)");
                }
                if (task.getHasshopvar() == 1) {
                    String regex = "\\{" + "店铺名称" + "\\}";
                    content = content.replaceAll(regex, "@var(shopName)");
                }
                boolean flag = false;
                String templateId = mobcodeRecordDao.selectTemplateByContent(message.getDykId(), message.getContent());//通过匹配短信内容获取已存在的模板Id
                if (empty(templateId)) {//没有模板
                    String postResponse = MessageUtil.post(message.getLabel(), content);//创建模板
                    if (MessageUtil.ret(postResponse)) {
                        templateId = JsonUtils.getString(postResponse, "template_id");
                        flag = true;
                        record.setState(0);
                    } else {
                        requestFailed(message, "短信模板创建失败");
                    }
                } else {//本地存在模板
                    String getResponse = MessageUtil.get(templateId);//获取模板
                    if (MessageUtil.ret(getResponse)) {
                        flag = true;
                        record.setState(2);//存在该模板
                    } else {
                        String postResponse = MessageUtil.post(message.getLabel(), content);
                        if (MessageUtil.ret(postResponse)) {
                            flag = true;
                            record.setState(0);
                            templateId = JsonUtils.getString(postResponse, "template_id");
                        } else {
                            requestFailed(message, "短信模板创建失败");
                        }
                    }
                }
                if (flag) {
                    record.setTempletepro(templateId);
                    mobcodeRecordDao.updateByPrimaryKeySelective(record);
                    //RedisUtil.hset(MassConstant.TEMPLATE_MESSAGE, templateId, message);
                    message.setTemplateId(templateId);
                    schedulerService.startSchedule(message);
                }
            } else {
                requestFailed(message, "短信任务不存在");
            }
        } else {
            requestFailed(message, "短信任务记录不存在");
        }
    }

    @Override
    public void doMass(MassSMSMessage message) throws Exception {
        TMobcodeRecord record = mobcodeRecordDao.getRecordByYoukeIdAndTaskId(message.getDykId(), message.getTaskId());
        if (notEmpty(record) && record.getState() == 2) {//模板审核成功
            Map<String, Object> map;
            List<Map<String, Object>> multi = new ArrayList<>();
            for (String mobile : message.getMobiles()) {
                map = new HashMap<>();
                map.put("to", mobile);
                multi.add(map);
            }
            String res = MessageUtil.multixsend(JsonUtils.ListToJson(multi), message.getTemplateId());
            if (MessageUtil.ret(res)) {//请求成功
                TMobcodeTask task = mobcodeTaskDao.selectByPrimaryKey(message.getTaskId());
                mobcodeTaskDao.updateByPrimaryKeySelective(task);
                record.setState(3);
                record.setOvertime(new Date());
                record.setSuccessnum(record.getSendnum());
                mobcodeRecordDao.updateByPrimaryKeySelective(record);
                TMobcodeDetail detail;
                for (String mobile : message.getMobiles()) {
                    detail = mobcodeDetailDao.getRecordDetailByRecordIdAndDykId(record.getId(), message.getDykId(), mobile);
                    if (notEmpty(detail)) {
                        detail.setCostnum(1);
                        detail.setState(1);
                        detail.setSendtime(new Date());
                        mobcodeDetailDao.updateByPrimaryKeySelective(detail);
                    }
                }
                TMobcode mobcode = mobcodeDao.selectByPrimaryKey(task.getYoukeid());
                mobcode.setIcecount(mobcode.getIcecount() - record.getSendnum());
                mobcodeDao.updateByPrimaryKeySelective(mobcode);
            } else {//请求失败
                requestFailed(message, "未知网关状态");
            }
        } else if (notEmpty(record) && record.getState() == 1) {//模板审核失败
            requestFailed(message, "短信模板审核失败");
        } else if (notEmpty(record) && record.getState() == 0) {//模板审核中
            //2分钟后再试
            message.setTaskTime(DateUtil.formatDateTime(DateUtil.getMinuteAfter(DateUtil.parseDate(message.getTaskTime()), 2)));
            schedulerService.startSchedule(message);
        }
    }

    @Override
    public void doMemberMass(MassSMSMessage message) throws Exception {
        TMobcodeRecord record = mobcodeRecordDao.getRecordByYoukeIdAndTaskId(message.getDykId(), message.getTaskId());
        if (notEmpty(record) && record.getState() == 2) {
            Map<String, Long> fansIdsMap = message.getFansIdsMap();
            List<Map<String, Object>> multi;
            Map<String, String> varMap;
            Map<String, Object> map;
            TShopFans shopFans;
            TMobcodeTask task;
            Long fansId;
            TShop shop;
            task = mobcodeTaskDao.selectByPrimaryKey(message.getTaskId());
            if (notEmpty(task)) {
                multi = new ArrayList<>();
                for (String mobile : message.getMobiles()) {
                    map = new HashMap<>();
                    varMap = new HashMap<>();
                    map.put("to", mobile);
                    fansId = fansIdsMap.get(mobile);
                    shopFans = shopFansDao.selectByPrimaryKey(fansId);
                    if (task.getHasnickvar() == 1 && shopFans != null) {
                        if (notEmpty(shopFans)) {
                            varMap.put("nickName", shopFans.getNickname());
                        }
                    }
                    if (task.getHasshopvar() == 1 && shopFans != null) {
                        shop = shopDao.selectByPrimaryKey(shopFans.getShopid());
                        if (notEmpty(shop)) {
                            varMap.put("shopName", shop.getTitle());
                        }
                    }
                    map.put("vars", varMap);
                    multi.add(map);
                }
                if (notEmpty(message.getMyMobile())) {
                    map = new HashMap<>();
                    map.put("to", message.getMyMobile());
                    varMap = new HashMap<>();
                    if (task.getHasnickvar() == 1) {
                        varMap.put("nickName", message.getMyMobile());
                    }
                    if (task.getHasshopvar() == 1) {
                        varMap.put("shopName", message.getLabel().substring(1, message.getLabel().length() - 1));
                    }
                    map.put("vars", varMap);
                    multi.add(map);
                }
                String res = MessageUtil.multixsend(JsonUtils.ListToJson(multi), message.getTemplateId());
                if (MessageUtil.ret(res)) {//请求成功
                    task.setState(1);
                    mobcodeTaskDao.updateByPrimaryKeySelective(task);
                    record.setSuccessnum(record.getSendnum());
                    record.setOvertime(new Date());
                    record.setState(3);
                    mobcodeRecordDao.updateByPrimaryKeySelective(record);
                    TMobcodeDetail detail;
                    for (String mobile : message.getMobiles()) {
                        detail = mobcodeDetailDao.getRecordDetailByRecordIdAndDykId(record.getId(), message.getDykId(), mobile);
                        if (notEmpty(detail)) {
                            detail.setSendtime(new Date());
                            detail.setState(1);
                            detail.setCostnum(1);
                            mobcodeDetailDao.updateByPrimaryKeySelective(detail);
                            TMobcodeFans fans = mobcodeFansDao.selectByMobile(mobile, message.getDykId());
                            if (empty(fans)) {
                                fans = new TMobcodeFans();
                                fans.setLastsendtime(new Date());
                                fans.setMobile(mobile);
                                fans.setYoukeid(message.getDykId());
                                mobcodeFansDao.insertSelective(fans);//粉丝发送记录
                            } else {
                                mobcodeFansDao.updateByYoukeIdAndMobile(mobile, message.getDykId());
                            }
                        }
                    }
                    TMobcode mobcode = mobcodeDao.selectByPrimaryKey(task.getYoukeid());
                    mobcode.setIcecount(mobcode.getIcecount() - record.getSendnum());
                    mobcodeDao.updateByPrimaryKeySelective(mobcode);
                } else {//群发请求失败
                    requestFailed(message, "未知网关状态");
                }
            }
        } else if (notEmpty(record) && record.getState() == 1) {//模板审核失败
            requestFailed(message, "短信模板审核失败!");
        } else if (notEmpty(record) && record.getState() == 0) {//模板审核中
            //2分钟后再试
            message.setTaskTime(DateUtil.formatDateTime(DateUtil.getMinuteAfter(DateUtil.parseDate(message.getTaskTime()), 2)));
            schedulerService.startSchedule(message);
        }
    }

    /**
     * 群发请求失败
     *
     * @param message    消息对象
     * @param failReason 错误原因
     */
    private void requestFailed(MassSMSMessage message, String failReason) {
        int size = message.getMobiles().size();
        TMobcodeTask task = mobcodeTaskDao.selectByPrimaryKey(message.getTaskId());
        if (notEmpty(task)) {
            task.setState(1);
            mobcodeTaskDao.updateByPrimaryKeySelective(task);
        }
        TMobcodeRecord record = mobcodeRecordDao.getRecordByYoukeIdAndTaskId(message.getDykId(), message.getTaskId());
        if (notEmpty(record)) {
            record.setOvertime(new Date());
            record.setFailnum(size);
            record.setFailreason(failReason);
            record.setState(record.getState() == 1 ? 1 : 4);
            mobcodeRecordDao.updateByPrimaryKeySelective(record);
        }
        TMobcode mobCode = mobcodeDao.selectByPrimaryKey(message.getDykId());
        if (notEmpty(mobCode)) {
            mobCode.setCount(mobCode.getCount() + size);
            mobCode.setIcecount(mobCode.getIcecount() - size);
            mobcodeDao.updateByPrimaryKeySelective(mobCode);
        }
    }


    @Override
    public PageInfo<MassRecordRetVo> getMassList(MassQueryVo params) {
        PageHelper.startPage(params.getPage(), params.getLimit());
        List<MassRecordRetVo> list = mobcodeRecordDao.selectMassList(params);
        return new PageInfo<>(list);
    }

    @Override
    public void doCancelSend(JSONObject params) {
        int id = params.getInt("id");
        TMobcodeRecord record = mobcodeRecordDao.selectByPrimaryKey(id);
        if (empty(record)) {
            throw new BusinessException("任务不存在");
        }
        if (record.getState() > 2) {
            throw new BusinessException("任务已经执行无法取消");
        }
        record.setState(6);//取消发送
        mobcodeRecordDao.updateByPrimaryKeySelective(record);
        TMobcode mobcode = mobcodeDao.selectByPrimaryKey(record.getYoukeid());
        if (notEmpty(mobcode)) {
            mobcode.setIcecount(mobcode.getIcecount() - record.getSendnum());
            mobcode.setCount(mobcode.getCount() + record.getSendnum());
        }
        JobKey jobKey = JobKey.jobKey(record.getYoukeid() + "-" + record.getTaskid(), record.getYoukeid() + "mass-job-group");
        TriggerKey triggerKey = new TriggerKey(record.getYoukeid() + "-" + record.getTaskid(), record.getYoukeid() + "mass-trigger-group");
        schedulerService.deleteJob(jobKey, triggerKey);
    }

    /**
     * 短信发送成功时触发
     *
     * @param message
     * @param massMessage
     */
    public void savedelivered(Message message, MassSMSMessage massMessage) {
        /*if (notEmpty(massMessage)) {
            String address = message.getAddress();
            Integer taskId = massMessage.getTaskId();
            String dykId = massMessage.getDykId();
            String myMobile = massMessage.getMyMobile();
            TMobcodeDetail detail = mobcodeDetailDao.selectRecordDetailByTaskIdAndMobile(taskId, address);
            if (detail.getState() == 0) {
                if (notEmpty(myMobile)) {
                    if (address.equals(myMobile)) {//mymobile不计入发送明细
                        return;
                    }
                }
                TMobcodeFans fans = mobcodeFansDao.selectByMobile(address, dykId);
                if (empty(fans)) {
                    fans = new TMobcodeFans();
                    fans.setLastsendtime(new Date());
                    fans.setMobile(address);
                    fans.setYoukeid(dykId);
                    mobcodeFansDao.insertSelective(fans);//粉丝发送记录
                } else {
                    mobcodeFansDao.updateByYoukeIdAndMobile(address, dykId);
                }
                TMobcodeRecord record = mobcodeRecordDao.getRecordByYoukeIdAndTaskId(dykId, taskId);
                if (notEmpty(record)) {
                    record.setState(3);
                    record.setSuccessnum(record.getSuccessnum() + 1);
                    mobcodeRecordDao.updateByPrimaryKeySelective(record);
                    detail.setCostnum(1);
                    detail.setState(1);
                    mobcodeDetailDao.updateByPrimaryKeySelective(detail);
                    TMobcode mobcode = mobcodeDao.selectByPrimaryKey(dykId);
                    if (notEmpty(mobcode)) {
                        mobcode.setIcecount(mobcode.getIcecount() - 1);
                        mobcodeDao.updateByPrimaryKeySelective(mobcode);
                    }
                }
            }
        }*/
    }

    /**
     * 短信发送失败时触发
     *
     * @param message
     * @param massMessage
     */
    public void savedropped(Message message, MassSMSMessage massMessage) {
        /*if (notEmpty(massMessage)) {
            String address = message.getAddress();
            String report = message.getReport();
            Integer taskId = massMessage.getTaskId();
            String dykId = massMessage.getDykId();
            TMobcodeDetail detail = mobcodeDetailDao.selectRecordDetailByTaskIdAndMobile(taskId, address);
            if (detail.getState() == 0) {
                TMobcodeRecord record = mobcodeRecordDao.getRecordByYoukeIdAndTaskId(dykId, taskId);
                if (notEmpty(record)) {
                    detail.setState(2);
                    detail.setCostnum(0);
                    mobcodeDetailDao.updateByPrimaryKeySelective(detail);
                    record.setFailnum(record.getFailnum() + 1);
                    record.setFailreason(report);
                    mobcodeRecordDao.updateByPrimaryKeySelective(record);
                    TMobcode mobcode = mobcodeDao.selectByPrimaryKey(dykId);
                    if (notEmpty(mobcode)) {
                        mobcode.setIcecount(mobcode.getIcecount() - 1);
                        mobcode.setCount(mobcode.getCount() + 1);
                        mobcodeDao.updateByPrimaryKeySelective(mobcode);
                    }
                }
            }
        }*/
    }

    /**
     * 未知网关
     * 手机信号异常，或基站异常，网关一直处于下发状态，submail向网关获取500次仍无回执时，会标记为unkown状态
     *
     * @param message
     * @param massMessage
     */
    public void saveunkown(Message message, MassSMSMessage massMessage) {
        /*if (notEmpty(massMessage)) {
            String address = message.getAddress();
            String report = message.getReport();
            Integer taskId = massMessage.getTaskId();
            String dykId = massMessage.getDykId();
            TMobcodeDetail detail = mobcodeDetailDao.selectRecordDetailByTaskIdAndMobile(taskId, address);
            if (detail.getState() == 0) {
                TMobcodeRecord record = mobcodeRecordDao.getRecordByYoukeIdAndTaskId(dykId, taskId);
                if (notEmpty(record)) {
                    detail.setState(2);
                    detail.setCostnum(0);
                    detail.setFailreason("未知网关状态:" + report);
                    mobcodeDetailDao.updateByPrimaryKeySelective(detail);
                    record.setFailnum(record.getFailnum() + 1);
                    mobcodeRecordDao.updateByPrimaryKeySelective(record);
                    TMobcode mobcode = mobcodeDao.selectByPrimaryKey(dykId);
                    if (notEmpty(mobcode)) {
                        mobcode.setIcecount(mobcode.getIcecount() - 1);
                        mobcode.setCount(mobcode.getCount() + 1);
                        mobcodeDao.updateByPrimaryKeySelective(mobcode);
                    }
                    mobcodeRecordDao.updateStateAndFailNum(record.getId(), "未知网关状态");//任务记录更新
                    mobcodeDetailDao.updateStateByMobileAndRecordIdWithFailure(address, record.getId(), dykId); //任务记录明细更新
                    mobcodeDao.updateCountAndIceCount(dykId, 1);//恢复短信数量和冻结数量
                }
            }
        }*/
    }

    /**
     * 短信模板审核通过时触发
     *
     * @param message
     */
    public void savetemplate_accept(Message message) {
        String templateId = message.getTemplate_id();
        TMobcodeRecord record = mobcodeRecordDao.selectRecordByTemplateId(templateId);
        if (notEmpty(record) && record.getState() == 0) {
            record.setState(2);
            mobcodeRecordDao.updateByPrimaryKeySelective(record);
        }
        String appId = (String) RedisSlaveUtil.hget(MassConstant.TEMPLATE_APPID, templateId);
        if (notEmpty(appId)) {
            MassTemplateVo vo = (MassTemplateVo) RedisSlaveUtil.hget(MassConstant.APPID_TEMPLATE, appId);
            if (notEmpty(vo)) {
                vo.setState(1);
                RedisUtil.hset(MassConstant.APPID_TEMPLATE, appId, vo);
            }
        }
    }

    /**
     * 短信模板审核不通过时触发
     *
     * @param message
     */
    public void savetemplate_reject(Message message) {
        String templateId = message.getTemplate_id();
        String reason = message.getReason();
        TMobcodeRecord record = mobcodeRecordDao.selectRecordByTemplateId(templateId);
        if (notEmpty(record) && record.getState() == 0) {
            record.setState(1);
            record.setFailreason(reason);
            mobcodeRecordDao.updateByPrimaryKeySelective(record);
        }
        String appId = (String) RedisSlaveUtil.hget(MassConstant.TEMPLATE_APPID, templateId);
        if (notEmpty(appId)) {
            MassTemplateVo vo = (MassTemplateVo) RedisSlaveUtil.hget(MassConstant.APPID_TEMPLATE, appId);
            if (notEmpty(vo)) {
                vo.setState(2);
                RedisUtil.hset(MassConstant.APPID_TEMPLATE, appId, vo);
            }
        }
    }

    /**
     * 请求成功 API请求成功时触发
     *
     * @param message
     * @param massMessage
     */
    public void saverequest(Message message, MassSMSMessage massMessage) {
    }

    /**
     * 发送中
     *
     * @param message
     * @param massMessage
     */
    public void savesending(Message message, MassSMSMessage massMessage) {
    }

    /**
     * 短信上行 用户回复时触发
     *
     * @param message
     * @param massMessage
     */
    public void savemo(Message message, MassSMSMessage massMessage) {
    }
}
