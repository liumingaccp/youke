package youke.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlUtil {
    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式

    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式

    private static final String regEx_html = "<(\"[^\"]*\"|'[^']*'|[^'\">])*>"; // 定义HTML标签的正则表达式

    private static final String regEx_space = "\\s*|\t|\r|\n";//定义空格回车换行符

    private final static String regEx_img = "<(img.*?)(class.*?)(src.*?)>";   // 找出IMG标签

    private final static String regEx_p = "<(img.*?)(src.*?)(alt.*?)(title.*?)(height.*?)>"; // 找出表情标签

    private final static String regEx_a = "(<(a.*?href=.*?)>(.*?)</a>)"; // 找出<a>标签

    public final static String regEx_span = "<(span.*?)(style.*?)>(.*?)</span>"; // 找出<span></span>标签

    public final static String regEx_strong = "(<(a.*?href=.*?)>(.*?)</a>)"; // 找出<strong></strong>标签

    private final static String spanTagPre = "<span style=\"text-decoration:underline;\">"; // 找出<span>标签

    private final static String spanTagSuf = "</span>"; // 找出<span>标签

    private final static String strongTagPre = "<strong>"; // 找出<strong>标签

    private final static String strongTagSuf = "</strong>"; // 找出<strong>标签

    /**
     * html标签过滤
     *
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        Pattern p_space = Pattern.compile(regEx_space, Pattern.CASE_INSENSITIVE);
        Matcher m_space = p_space.matcher(htmlStr);
        htmlStr = m_space.replaceAll(""); // 过滤空格回车标签
        return htmlStr.trim(); // 返回文本字符串
    }

    /**
     * 获取html纯文本
     *
     * @param htmlStr
     * @return
     */
    public static String getTextFromHtml(String htmlStr) {
        htmlStr = delHTMLTag(htmlStr);
        htmlStr = htmlStr.replaceAll("&nbsp;", "");
        htmlStr = htmlStr.substring(0, htmlStr.indexOf("。") + 1);
        return htmlStr;
    }

    /**
     * 获取指定网页的源码
     *
     * @param url
     * @return
     */
    public static String getContent(String url) {
        HttpClientBuilder custom = HttpClients.custom();//创建httpclient
        //通过构建器构建一个httpclient对象，可以认为是获取到一个浏览器对象
        CloseableHttpClient build = custom.build();
        //把url封装到get请求中
        HttpGet httpGet = new HttpGet(url);
        String content = null;
        try {
            //使用client执行get请求,获取请求的结果，请求的结果被封装到response中
            CloseableHttpResponse response = build.execute(httpGet);
            //表示获取返回的内容实体对象
            HttpEntity entity = response.getEntity();
            //解析实体中页面的内容，返回字符串形式
            content = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 根据xpath获取对应标签的内容
     *
     * @param tagNode
     * @param xpath
     * @return
     */
    public static String getText(TagNode tagNode, String xpath) {
        String content = null;
        Object[] evaluateXPath;
        try {
            evaluateXPath = tagNode.evaluateXPath(xpath);
            if (evaluateXPath != null && evaluateXPath.length > 0) {
                TagNode node = (TagNode) evaluateXPath[0];
                content = node.getText().toString();
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 获取对应标签中指定属性的值
     *
     * @param tagNode
     * @param xpath
     * @param attr
     * @return
     */
    public static String getAttributeByName(TagNode tagNode, String xpath, String attr) {
        String content = null;
        Object[] evaluateXPath;
        try {
            evaluateXPath = tagNode.evaluateXPath(xpath);
            if (evaluateXPath != null && evaluateXPath.length > 0) {
                TagNode node = (TagNode) evaluateXPath[0];
                content = node.getAttributeByName(attr);
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) throws IOException {
        Pattern compile = Pattern.compile("https://item.jd.com/([0-9]+).html");
        Matcher matcher = compile.matcher("https://item.jd.com/3915795.html");
        String goodid = null;
        if (matcher.find()) {
            goodid = matcher.group(1);
        }
        System.out.println(goodid);
    }
}
