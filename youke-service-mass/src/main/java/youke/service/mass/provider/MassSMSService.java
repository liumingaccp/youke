package youke.service.mass.provider;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.TMobcodeDetail;
import youke.common.model.TMobcodeTemplete;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.MassRecordRetVo;
import youke.common.model.vo.result.MassTempListVo;
import youke.common.model.vo.result.MobcodeRecordVo;
import youke.common.redis.RedisSlaveUtil;
import youke.common.utils.MD5Util;
import youke.common.utils.MessageUtil;
import youke.facade.mass.provider.IMassSMSService;
import youke.facade.mass.vo.MassSMSMessage;
import youke.service.mass.biz.IMassSMSBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MassSMSService extends Base implements IMassSMSService {

    @Resource
    private IMassSMSBiz massSMSBiz;

    public PageInfo<TMobcodeTemplete> getTemplateList(MassSMSQueryVo params, String dykId) {
        if (notEmpty(dykId) && notEmpty(params)) {
            params.setDykId(dykId);
            return massSMSBiz.getTemplateList(params);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public List<String> getTemplateNames(MassSMSQueryVo params, String dykId) {
        if (notEmpty(dykId) && notEmpty(params)) {
            params.setDykId(dykId);
            return massSMSBiz.getTemplateNames(params);
        } else {
            throw new BusinessException("无效的参数或请求");
        }
    }

    public PageInfo<MobcodeRecordVo> getRecordList(MassSMSQueryVo params, String dykId) {
        if (notEmpty(params) && notEmpty(dykId)) {
            params.setDykId(dykId);
            return massSMSBiz.getRecordList(params);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public PageInfo<MassTempListVo> getShopSendList(MassSMSParamVo params, String dykId, String appId) {
        if (notEmpty(dykId) && notEmpty(params) && notEmpty(appId)) {
            params.setDykId(dykId);
            params.setAppId(appId);
            return massSMSBiz.getShopSendList(params);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void saveSendTask(MassSMSParamVo params, String dykId, String appId) {
        if (notEmpty(params) && notEmpty(dykId) && notEmpty(appId)) {
            if (empty(params.getTaskTime())) {
                throw new BusinessException("请设置发送时间");
            }
            params.setDykId(dykId);
            params.setAppId(appId);
            params.setType(0);
            massSMSBiz.saveSendTask(params);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    @Override
    public void saveShopSendTask(MassSMSParamVo params, String dykId, String appId) {
        if (notEmpty(params) && notEmpty(dykId) && notEmpty(appId)) {
            if (empty(params.getTaskTime())) {
                throw new BusinessException("请设置发送时间");
            }
            params.setDykId(dykId);
            params.setAppId(appId);
            params.setType(1);
            massSMSBiz.saveMemberSendTask(params);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    public void saveTemplate(MassTemplateParamVo params, String dykId) {
        if (empty(dykId) && empty(params))
            throw new BusinessException("无效的请求或参数");
        params.setRole(1);//用户模板
        massSMSBiz.saveTemplate(params, dykId);
    }

    public PageInfo<TMobcodeDetail> getRecordDetailList(MassRecordDetailQueryVo params, String dykId) {
        if (empty(params) && empty(dykId))
            throw new BusinessException("无效的请求或参数");
        params.setDykId(dykId);
        return massSMSBiz.getRecordDetailList(params);
    }

    @Override
    public void deleteTemplate(int id) {
        if (empty(id))
            throw new BusinessException("请选择要删除的模板");
        massSMSBiz.deleteTemplate(id);
    }

    @Override
    public void deleteRecord(int id) {
        if (empty(id))
            throw new BusinessException("请选择要删除的模板");
        massSMSBiz.deleteRecord(id);
    }

    @Override
    public PageInfo<MassRecordRetVo> getMassList(MassQueryVo params, String dykId) {
        if (notEmpty(params) && notEmpty(dykId)) {
            params.setDykId(dykId);
            return massSMSBiz.getMassList(params);
        }
        throw new BusinessException("无效的请求或参数");
    }

    @Override
    public void doCancelSend(JSONObject params) {
        if (empty(params.getInt("id"))) {
            throw new BusinessException("请选择要取消的任务");
        } else {
            massSMSBiz.doCancelSend(params);
        }
    }

    @Override
    public void callback(Message message) {

        String event = message.getEvents();
        String send_id = message.getSend_id();
        String signature = message.getSignature();
        String token = message.getToken();
        String key = MessageUtil.getKey();
        boolean result = MD5Util.MD5(token + key).equals(signature);
        if (result) {
            MassSMSMessage massMessage = null;
            if (RedisSlaveUtil.hasKey("SEND_HISTORY") && send_id != null) {
                massMessage = (MassSMSMessage) RedisSlaveUtil.hget("SEND_HISTORY", send_id);
            }
            switch (event) {
                case "request":
                    massSMSBiz.saverequest(message, massMessage);
                    break;
                case "delivered":
                    massSMSBiz.savedelivered(message, massMessage);
                    break;
                case "dropped":
                    massSMSBiz.savedropped(message, massMessage);
                    break;
                case "sending":
                    massSMSBiz.savesending(message, massMessage);
                    break;
                case "mo":
                    massSMSBiz.savemo(message, massMessage);
                    break;
                case "unkown":
                    massSMSBiz.saveunkown(message, massMessage);
                    break;
                case "template_accept":
                    massSMSBiz.savetemplate_accept(message);
                    break;
                case "template_reject":
                    massSMSBiz.savetemplate_reject(message);
                    break;
            }
        } else {
            throw new BusinessException("签名认证失败!");
        }
    }
}
