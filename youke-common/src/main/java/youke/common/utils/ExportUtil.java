package youke.common.utils;

import com.csvreader.CsvReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import youke.common.constants.ApiCodeConstant;
import youke.common.exception.BusinessException;
import youke.common.oss.FileUpOrDwUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExportUtil {
    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);
    /**
     * CSV文件列分隔符
     */
    private static final String CSV_COLUMN_SEPARATOR = ",";

    /**
     * CSV文件列分隔符
     */
    private static final String CSV_RN = "\r\n";

    /**
     * @param dataList 集合数据
     * @param colNames 表头部数据
     * @param mapKey   查找的对应数据
     * @param os       返回结果
     */
    public static boolean doExport(List<Map<String, Object>> dataList, String colNames, String mapKey, OutputStream os) {
        try {
            StringBuffer buf = new StringBuffer();

            String[] colNamesArr = null;
            String[] mapKeyArr = null;

            colNamesArr = colNames.split(",");
            mapKeyArr = mapKey.split(",");

            // 完成数据csv文件的封装
            // 输出列头
            for (int i = 0; i < colNamesArr.length; i++) {
                buf.append(colNamesArr[i]).append(CSV_COLUMN_SEPARATOR);
            }
            buf.append(CSV_RN);

            if (null != dataList) { // 输出数据
                for (int i = 0; i < dataList.size(); i++) {
                    for (int j = 0; j < mapKeyArr.length; j++) {
                        buf.append(dataList.get(i).get(mapKeyArr[j])).append(CSV_COLUMN_SEPARATOR);
                    }
                    buf.append(CSV_RN);
                }
            }
            // 写出响应
            os.write(buf.toString().getBytes("GBK"));
            os.flush();
            return true;
        } catch (Exception e) {
            logger.error("doExport错误...", e);
        }
        return false;
    }

    /**
     * @throws UnsupportedEncodingException setHeader
     */
    public static void responseSetProperties(String fileName, HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置文件后缀
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String fn = fileName + sdf.format(new Date()).toString() + ".csv";
        // 读取字符编码
        String utf = "UTF-8";

        // 设置响应
        response.setContentType("application/ms-txt.numberformat:@");
        response.setCharacterEncoding(utf);
        response.setHeader("Pragma", "public");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fn, utf));
    }

    /**
     * CSV文件解析
     *
     * @param fileName 文件名
     * @param fileUrl  文件地址
     * @return
     */
    public static List<String[]> doParseCsv(String fileName, String fileUrl) {
        String exName = org.springframework.util.StringUtils.getFilenameExtension(fileName);
        if (!Arrays.asList(ApiCodeConstant.IMPORT).contains(exName)) {
            throw new BusinessException("文件格式错误,请使用指定格式的模板进行文件上传");
        } else {
            List<String[]> csvFileList = new ArrayList<>();
            CsvReader csvReader = null;
            InputStream inputStream = FileUpOrDwUtil.downloadIo(fileUrl.replace(ApiCodeConstant.AUTH_DOMAIN + "/", ""));
            try {
                csvReader = new CsvReader(inputStream, Charset.forName("GBK"));
                csvReader.readHeaders();
                while (csvReader.readRecord()) {
                    csvFileList.add(csvReader.getValues());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (csvReader != null) {
                    csvReader.close();
                }
            }
            return csvFileList;
        }
    }
}