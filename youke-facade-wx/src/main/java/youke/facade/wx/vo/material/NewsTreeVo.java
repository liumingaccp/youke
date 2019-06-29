package youke.facade.wx.vo.material;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsTreeVo implements Serializable {

    private NewsVo news;
    private List<NewsVo> subNews = new ArrayList<NewsVo>();

    public NewsVo getNews() {
        return news;
    }

    public void setNews(NewsVo news) {
        this.news = news;
    }

    public List<NewsVo> getSubNews() {
        return subNews;
    }

    public void setSubNews(List<NewsVo> subNews) {
        this.subNews = subNews;
    }
}
