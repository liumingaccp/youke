package youke.facade.user.vo;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/2/28
 * Time: 11:45
 */
public class CommunityDataVo implements Serializable {
    private FansDataVo zRNewFansNum;
    private FansDataVo zRActiveFansNum;
    private FansDataVo weekREFansNum;
    private FansDataVo weekSTOFansNum;
    private FansDataVo weekSaleNum;
    private FansDataVo weekREPercent;

    public FansDataVo getzRNewFansNum() {
        return zRNewFansNum;
    }

    public void setzRNewFansNum(FansDataVo zRNewFansNum) {
        this.zRNewFansNum = zRNewFansNum;
    }

    public FansDataVo getzRActiveFansNum() {
        return zRActiveFansNum;
    }

    public void setzRActiveFansNum(FansDataVo zRActiveFansNum) {
        this.zRActiveFansNum = zRActiveFansNum;
    }

    public FansDataVo getWeekREFansNum() {
        return weekREFansNum;
    }

    public void setWeekREFansNum(FansDataVo weekREFansNum) {
        this.weekREFansNum = weekREFansNum;
    }

    public FansDataVo getWeekSTOFansNum() {
        return weekSTOFansNum;
    }

    public void setWeekSTOFansNum(FansDataVo weekSTOFansNum) {
        this.weekSTOFansNum = weekSTOFansNum;
    }

    public FansDataVo getWeekSaleNum() {
        return weekSaleNum;
    }

    public void setWeekSaleNum(FansDataVo weekSaleNum) {
        this.weekSaleNum = weekSaleNum;
    }

    public FansDataVo getWeekREPercent() {
        return weekREPercent;
    }

    public void setWeekREPercent(FansDataVo weekREPercent) {
        this.weekREPercent = weekREPercent;
    }
}
