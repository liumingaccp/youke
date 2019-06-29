package youke.service.wx.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.param.MassFansQueryVo;
import youke.common.model.vo.result.MassFansVo;
import youke.common.model.vo.result.MassRecordVo;
import youke.facade.wx.vo.mass.TaskParam;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 19:50
 */
public interface IWxMassFansBiz {

    PageInfo<MassFansVo> getSendlist(MassFansQueryVo params, int type);

    void saveHighSendTask(TaskParam params);

    long getTotal(MassFansQueryVo params, int type);

    void saveTagSendTask(TaskParam params);

    void saveKefuSendTask(TaskParam params);

    PageInfo<MassRecordVo> getRecordList(MassFansQueryVo params);

    int getSubScrtype(String appId);

    PageInfo<MassFansVo> getRecordFansList(Integer id, String appId, Integer page, Integer limit);
}
