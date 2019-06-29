package youke.facade.user.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/10
 * Time: 10:27
 */
public class SubFansInfo implements Serializable {
    private Integer hasSubscr = 0;
    private Integer hasMobile = 0;
    private Integer integral = 0;
    private String mobile = "";
    private List<String> icons = new ArrayList<>();

    public Integer getHasSubscr() {
        return hasSubscr;
    }

    public void setHasSubscr(Integer hasSubscr) {
        this.hasSubscr = hasSubscr;
    }

    public Integer getHasMobile() {
        return hasMobile;
    }

    public void setHasMobile(Integer hasMobile) {
        this.hasMobile = hasMobile;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setIcons(List<String> icons) {
        this.icons = icons;
    }

    public List<String> getIcons() {
        return icons;
    }
}
