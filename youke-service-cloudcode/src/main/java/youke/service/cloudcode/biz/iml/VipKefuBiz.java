package youke.service.cloudcode.biz.iml;

import org.springframework.stereotype.Service;
import youke.common.dao.IVipKefuDao;
import youke.common.exception.BusinessException;
import youke.common.model.TVipKefu;
import youke.common.utils.StringUtil;
import youke.service.cloudcode.biz.IVipKefuBiz;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/5/31
 * Time: 18:29
 */
@Service
public class VipKefuBiz implements IVipKefuBiz {
    @Resource
    private IVipKefuDao vipKefuDao;
    @Override
    public void saveVipKefu(String url, String remark, String appId, String dykId) {
        Map map = vipKefuDao.selectUrlByAppId(appId);
        if(map != null){
            Object url1 = map.get("url");
            if(url1 != null){
                vipKefuDao.deleteByAppId(appId);
            }
        }
        TVipKefu kefu = new TVipKefu();
        kefu.setUrl(url);
        kefu.setRemark(remark);
        kefu.setCreateTime(new Date());
        kefu.setAppId(appId);
        kefu.setYoukeId(dykId);
        vipKefuDao.insertSelective(kefu);
    }

    @Override
    public Map selectUrl(String appId) {
        return vipKefuDao.selectUrlByAppId(appId);
    }

    @Override
    public void delVipKefu(String appId) {
        vipKefuDao.deleteByAppId(appId);
    }
}
