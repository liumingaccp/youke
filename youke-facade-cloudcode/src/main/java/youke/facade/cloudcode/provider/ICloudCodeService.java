package youke.facade.cloudcode.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.cloudcode.CloudCodeRecordQueryVo;
import youke.common.model.vo.result.cloudcode.CloudCodeAuditRetVo;
import youke.common.model.vo.param.cloudcode.CloudCodeQueryVo;
import youke.common.model.vo.param.cloudcode.CloudCodeSaveVo;
import youke.common.model.vo.result.cloudcode.CloudCodeQueryRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeRecordQueryRetVo;
import youke.common.model.vo.result.cloudcode.H5CloudCodeQrCodeRetVo;

public interface ICloudCodeService {

    /**
     * 云码保存
     *
     * @param params
     * @param dykId
     * @param appId
     */
    Long saveCloudCode(CloudCodeSaveVo params, String dykId, String appId);

    /**
     * 获取云码列表
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<CloudCodeQueryRetVo> getCloudCodeList(CloudCodeQueryVo params, String dykId);

    /**
     * 云码编辑|查看 页面数据获取
     *
     * @param id
     * @return
     */
    CloudCodeAuditRetVo auditCloudCode(Long id);

    /**
     * 云码作废
     *
     * @param id
     */
    void dropCloudCode(Long id);

    /**
     * 云码删除
     *
     * @param id
     */
    void deleteCloudCode(Long id);

    /**
     * 获取扫码记录
     *
     * @param params
     * @param dykId
     * @return
     */
    PageInfo<CloudCodeRecordQueryRetVo> getRecordList(CloudCodeRecordQueryVo params, String dykId);

    /**
     * 获取配置表对应数据
     *
     * @param key
     * @return
     */
    String getConfig(String key);

    /**
     * H5获取云码
     *
     * @param id
     * @param openId
     * @return
     */
    H5CloudCodeQrCodeRetVo getCloudCode(Long id, String openId);
}
