package youke.common.utils;

import net.sf.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.springframework.web.multipart.MultipartFile;
import youke.common.exception.BusinessException;
import youke.common.exception.WxException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2017/12/28
 * Time: 11:33
 */
public class WxCurlUtil {

    /**
     * @param url 上传地址
     * @return
     */
    public static String postFile(String url, InputStream io, UUID uuid) {
        String result = "";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            String boundary = "-----------------------------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write(
                    String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", uuid.toString())
                            .getBytes());
            output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());
            byte[] data = new byte[1024];
            int len = 0;
            InputStream input = io;
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }
            output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();
            InputStream resp = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            while ((len = resp.read(data)) > -1)
                sb.append(new String(data, 0, len, "utf-8"));
            resp.close();
            result = sb.toString();
        } catch (ClientProtocolException e) {
            System.err.println("postFile，不支持http协议" + e);
        } catch (IOException e) {
            System.err.println("postFile数据传输失败" + e);
        }
        return result;
    }

    public static String postFile(String url, MultipartFile file) {
        String res = "";
        HttpURLConnection conn = null;
        String BOUNDARY = "---------------------------"+System.currentTimeMillis(); //boundary就是request头和上传文件内容的分隔符
        try {
            URL postUrl = new URL(url);
            conn = (HttpURLConnection) postUrl.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn
                    .setRequestProperty("User-Agent",
                            "Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data; boundary=" + BOUNDARY);
            OutputStream out = new DataOutputStream(conn.getOutputStream());

            String filename = file.getOriginalFilename();
            String contentType =file.getContentType();
            if (contentType == null || contentType.equals("")) {
                contentType = "application/octet-stream";
            }
            StringBuffer strBuf = new StringBuffer();
            strBuf.append("\r\n").append("--").append(BOUNDARY).append(
                    "\r\n");
            strBuf.append("Content-Disposition: form-data; name=\""
                    + file.getName() + "\"; filename=\"" + filename
                    + "\"\r\n");
            strBuf.append("Content-Type:" + contentType + "\r\n\r\n");
            out.write(strBuf.toString().getBytes());
            InputStream in = file.getInputStream();


            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                out.write(bufferOut, 0, bytes);
            }
            in.close();

            byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
            out.write(endData);
            out.flush();
            out.close();
            // 读取返回数据
            strBuf = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    conn.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                strBuf.append(line).append("\n");
            }
            res = strBuf.toString();
            reader.close();
            reader = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
                conn = null;
            }
        }
        return res;
    }

    /**
     * url - 请求路径
     * filePath 图片绝对路径@return
     */
    public static String postFile(String url, String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return null;
        String result = "";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            String boundary = "-----------------------------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write(
                    String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName())
                            .getBytes());
            output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());
            byte[] data = new byte[1024];
            int len = 0;
            FileInputStream input = new FileInputStream(file);
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }
            output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();
            InputStream resp = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            while ((len = resp.read(data)) > -1)
                sb.append(new String(data, 0, len, "utf-8"));
            resp.close();
            result = sb.toString();
        } catch (ClientProtocolException e) {
            System.err.println("postFile，不支持http协议" + e);
        } catch (IOException e) {
            System.err.println("postFile数据传输失败" + e);
        }
        return result;
    }

    /**
     * url - 请求路径
     * filePath 图片绝对路径@return
     */
    public static String postFile(String url, File file) {
        if (!file.exists())
            return null;
        String result = "";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            String boundary = "-----------------------------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write(
                    String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", file.getName())
                            .getBytes());
            output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());
            byte[] data = new byte[1024];
            int len = 0;
            FileInputStream input = new FileInputStream(file);
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }
            output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();
            InputStream resp = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            while ((len = resp.read(data)) > -1)
                sb.append(new String(data, 0, len, "utf-8"));
            resp.close();
            result = sb.toString();
        } catch (ClientProtocolException e) {
            System.err.println("postFile，不支持http协议" + e);
        } catch (IOException e) {
            System.err.println("postFile数据传输失败" + e);
        }
        return result;
    }

    /**
     * @param url     请求路径
     * @param fileurl 远程访问的路径
     * @return
     */
    public static String postFileFromUrl(String url, String fileurl) {
        InputStream input = HttpConnUtil.getStreamFromNetByUrl(fileurl);
        String fileName = StringUtil.getFileName(fileurl);
        String result = "";
        try {
            URL url1 = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            String boundary = "-----------------------------" + System.currentTimeMillis();
            conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream output = conn.getOutputStream();
            output.write(("--" + boundary + "\r\n").getBytes());
            output.write(
                    String.format("Content-Disposition: form-data; name=\"media\"; filename=\"%s\"\r\n", fileName)
                            .getBytes());
            output.write("Content-Type: image/jpeg \r\n\r\n".getBytes());
            byte[] data = new byte[1024];
            int len = 0;
            while ((len = input.read(data)) > -1) {
                output.write(data, 0, len);
            }
            output.write(("\r\n--" + boundary + "\r\n\r\n").getBytes());
            output.flush();
            output.close();
            input.close();
            InputStream resp = conn.getInputStream();
            StringBuffer sb = new StringBuffer();
            while ((len = resp.read(data)) > -1)
                sb.append(new String(data, 0, len, "utf-8"));
            resp.close();
            result = sb.toString();
        } catch (ClientProtocolException e) {
            System.err.println("postFile，不支持http协议" + e);
        } catch (IOException e) {
            System.err.println("postFile数据传输失败" + e);
        }
        return result;
    }

    public static void ret(String ret) {
        if (ret == null || !ret.startsWith("{")) {
            throw new BusinessException("其他异常");
        }
        JSONObject jsonObj = JSONObject.fromObject(ret);
        if (jsonObj.has("errcode")) {
            int errcode = jsonObj.getInt("errcode");
            String errmsg = jsonObj.getString("errmsg");
            if (errcode != 0) {
                String s = WxException.map.get(errcode);
                if (StringUtil.hasLength(s)) {
                    throw new BusinessException(s);
                } else {
                    throw new BusinessException(errmsg);
                }
            }
        }
    }

    /**
     * 下载临时素材
     *
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static InputStream downloadTempMaterial(String accessToken, String mediaId) {
        InputStream is = null;
        String url = "https://api.weixin.qq.com/cgi-bin/media/get?access_token="
                + accessToken + "&media_id=" + mediaId;
        try {
            URL urlGET = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGET
                    .openConnection();
            http.setRequestMethod("GET"); // 必须是GET方式请求
            http.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setUseCaches(false);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            // 获取文件转化为byte流
            is = http.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }

    /**
     * 下载永久素材
     *
     * @param accessToken
     * @param mediaId
     * @return
     */
    public static InputStream downloadPerpMaterial(String accessToken, String mediaId) {
        InputStream is = null;
        JSONObject obj = new JSONObject();
        String url = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token="
                + accessToken;
        try {
            obj.put("media_id", mediaId);
            URL urlPOST = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlPOST
                    .openConnection();
            http.setRequestMethod("POST"); // 必须是POST方式请求
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("Content-Length", String.valueOf(obj.toString().getBytes().length));
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setUseCaches(false);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
            System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
            http.connect();
            OutputStream out = http.getOutputStream();
            out.write((obj.toString()).getBytes());//发送json数据
            out.flush();
            out.close();
            // 获取文件转化为byte流
            is = http.getInputStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return is;
    }
}
