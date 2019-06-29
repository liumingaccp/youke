package youke.common.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.List;

public class JsonUtils {

    /**
     * JSON字符串转Bean对象
     *
     * @param json
     * @param t
     * @return
     */
    public static <T> T JsonToBean(String json, Class<T> t) throws Exception {
        JSONObject jsonObject = null;
        T bean = null;
        jsonObject = JSONObject.fromObject(json);
        bean = (T) JSONObject.toBean(jsonObject, t);
        return bean;
    }

    /**
     * JSON字符串转List
     *
     * @param json
     * @return
     */
    public static List JsonToList(String json) throws Exception {
        JSONArray jsonArray = null;
        List list = null;
        jsonArray = (JSONArray) JSONSerializer.toJSON(json);
        return (List) JSONSerializer.toJava(jsonArray);
    }

    /**
     * JSON字符串转JSONArray
     *
     * @param json
     * @return
     */
    public static JSONArray JsonToJSONArray(String json) throws Exception {
        return JSONArray.fromObject(json);
    }

    /**
     * 对象转JSON
     *
     * @param object
     * @return
     */
    public static String BeanToJson(Object object) throws Exception {
        JSONObject jsonObject = JSONObject.fromObject(object);
        return jsonObject.toString();
    }

    /**
     * List转JSON
     *
     * @return
     */
    public static String ListToJson(List list) throws Exception {
        JSONArray json = new JSONArray();
        json.addAll(list);
        return json.toString();
    }

    /**
     * String转JSONObject
     *
     * @param str
     * @return
     */
    public static JSONObject getJsonObject(String str) {
        return JSONObject.fromObject(str);
    }

    /**
     * 根据key获取单层嵌套的值
     *
     * @param str
     * @param key
     * @return
     */
    public static String getString(String str, String key) {
        return JSONObject.fromObject(str).getString(key);
    }

    /**
     * 根据key获取双层嵌套的值
     *
     * @param str
     * @param keyOne
     * @param keyTwo
     * @return
     */
    public static String getString(String str, String keyOne, String keyTwo) {
        return JSONObject.fromObject(getJsonObject(str).getString(keyOne)).getString(keyTwo);
    }
}
