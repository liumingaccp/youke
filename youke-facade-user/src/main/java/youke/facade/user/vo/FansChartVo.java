package youke.facade.user.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 12:10
 */
public class FansChartVo implements Serializable {
    private List<FansChartDateVo> list;

    public void setList(List<FansChartDateVo> list) {
        this.list = list;
    }

    public List<FansChartDateVo> getList() {
        return list;
    }
}
