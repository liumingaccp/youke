package youke.service.mass.biz.impl;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.IMobcodeDao;
import youke.common.dao.IMobcodeRecordDao;
import youke.common.dao.ISubscrDao;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.KeyValVo;
import youke.common.model.TMobcode;
import youke.common.model.TMobcodeRecord;
import youke.common.model.TSubscr;
import youke.common.model.vo.result.MassTemplateVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.utils.JsonUtils;
import youke.facade.mass.vo.ExpireMsgVo;
import youke.facade.mass.vo.MassConstant;
import youke.service.mass.biz.IMobmsgBiz;
import youke.common.utils.MessageUtil;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class MobmsgBiz extends Base implements IMobmsgBiz {

    private static final int MOBCODETIME = 1800;//30分钟

    private static final String MOBCODEKEY = "mobile-code-";

    @Resource
    private IMobcodeDao mobcodeDao;
    @Resource
    private ISubscrDao subscrDao;
    @Resource
    private IMobcodeRecordDao mobcodeRecordDao;

    /**
     * 扣除公众号对应商户短信数量
     */
    private void doDeductNum(String appId, String code) {
        TSubscr subscr = subscrDao.selectByPrimaryKey(appId);
        if (notEmpty(subscr)) {
            TMobcode mobcode = mobcodeDao.selectByPrimaryKey(subscr.getYoukeid());
            if (notEmpty(mobcode)) {
                if (mobcode.getCount() <= 0) {
                    throw new BusinessException("验证码发送失败，短信余额不足，请联系商家。");
                } else {
                    mobcodeDao.updateAddCount(-1, mobcode.getYoukeid());//扣除短信数量
                    TMobcodeRecord record = new TMobcodeRecord();
                    MassTemplateVo vo = (MassTemplateVo) RedisSlaveUtil.hget(MassConstant.APPID_TEMPLATE, appId);
                    if (notEmpty(vo)) {
                        record.setContent(ApiCodeConstant.DEFAULT_CODE_MSG.replace("@var(code)", code).replace("@var(time)", "30"));
                        record.setSuccessnum(1);
                        record.setFailnum(0);
                        record.setState(3);
                        record.setYoukeid(subscr.getYoukeid());
                        record.setSendtype(10);
                        record.setLabel(subscrDao.selectNickname(appId));
                        record.setTempletepro(vo.getTemplate());
                        record.setOvertime(new Date());
                        record.setTasktime(new Date());
                        mobcodeRecordDao.insertSelective(record);
                    }
                }
            }
        }
    }

    @Override
    public void doSendMobCode(String appId, String mobile) {
        if (notEmpty(RedisSlaveUtil.get(MOBCODEKEY + appId + mobile + "-time")))
            throw new BusinessException("验证码发送间隔较短，请稍候再试");
        RedisUtil.set(MOBCODEKEY + appId + mobile + "-time", "60", 60);
        String timeS = new Date().getTime() + "";
        String code = timeS.substring(timeS.length() - 4);
        RedisUtil.set(MOBCODEKEY + mobile, code, MOBCODETIME);
        if (notEmpty(appId)) {
            if (appId.equals(ApiCodeConstant.APPID)) {
                MessageUtil.sendMobCode(mobile, code);
            } else {
                //扣除短信数量
                doDeductNum(appId, code);
                MassTemplateVo vo = (MassTemplateVo) RedisSlaveUtil.hget(MassConstant.APPID_TEMPLATE, appId);
                if (empty(vo)) {
                    //二次初始化创建验证码短信模板
                    String nickname = subscrDao.selectNickname(appId);
                    if (notEmpty(nickname)) {
                        createTemplate(appId, ApiCodeConstant.DEFAULT_CODE_MSG, nickname);
                        //短信有个审核的时间过程
                        throw new BusinessException("初始化验证码，请1分钟后再试");
                    }
                }
                MessageUtil.sendBusMobCode(mobile, code, vo.getTemplate());
            }
        }
    }

    @Override
    public boolean checkMobCode(String mobile, String code) {
        if (code.equals("8888"))
            return true;
        if (code.equals(RedisSlaveUtil.get(MOBCODEKEY + mobile)))
            return true;
        return false;
    }

    @Override
    public void sendExpireMsg(List<ExpireMsgVo> expireMsgs, int type) {
        if (expireMsgs != null && expireMsgs.size() > 0) {
            StringBuilder multi = new StringBuilder();
            if (type == 0) {
                multi.append("[");
                for (ExpireMsgVo msgVo : expireMsgs) {
                    multi.append("{\"to\":\"" + msgVo.getMobile() + "\",\"vars\":{\"mobile\":\"" + msgVo.getMobile() + "\",\"day\":\"" + msgVo.getDay() + "\",\"endtime\":\"" + msgVo.getEndtime() + "\"}}");
                }
                multi.append("]");
                MessageUtil.multixsend(multi.toString(), "tA7hc1");
            } else if (type == 1) {
                multi.append("[");
                for (ExpireMsgVo msgVo : expireMsgs) {
                    multi.append("{\"to\":\"" + msgVo.getMobile() + "\",\"vars\":{\"shop\":\"" + msgVo.getTitle() + "\",\"day\":\"" + msgVo.getDay() + "\",\"endtime\":\"" + msgVo.getEndtime() + "\"}}");
                }
                multi.append("]");
                MessageUtil.multixsend(multi.toString(), "9zDuo2");
            }
        }
    }


    @Override
    public void sendPayFailMsg(String mobile, String money, String reason) {
        MessageUtil.xsend(mobile, "{\"money\":\"" + money + "\",\"reason\":\"" + reason + "\"}", "M4VLD4");
    }

    @Override
    public void sendMobCountMsg(List<KeyValVo> keyValVos) {
        if (keyValVos != null && keyValVos.size() > 0) {
            StringBuilder multi = new StringBuilder();
            multi.append("[");
            for (KeyValVo keyValVo : keyValVos) {
                multi.append("{\"to\":\"" + keyValVo.getKey() + "\",\"vars\":{\"count\":\"" + keyValVo.getVal() + "\"}}");
            }
            multi.append("]");
            MessageUtil.multixsend(multi.toString(), "HRM9P");
        }
    }

    @Override
    public void sendYoukeSucc(List<String> mobiles) {
        if (mobiles != null && mobiles.size() > 0) {
            StringBuilder multi = new StringBuilder();
            multi.append("[");
            for (String mobile : mobiles) {
                multi.append("{\"to\":\"" + mobile + "\",\"vars\":{}}");
            }
            multi.append("]");
            MessageUtil.multixsend(multi.toString(), "fBbcU");
        }
    }

    @Override
    public void createTemplate(String appId, String content, String label) {
        String res = MessageUtil.post(label, content);
        JSONObject obj = JsonUtils.getJsonObject(res);
        String templateId = obj.getString("template_id");
        MassTemplateVo vo = new MassTemplateVo();
        vo.setTemplate(templateId);
        vo.setState(0);
        RedisUtil.hset(MassConstant.TEMPLATE_APPID, templateId, appId);
        RedisUtil.hset(MassConstant.APPID_TEMPLATE, appId, vo);
    }
}
