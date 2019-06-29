package youke.service.user.provider;

import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.model.TArea;
import youke.common.model.vo.result.NewsVo;
import youke.common.model.vo.result.TagVo;
import youke.facade.user.provider.IAreaService;
import youke.facade.user.vo.AreaVo;
import youke.service.user.biz.IAreaBiz;
import youke.service.user.biz.INewsBiz;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class AreaService implements IAreaService {

    @Resource
    private IAreaBiz areaBiz;
    @Resource
    private INewsBiz newsBiz;

    public List<AreaVo> getAreas(int pid) {
        List<AreaVo> list = new ArrayList<AreaVo>();
        if(pid >= 0 && pid < 1000){
            List<TArea> areas = areaBiz.getAreas(pid);
            if(areas != null && areas.size() > 0){
                for(TArea info : areas){
                    AreaVo vo = new AreaVo();
                    vo.setId(info.getId());
                    vo.setTitle(info.getTitle());
                    list.add(vo);
                }
            }
            return list;
        }else{
            return null;
        }

    }

    @Override
    public List<TagVo> getNewsType() {
        return newsBiz.getNewsType();
    }

    @Override
    public PageInfo<NewsVo> getNewsList(int typeId, int page, int limit) {
        return newsBiz.getNewsList(typeId,page,limit);
    }

    @Override
    public NewsVo getNewsInfo(int id) {
        return newsBiz.getNewsInfo(id);
    }


}
