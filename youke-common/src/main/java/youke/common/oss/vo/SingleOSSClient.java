package youke.common.oss.vo;

import com.aliyun.oss.OSSClient;
import youke.common.constants.ApiCodeConstant;

/**
 * Created with IntelliJ IDEA
 * Created LHF
 * Date: 2017/12/13
 * Time: 12:08
 * 用于获取OSS连接
 */
public class SingleOSSClient {

    private static String endpoint = ApiCodeConstant.ENDPOINT; //"*** Provide OSS endpoint ***";
    private static String accessKeyId = ApiCodeConstant.SUB_ACCOUNT_ID; //"*** Provide your AccessKeyId ***";
    private static String accessKeySecret = ApiCodeConstant.SUB_ACCOUNT_SECRET; //"*** Provide your AccessKeySecret ***";

    private static volatile OSSClient oSSClient = null;

    private SingleOSSClient(){}

    public static OSSClient getOSSClient(){
        if(oSSClient == null){
            synchronized ((SingleOSSClient.class)){
                if(oSSClient == null){
                    oSSClient =new OSSClient(endpoint, accessKeyId, accessKeySecret);
                }
            }
        }
        return oSSClient;
    }
   /* public static void close(){
        if(oSSClient != null){
            oSSClient.shutdown();
        }
    }*/
}
