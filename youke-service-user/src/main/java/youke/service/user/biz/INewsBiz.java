package youke.service.user.biz;

import com.github.pagehelper.PageInfo;
import youke.common.model.TArea;
import youke.common.model.vo.result.NewsVo;
import youke.common.model.vo.result.TagVo;

import java.util.List;


public interface INewsBiz {

    List<TagVo> getNewsType();

    PageInfo<NewsVo> getNewsList(int typeId, int page, int limit);

    NewsVo getNewsInfo(int id);
}
