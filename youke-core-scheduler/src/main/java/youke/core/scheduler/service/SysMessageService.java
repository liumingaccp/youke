package youke.core.scheduler.service;

import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.constants.ApiCodeConstant;
import youke.common.dao.*;
import youke.common.model.TCloudCode;
import youke.common.model.TYouke;
import youke.common.model.vo.result.cloudcode.CloudCodeNoticeVo;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;

@Service
public class SysMessageService extends Base {
    @Resource
    private IShopDao shopDao;
    @Resource
    private IYoukeDao youkeDao;
    @Resource
    private ICloudCodeDao cloudCodeDao;
    @Resource
    private ISysMessageDao sysMessageDao;
    @Resource
    private IMarketActiveRecordDao marketActiveRecordDao;

    /**
     * 添加营销活动审核提醒
     */
    public void addMarketAuditMessage() {
        List<String> youkeIds = youkeDao.selectIds();
        for (String youkeId : youkeIds) {
            int num = marketActiveRecordDao.selectWaitCheckNum(youkeId);
            if (num > 0) {
                String url = ApiCodeConstant.DOMAIN_PC + "#/yingxiao/participation-record";
                sysMessageDao.addMessage("审核提醒", "您好,您有"
                        + num + "位用户提交了活动信息,请尽快审核,点击"
                        + "<a href=\"" + url + "\" style=\"color:#007ffc\">审核</a>"
                        + "进入审核页面", youkeId);
            }
        }
    }

    /**
     * 添加云码过期提醒
     */
    public void addCloudCodeExpireMessage() {
        List<CloudCodeNoticeVo> codes = cloudCodeDao.selectOverLoadCode();
        TCloudCode CloudCode;
        if (codes.size() > 0) {
            HashSet<Long> ids = new HashSet<>();
            for (CloudCodeNoticeVo code : codes) {
                ids.add(code.getId());
            }
            StringBuilder sb;
            for (Long id : ids) {
                sb = new StringBuilder(100);
                CloudCode = cloudCodeDao.selectByPrimaryKey(id);
                sb.append("营销云码【").append(CloudCode.getTitle()).append("】，");
                for (CloudCodeNoticeVo code : codes) {
                    if (id.equals(code.getId())) {
                        sb.append("【").append(code.getRemark()).append("】被添加满【").append(code.getTotalTimes()).append("】人，");
                    }
                }
                sb.append("请及时编辑该营销云码。");
                sysMessageDao.addMessage("操作提醒", sb.toString(), CloudCode.getYoukeid());
            }
        }
    }

    /**
     * 添加店铺绑定提醒
     */
    public void addBindShopMessage() {
        List<TYouke> youkes = youkeDao.selectAll();
        for (TYouke youke : youkes) {
            int num = shopDao.selectShopNum(youke.getId());
            if (num == 0) {
                sysMessageDao.addMessage("店铺绑定提醒", "您好，您还暂未绑定店铺，点击账号管理-店铺授权，接入您的店铺，同步订单数据。", youke.getId());
            }
        }
    }
}
