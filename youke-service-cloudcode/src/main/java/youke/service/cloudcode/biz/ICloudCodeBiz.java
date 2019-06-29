package youke.service.cloudcode.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.cloudcode.CloudCodeQueryVo;
import youke.common.model.vo.param.cloudcode.CloudCodeRecordQueryVo;
import youke.common.model.vo.param.cloudcode.CloudCodeSaveVo;
import youke.common.model.vo.result.cloudcode.CloudCodeAuditRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeQueryRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeRecordQueryRetVo;
import youke.common.model.vo.result.cloudcode.H5CloudCodeQrCodeRetVo;

public interface ICloudCodeBiz {

    /**
     * 云码保存
     *
     * @param params
     */
    Long saveCloudCode(CloudCodeSaveVo params);

    /**
     * 获取云码列表
     *
     * @param params
     * @return
     */
    PageInfo<CloudCodeQueryRetVo> getCloudCodeList(CloudCodeQueryVo params);

    /**
     * 云码编辑|查看 页面数据获取
     *
     * @param id
     * @return
     */
    CloudCodeAuditRetVo getCloudCodeData(Long id);

    /**
     * 云码作废
     *
     * @param id
     */
    void doDropCloudCode(Long id);

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
     * @return
     */
    PageInfo<CloudCodeRecordQueryRetVo> getRecordList(CloudCodeRecordQueryVo params);

    /**
     * 获取云码
     *
     * @param appId
     * @param openId
     * @param id
     * @return
     */
    H5CloudCodeQrCodeRetVo doGetCloudCodeAndSaveRecord(String openId, Long id);

    /**
     * 获取配置表对应数据
     *
     * @param key
     * @return
     */
    String getConfig(String key);

}
