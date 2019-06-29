package youke.facade.wx.provider;

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
 * Time: 19:23
 */
public interface IWeixinMassFansService {

    /**
     * @param params
     * @param type
     *      1 : 表示 普通群发和高级群发,占用次数
     *      2 : 表示 客服(互动)群发, 不占次数
     * @return
     */
    long selectCount(MassFansQueryVo params, int type);

    /**
     * @param params
     * @param type
     *      1 : 表示高级群发任务保存
     *      2 : 表示普通群发任务保存
     *      3 : 表示互动群发任务保存
     */
    void saveSendTask(TaskParam params, int type);

    /**
     * @param params
     * @param type
     *      1 : 表示 高级群发,占用次数
     *      2 : 表示 普通群发,占用次数
     *      2 : 表示 客服(互动)群发, 不占次数
     * @return
     */
    PageInfo<MassFansVo> getSendlist(MassFansQueryVo params, int type);

    PageInfo<MassRecordVo> getRecordList(MassFansQueryVo params);

    /**
     * 获取群发的公众号类型
     * @param appId
     * @return
     */
    int getSubScrtype(String appId);

    /**
     * 查询群发人数
     * @param id
     * @param appId
     * @return
     */
    PageInfo<MassFansVo> getRecordFansList(Integer id, String appId, Integer page, Integer limit);
}
