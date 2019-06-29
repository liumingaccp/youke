package youke.service.user.biz;

import youke.common.model.TArea;
import youke.common.model.vo.result.NewsVo;
import youke.common.model.vo.result.TagVo;

import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/23
 * Time: 9:03
 */
public interface IAreaBiz {
    List<TArea> getAreas(int pid);
}
