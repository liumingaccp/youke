package youke.web.spread.common.base;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 业务基类
 */
public abstract class Base {
    //默认编码
    protected final String CHARSET_UTF8="UTF-8";
    //默认分割路径
    protected final String SEPARATOR = File.separator;
    protected final String BLANK = "";
    //默认,隔开
    protected final String DELIMITED = ",";
    //默认页数
    protected final int PAGESIZE = 20;
    

    /**Log object**/
    protected final Logger logger = LogManager.getLogger(getClass());

    protected Date getDate(){
    	return new Date();
    }

    protected boolean notEmpty(String s){
        return StringUtils.isNotBlank(s);
    }

    protected boolean empty(String s){
        return StringUtils.isBlank(s);
    }

    protected boolean notEmpty(Object obj){
        return null != obj;
    }

    protected boolean empty(Object obj){
        return null == obj;
    }

    protected boolean empty(List<?> list){
        return null == list || list.isEmpty();
    }

    protected boolean notEmpty(List<?> list){
        return null != list && ! list.isEmpty();
    }

    protected boolean empty(Map<?,?> map){
        return null == map || map.isEmpty();
    }

    protected boolean notEmpty(Map<?,?> map){
        return null != map && ! map.isEmpty();
    }

    protected static boolean empty(File file){
        return null == file || !file.exists();
    }

    protected boolean notEmpty(File file){
        return null != file && file.exists();
    }

    protected boolean empty(Object[] objs){
        return null == objs || 0 == objs.length;
    }

    protected boolean notEmpty(Object[] objs){
        return null != objs && 0 != objs.length;
    }
}
