package youke.common.model.vo.result.helper;

import youke.common.model.TTrialActiveOrderPic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/3/9
 * Time: 15:11
 */
public class H5TtrialOrderDetailRetVo implements Serializable {
    private List<TTrialActiveOrderPic> pics;

    public void setPics(List<TTrialActiveOrderPic> pics) {
        this.pics = pics;
    }

    public List<TTrialActiveOrderPic> getPics() {
        return pics;
    }
}
