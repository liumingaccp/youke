package youke.facade.wx.vo.material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SysNewsTreeVo implements Serializable {

    private SysnewsVo news;
    private List<SysnewsVo> subNews = new ArrayList<SysnewsVo>();

    public SysnewsVo getNews() {
        return news;
    }

    public void setNews(SysnewsVo news) {
        this.news = news;
    }

    public List<SysnewsVo> getSubNews() {
        return subNews;
    }

    public void setSubNews(List<SysnewsVo> subNews) {
        this.subNews = subNews;
    }

}
