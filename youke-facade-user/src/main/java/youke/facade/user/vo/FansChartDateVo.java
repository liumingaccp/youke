package youke.facade.user.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 12:09
 */
public class FansChartDateVo implements Serializable{
    private String name;
    private int val;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }
}
