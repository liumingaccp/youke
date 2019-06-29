package youke.facade.user.provider;

import com.github.pagehelper.PageInfo;
import youke.common.model.vo.result.NewsVo;
import youke.common.model.vo.result.TagVo;
import youke.facade.user.vo.AreaVo;

import java.util.List;

public interface IAreaService {

    List<AreaVo> getAreas(int pid);

    List<TagVo> getNewsType();

    PageInfo<NewsVo> getNewsList(int typeId, int page, int limit);

    NewsVo getNewsInfo(int id);
}
