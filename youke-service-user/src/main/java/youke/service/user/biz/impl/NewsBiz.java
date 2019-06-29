package youke.service.user.biz.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.dao.IAreaDao;
import youke.common.model.vo.result.NewsVo;
import youke.common.model.vo.result.TagVo;
import youke.service.user.biz.INewsBiz;

import javax.annotation.Resource;
import java.util.List;

@Service
public class NewsBiz implements INewsBiz {

    @Resource
    private IAreaDao areaDao;

    @Override
    public List<TagVo> getNewsType() {
        return areaDao.selectNewsType();
    }

    @Override
    public PageInfo<NewsVo> getNewsList(int typeId, int page, int limit) {
        PageHelper.startPage(page, limit, "a.createTime DESC");
        List<NewsVo> newsVoList = null;
        if(typeId==-1)
            newsVoList = areaDao.selectNewsList();
        else
            newsVoList = areaDao.selectNewsListByTypeId(typeId);
        for (NewsVo vo:newsVoList) {
            vo.setCreateTime(vo.getCreateTime().split(" ")[0]);
            vo.setCover(vo.getCover().replace("http://","https://"));
        }
        PageInfo<NewsVo> newsPage = new PageInfo<>(newsVoList);
        return newsPage;
    }

    @Override
    public NewsVo getNewsInfo(int id) {
        NewsVo vo =  areaDao.selectNewsInfo(id);
        vo.setCreateTime(vo.getCreateTime().split(" ")[0]);
        vo.setCover(vo.getCover().replace("http://","https://"));
        vo.setContent(vo.getContent().replace("http://","https://"));
        return vo;
    }

}
