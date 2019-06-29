package youke.service.wx.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.exception.BusinessException;
import youke.common.model.vo.param.MassFansQueryVo;
import youke.common.model.vo.result.MassFansVo;
import youke.common.model.vo.result.MassRecordVo;
import youke.common.utils.DateUtil;
import youke.common.utils.StringUtil;
import youke.facade.wx.provider.IWeixinMassFansService;
import youke.facade.wx.vo.mass.TaskParam;
import youke.service.wx.biz.IWxMassFansBiz;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 19:29
 */
@Service
public class WeixinMassFansService implements IWeixinMassFansService {

    @Resource
    private IWxMassFansBiz wxMassFansBiz;

    public PageInfo<MassFansVo> getSendlist(MassFansQueryVo params, int type) {
        //参数控制
        if(params.getPage() == null || params.getPage() < 0){
            params.setPage(1);
        }
        return wxMassFansBiz.getSendlist(params, type);
    }

    public PageInfo<MassRecordVo> getRecordList(MassFansQueryVo params) {
        return wxMassFansBiz.getRecordList(params);
    }

    public int getSubScrtype(String appId) {
        return wxMassFansBiz.getSubScrtype(appId);
    }

    public long selectCount(MassFansQueryVo params, int type) {
        return wxMassFansBiz.getTotal(params,type);
    }

    public void saveSendTask(TaskParam params, int type) {
        String taskTime = params.getTaskTime();
        if(!StringUtil.hasLength(taskTime)){
            taskTime = DateUtil.formatDateTime(new Date());
            params.setTaskTime(taskTime);
        }
        switch (type){
            case 1 :
                wxMassFansBiz.saveHighSendTask(params);
                break;
            case 2 :
                wxMassFansBiz.saveTagSendTask(params);
                break;
            case 3:
                wxMassFansBiz.saveKefuSendTask(params);
                break;
            default:
                break;
        }
    }

    @Override
    public PageInfo<MassFansVo> getRecordFansList(Integer id, String appId, Integer page, Integer limit) {
        if(id == null || id < 0){
            throw new BusinessException("数据错误");
        }
        if(page == null || page < -2){
            page = 1;
        }
        if(limit == null || limit < -2){
            limit = 20;
        }
        return wxMassFansBiz.getRecordFansList(id, appId, page, limit);
    }
}
