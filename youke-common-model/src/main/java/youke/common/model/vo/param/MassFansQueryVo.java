package youke.common.model.vo.param;

import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/8
 * Time: 19:38
 */
public class MassFansQueryVo extends WxFansBaseQueryVo {
    private Integer tagFilter;
    private Integer limitSend;
    private String subTimeBeg;
    private String subTimeEnd;
    private String mouthStartTime;
    //微信互动超时时间
    private Date wxTimeOut;

    public MassFansQueryVo() {
    }

    public MassFansQueryVo(String tagIds) {
        this.tagIds = tagIds;
        this.setTags(tagIds);
    }

    public void setWxTimeOut(Date wxTimeOut) {
        this.wxTimeOut = wxTimeOut;
    }

    public Date getWxTimeOut() {
        return wxTimeOut;
    }

    public Integer getTagFilter() {
        return tagFilter;
    }

    public void setTagFilter(Integer tagFilter) {
        this.tagFilter = tagFilter;
    }

    public Integer getLimitSend() {
        return limitSend;
    }

    public void setLimitSend(Integer limitSend) {
        this.limitSend = limitSend;
    }

    public String getSubTimeBeg() {
        return StringUtils.hasLength(this.subTimeBeg) ? subTimeBeg : null;
    }

    public void setSubTimeBeg(String subTimeBeg) {
        this.subTimeBeg = subTimeBeg;
    }

    public String getSubTimeEnd() {
        return StringUtils.hasLength(this.subTimeEnd) ? subTimeEnd : null;
    }

    public void setSubTimeEnd(String subTimeEnd) {
        this.subTimeEnd = subTimeEnd;
    }

    public String getMouthStartTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();//获取当前日期
        //cal.add(Calendar.MONTH, -1);
        cal.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        Date time = cal.getTime();
        String dateStr = format.format(time);
        return dateStr;
    }

    public static void main(String[] a) {
        System.out.print(new MassFansQueryVo().getMouthStartTime());

    }
}
