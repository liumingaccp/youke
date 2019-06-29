package youke.facade.mass.provider;


import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import youke.common.model.TMobcodeDetail;
import youke.common.model.TMobcodeTemplete;
import youke.common.model.vo.param.*;
import youke.common.model.vo.result.MassRecordRetVo;
import youke.common.model.vo.result.MassTempListVo;
import youke.common.model.vo.result.MobcodeRecordVo;

import java.util.List;

public interface IMassSMSService {

    /**
     * 分页获取模板
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<TMobcodeTemplete> getTemplateList(MassSMSQueryVo params, String dykId);

    /**
     * 获取模板名称
     *
     * @param params
     * @param dykId
     * @return
     */
    List<String> getTemplateNames(MassSMSQueryVo params, String dykId);

    /**
     * 短信群发记录分页获取
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<MobcodeRecordVo> getRecordList(MassSMSQueryVo params, String dykId);

    /**
     * 获取发送人员名单
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<MassTempListVo> getShopSendList(MassSMSParamVo params, String dykId, String appId);

    /**
     * 用户短信模板保存
     *
     * @param params
     * @param dykId
     */
    void saveTemplate(MassTemplateParamVo params, String dykId);

    /**
     * 短信发送记录明细获取
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<TMobcodeDetail> getRecordDetailList(MassRecordDetailQueryVo params, String dykId);

    /**
     * 普通任务发送保存
     *
     * @param params
     * @param dykId
     * @param appId
     */
    void saveSendTask(MassSMSParamVo params, String dykId, String appId);

    /**
     * 会员短信群发保存发送任务
     *
     * @param params
     * @param dykId
     * @param appId
     */
    void saveShopSendTask(MassSMSParamVo params, String dykId, String appId);

    /**
     * 短信回调
     *
     * @param message
     */
    void callback(Message message);

    /**
     * 删除短信模板
     *
     * @param id
     */
    void deleteTemplate(int id);

    /**
     * 删除群发记录
     *
     * @param id
     */
    void deleteRecord(int id);

    /**
     * 获取短信记录列表
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<MassRecordRetVo> getMassList(MassQueryVo params, String dykId);

    /**
     * 取消短信发送
     *
     * @param params
     */
    void doCancelSend(JSONObject params);
}
