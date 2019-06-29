package youke.service.mass.biz;

import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import youke.common.model.TMobcodeDetail;
import youke.common.model.TMobcodeTemplete;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.MassRecordRetVo;
import youke.common.model.vo.result.MassTempListVo;
import youke.common.model.vo.result.MobcodeRecordVo;
import youke.facade.mass.vo.MassSMSMessage;

import java.util.List;

public interface IMassSMSBiz {

    /**
     * 分页获取短信模板
     *
     * @param params
     * @return
     */
    PageInfo<TMobcodeTemplete> getTemplateList(MassSMSQueryVo params);

    /**
     * 获取模板名称列表
     *
     * @param params
     * @return
     */
    List<String> getTemplateNames(MassSMSQueryVo params);

    /**
     * 短信群发记录分页获取
     *
     * @param params
     * @return
     */
    PageInfo<MobcodeRecordVo> getRecordList(MassSMSQueryVo params);

    /**
     * 获取短信发送人员名单
     *
     * @param params
     * @return
     */
    PageInfo<MassTempListVo> getShopSendList(MassSMSParamVo params);

    /**
     * 用户短信模板保存
     *
     * @param params
     * @param dykId
     */
    void saveTemplate(MassTemplateParamVo params, String dykId);

    /**
     * 短信发送记录明细分页获取
     *
     * @param params
     * @return
     */
    PageInfo<TMobcodeDetail> getRecordDetailList(MassRecordDetailQueryVo params);

    /**
     * 请求发送成功
     *
     * @param message
     * @param massMessage
     */
    void saverequest(Message message, MassSMSMessage massMessage);

    /**
     * 短信发送成功
     *
     * @param message
     * @param massMessage
     */
    void savedelivered(Message message, MassSMSMessage massMessage);

    /**
     * 短信发送失败
     *
     * @param message
     * @param massMessage
     */
    void savedropped(Message message, MassSMSMessage massMessage);

    /**
     * 短信正在发送
     *
     * @param message
     * @param massMessage
     */
    void savesending(Message message, MassSMSMessage massMessage);


    /**
     * 短信上行推送到运营商
     *
     * @param message
     * @param massMessage
     */
    void savemo(Message message, MassSMSMessage massMessage);

    /**
     * 发生未知错误
     *
     * @param message
     * @param massMessage
     */
    void saveunkown(Message message, MassSMSMessage massMessage);

    /**
     * 删除短信模板
     *
     * @param id
     */
    void deleteTemplate(int id);

    /**
     * 删除短信群发记录
     *
     * @param id
     */
    void deleteRecord(int id);

    /**
     * 短信模板审核成功
     *
     * @param message
     */
    void savetemplate_accept(Message message);

    /**
     * 短信模板审核失败
     *
     * @param message
     */
    void savetemplate_reject(Message message);

    /**
     * 获取短信记录列表
     *
     * @param params
     * @return
     */
    PageInfo<MassRecordRetVo> getMassList(MassQueryVo params);

    /**
     * 取消短信发送
     *
     * @param params
     */
    void doCancelSend(JSONObject params);

    /**
     * 普通短信发送
     *
     * @param params
     */
    void saveSendTask(MassSMSParamVo params);

    /**
     * 会员短信发送
     *
     * @param params
     */
    void saveMemberSendTask(MassSMSParamVo params);

    /**
     * 请求模板
     *
     * @param queuemessage
     */
    void doPostTemplate(MassSMSMessage queuemessage);

    /**
     * 普通群发
     *
     * @param message
     */
    void doMass(MassSMSMessage message) throws Exception;

    /**
     * 会员群发
     *
     * @param message
     */
    void doMemberMass(MassSMSMessage message) throws Exception;
}
