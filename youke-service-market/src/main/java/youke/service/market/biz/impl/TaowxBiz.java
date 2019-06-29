package youke.service.market.biz.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.dao.ITaowxPosterDao;
import youke.common.model.TTaowxPoster;
import youke.common.model.vo.page.PageModel;
import youke.common.model.vo.result.TaowxPoterVo;
import youke.service.market.biz.ITaowxBiz;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class TaowxBiz extends Base implements ITaowxBiz {

    @Resource
    private ITaowxPosterDao taowxPosterDao;

    public int addTaowxPoster(String url, String shortUrl,String youkeId) {
        TTaowxPoster poster = new TTaowxPoster();
        poster.setCreatetime(new Date());
        poster.setShorturl(shortUrl);
        poster.setSourceurl(url);
        poster.setYoukeid(youkeId);
        taowxPosterDao.insertSelective(poster);
        return poster.getId();
    }

    public PageInfo<TaowxPoterVo> getTaoWxList(int page, int limit, String timeStart, String timeEnd, String dykId) {
        PageHelper.startPage(page, limit, "createTime desc");
        List<TTaowxPoster> list =  taowxPosterDao.queryPage(timeStart, timeEnd, dykId);
        PageModel<TaowxPoterVo> model = new PageModel<TaowxPoterVo>((Page) list);
        if(list != null && list.size() > 0){
            for(TTaowxPoster info : list){
                TaowxPoterVo vo = new TaowxPoterVo();
                vo.setCreateTime(info.getCreatetime());
                vo.setPosterId(info.getId());
                vo.setShortUrl(info.getShorturl());
                vo.setSourceUrl(info.getSourceurl());
                vo.setBody(info.getBody());
                vo.setPosterUrl(info.getPosterurl());
                model.getResult().add(vo);
            }
        }

        return new PageInfo<TaowxPoterVo>(model);
    }

    public void savePoter(int posterId, String posterUrl, String body) {
        TTaowxPoster model = new TTaowxPoster();
        model.setId(posterId);
        model.setPosterurl(posterUrl);
        model.setBody(body);
        taowxPosterDao.updateByPrimaryKeySelective(model);
    }

    public TaowxPoterVo select(int id) {
        TaowxPoterVo vo = null;
        TTaowxPoster model = taowxPosterDao.selectByPrimaryKey(id);
        if(model != null){
            vo = new TaowxPoterVo();
            vo.setPosterId(model.getId());
            vo.setCreateTime(model.getCreatetime());
            vo.setPosterId(model.getId());
            vo.setShortUrl(model.getShorturl());
            vo.setSourceUrl(model.getSourceurl());
            vo.setBody(model.getBody());
            vo.setPosterUrl(model.getPosterurl());
        }
        return vo;
    }
}
