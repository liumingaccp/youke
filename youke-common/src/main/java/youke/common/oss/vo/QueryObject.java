package youke.common.oss.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Created By lhf
 * Date: 2017/12/15
 * Time: 10:43
 * 分页查询的对象
 */
public class QueryObject {

    private static int DEFULTE_MAXKEYS = 100;

    //前缀
    private String prefix = "";
    //分隔符.只会查找第一次出现的,如果有多个就不会查出来,用来分隔目录的,前缀必须要有才行
    private String delimiter = "";
    //标记的起始位置,分隔的作用
    private String marker = "";
    //下一个起始位置
    private String nextMarker = "";
    //单页的条数,默认100
    private int maxKeys = DEFULTE_MAXKEYS;

    public static int getDefulteMaxkeys() {
        return DEFULTE_MAXKEYS;
    }

    public static void setDefulteMaxkeys(int defulteMaxkeys) {
        DEFULTE_MAXKEYS = defulteMaxkeys;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getNextMarker() {
        return nextMarker;
    }

    public void setNextMarker(String nextMarker) {
        this.nextMarker = nextMarker;
    }

    public int getMaxKeys() {
        return maxKeys;
    }

    public void setMaxKeys(int maxKeys) {
        this.maxKeys = maxKeys;
    }
}
