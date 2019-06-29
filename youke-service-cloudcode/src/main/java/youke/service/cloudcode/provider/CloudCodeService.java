package youke.service.cloudcode.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.cloudcode.CloudCodeRecordQueryVo;
import youke.common.model.vo.result.cloudcode.CloudCodeAuditRetVo;
import youke.common.model.vo.param.cloudcode.CloudCodeQueryVo;
import youke.common.model.vo.param.cloudcode.CloudCodeSaveVo;
import youke.common.model.vo.result.cloudcode.CloudCodeQueryRetVo;
import youke.common.model.vo.result.cloudcode.CloudCodeRecordQueryRetVo;
import youke.common.model.vo.result.cloudcode.H5CloudCodeQrCodeRetVo;
import youke.facade.cloudcode.provider.ICloudCodeService;
import youke.service.cloudcode.biz.ICloudCodeBiz;

import javax.annotation.Resource;

@Service
public class CloudCodeService extends Base implements ICloudCodeService {
    @Resource
    private ICloudCodeBiz cloudCodeBiz;

    @Override
    public Long saveCloudCode(CloudCodeSaveVo params, String dykId, String appId) {
        if (notEmpty(params) && notEmpty(dykId) && notEmpty(appId)) {
            params.setDykId(dykId);
            params.setAppId(appId);
            return cloudCodeBiz.saveCloudCode(params);
        } else {
            throw new BusinessException("无效的请求或参数");
        }
    }

    @Override
    public PageInfo<CloudCodeQueryRetVo> getCloudCodeList(CloudCodeQueryVo params, String dykId) {
        if (empty(params) && empty(dykId) ) {
            throw new BusinessException("无效的请求或参数");
        }
        params.setDykId(dykId);
        return cloudCodeBiz.getCloudCodeList(params);
    }

    @Override
    public CloudCodeAuditRetVo auditCloudCode(Long id) {
        if (empty(id)) {
            throw new BusinessException("无效的请求或参数");
        }
        return cloudCodeBiz.getCloudCodeData(id);
    }

    @Override
    public void dropCloudCode(Long id) {
        if (empty(id)) {
            throw new BusinessException("无效的请求或参数");
        }
        cloudCodeBiz.doDropCloudCode(id);
    }

    @Override
    public void deleteCloudCode(Long id) {
        if (empty(id)) {
            throw new BusinessException("无效的请求或参数");
        }
        cloudCodeBiz.deleteCloudCode(id);
    }

    @Override
    public PageInfo<CloudCodeRecordQueryRetVo> getRecordList(CloudCodeRecordQueryVo params, String dykId) {
        if (empty(params) && empty(dykId) ) {
            throw new BusinessException("无效的参数或请求");
        }
        params.setDykId(dykId);
        return cloudCodeBiz.getRecordList(params);
    }

    @Override
    public H5CloudCodeQrCodeRetVo getCloudCode(Long id, String openId) {
        if (empty(openId) && empty(id)) {
            throw new BusinessException("无效的请求或参数");
        }
        return cloudCodeBiz.doGetCloudCodeAndSaveRecord( openId, id);
    }

    @Override
    public String getConfig(String key) {
        return empty(key) ? "" : cloudCodeBiz.getConfig(key);
    }
}
