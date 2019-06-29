package youke.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import youke.common.constants.ApiCodeConstant;
import youke.common.oss.vo.PartFileParam;
import youke.common.oss.vo.QueryObject;
import youke.common.oss.vo.SingleOSSClient;
import youke.common.utils.DateUtil;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA
 * Created By Kinsey
 * Date: 2017/12/13
 * Time: 18:38
 * 文件的上传和下载,以及 文件的一些管理
 */
public class FileUpOrDwUtil {

    private static OSSClient ossClient = SingleOSSClient.getOSSClient();
    private static String bucketName = ApiCodeConstant.BUCKET_NAME;
    private static String url = ApiCodeConstant.AUTH_DOMAIN + "/";

    private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());

    /**
     * 创建文件夹,可以一次性创建多级目录.其实也就是上传一个空的字符串
     * 比如创建 dir1/dir2/dir3/ 可以直接这样写,也可以传入 dir1,dir2,dir2即可一次创建三个目录
     * 必须要加 /
     */
    public static void createDir(String... dirNames) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < dirNames.length; i++) {
            sb.append(dirNames[i] + "/");
        }
        ossClient.putObject(bucketName, sb.toString(), new ByteArrayInputStream(new byte[0]));
    }

    /**
     * 上传网络流
     *
     * @param inputStream 网络流
     * @param key         保存的键
     * @return 返回访问地址
     */
    public static String uploadNetStream(InputStream inputStream, String key) {
        ossClient.putObject(bucketName, key, inputStream);
        return url + key;
    }

    public static String uploadNetStream(InputStream inputStream, String key, Date date) {
        if(date != null){
            ObjectMetadata meta = new ObjectMetadata();
            meta.setExpirationTime(date);
            ossClient.putObject(bucketName, key, inputStream, meta);
        }else{
            ossClient.putObject(bucketName, key, inputStream);
        }
        return url + key;
    }

    /**
     * 上传网络流(以文件的形式保存)
     *
     * @param inputStream 网络流
     * @param key         保存的键
     * @return 返回访问地址
     */
    public static String uploadNetStream(InputStream inputStream, String key, String filename) {
        File file = new File(filename);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int ch = 0;
            while ((ch = inputStream.read()) != -1) {
                os.write(ch);
            }
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        ossClient.putObject(bucketName, key, file);
        return url + key;
    }

    /**
     * 上传一个文件流
     *
     * @param localFileName 文件路径
     * @param key           保存的key
     * @return 返回访问地址
     */
    public static String uploadFileStream(String localFileName, String key) {
        return uploadFileOrStream(localFileName, key, true);
    }

    /**
     * 上传一个文件流
     *
     * @param localFile 文件对象
     * @param key       保存的key
     * @return 返回访问地址
     */
    public static String uploadFileStream(File localFile, String key) {
        return uploadFileOrStream(localFile, key, true);
    }

    /**
     * 上传一个文件
     *
     * @param localFile 文件对象
     * @param key       保存的key
     * @return 返回访问地址
     */
    public static String uploadLocalFile(File localFile, String key) {
        return uploadFileOrStream(localFile, key, false);
    }

    public static String uploadLocalFile(String localFileName, String key, Date date) {
        if(date != null){
            ObjectMetadata meta = new ObjectMetadata();
            meta.setExpirationTime(date);
            ossClient.putObject(bucketName, key, new File(localFileName));
        }else {
            ossClient.putObject(bucketName, key, new File(localFileName));
        }
        return url + key;
    }

    /**
     * 上传一个文件
     *
     * @param localFileName 文件的路径
     * @param key           保存的key
     */
    public static String uploadLocalFile(String localFileName, String key) {
        return uploadFileOrStream(localFileName, key, false);
    }

    /**
     * 上传一个文件,可以选择保存的方式
     *
     * @param localFileName 文件的路径
     * @param key           保存的key
     * @param isStream      true : 以流的方式保存
     *                      false : 以文件的形式保存
     */
    public static String uploadFileOrStream(String localFileName, String key, boolean isStream) {
        if (isStream) {
            try {
                InputStream inputStream = new FileInputStream(localFileName);
                ossClient.putObject(bucketName, key, inputStream);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ossClient.putObject(bucketName, key, new File(localFileName));
        }
        return url + key;
    }

    /**
     * 上传一个文件,可以选择保存方式
     *
     * @param localFile 文件对象
     * @param key       保存的key
     * @param isStream  true : 以流的方式保存
     *                  false : 以文件的形式保存
     */
    public static String uploadFileOrStream(File localFile, String key, boolean isStream) {
        InputStream inputStream = null;
        if (isStream) {
            try {
                inputStream = new FileInputStream(localFile);
                ossClient.putObject(bucketName, key, inputStream);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ossClient.putObject(bucketName, key, localFile);
        }


        return url + key;
    }

    /**
     * 批量上传流,为所有key指定前缀后缀
     *
     * @param filepathNames key : 保存的key(除开后缀)
     *                      value 传入的文件路径
     * @param pre           批量指定的前缀(所有文件保存后的key都会加上这个前缀)
     * @param suff          批量指定的后缀(所有文件保存后的key都会加上这个后缀)
     * @return 返回Map
     * key : 原先传入的key值
     * value : oss访问路径
     */
    public static Map<String, String> uploadFileBatch(Map<String, InputStream> filepathNames, String pre, String
            suff, Integer type) {
        Map<String, String> paths = new HashMap<String, String>();
        if (filepathNames.size() > 0) {
            String temp = null;
            for (Map.Entry<String, InputStream> path : filepathNames.entrySet()) {
                temp = uploadNetStream(path.getValue(), path.getKey());
                paths.put(path.getKey(), pre + temp + suff);
            }
        }
        return paths;
    }

    /**
     * 批量上传流,每个文件单独指定key值
     *
     * @param filepathNames Map 的key 对应要存储的key
     *                      value 保存文件的路径
     * @return 返回Map
     * key : 原先传入的key值
     * value : oss访问路径
     */
    public static Map<String, String> uploadFileBatch(Map<String, InputStream> filepathNames, Integer type) {
        return uploadFileBatch(filepathNames, "", "", type);
    }

    /**
     * 批量上传文件,为所有key指定前缀后缀
     *
     * @param filepathNames 传入的文件路径
     * @param pre           批量指定的前缀(所有文件保存后的key都会加上这个前缀)
     * @param suff          批量指定的后缀(所有文件保存后的key都会加上这个后缀)
     * @return 返回Map
     * key : 原先传入的key值
     * value : oss访问路径
     */
    public static Map<String, String> uploadFileBatch(Map<String, String> filepathNames, String pre, String suff,
                                                      boolean file) {
        Map<String, String> paths = new HashMap<String, String>();
        if (filepathNames.size() > 0) {
            String temp = null;
            for (Map.Entry<String, String> path : filepathNames.entrySet()) {
                temp = uploadFileOrStream(path.getValue(), path.getKey(), false);
                paths.put(path.getKey(), pre + temp + suff);
            }
        }
        return paths;
    }

    /**
     * 批量上传文件,每个文件单独指定key值
     *
     * @param filepathNames Map 的key 对应要存储的key
     *                      value 保存文件的路径
     * @return 返回Map
     * key : 原先传入的key值
     * value : oss访问路径
     */
    public static Map<String, String> uploadFileBatch(Map<String, String> filepathNames, boolean file) {
        return uploadFileBatch(filepathNames, "", "", true);
    }

    /**
     * 批量上传文件,为所有key指定前缀后缀
     *
     * @param fileNames 传入的文件
     * @param pre       批量指定的前缀(所有文件保存后的key都会加上这个前缀)
     * @param suff      批量指定的后缀(所有文件保存后的key都会加上这个后缀)
     * @return 返回Map
     * key : 原先传入的key值
     * value : oss访问路径
     */
    public static Map<String, String> uploadFileBatch(Map<String, File> fileNames, String pre, String suff) {
        Map<String, String> paths = new HashMap<String, String>();
        if (fileNames.size() > 0) {
            String temp = null;
            for (Map.Entry<String, File> path : fileNames.entrySet()) {
                temp = uploadFileOrStream(path.getValue(), path.getKey(), false);
                paths.put(path.getKey(), pre + temp + suff);
            }
        }
        return paths;
    }

    /**
     * 批量上传文件,每个文件单独指定key值
     *
     * @param fileNames Map 的key 对应要存储的key
     *                  value 保存的文件
     * @return 返回Map
     * key : 原先传入的key值
     * value : oss访问路径
     */
    public static Map<String, String> uploadFileBatch(Map<String, File> fileNames) {
        return uploadFileBatch(fileNames, "", "");
    }

    /**
     * +++++++++++++++++++++++++++++++++++++++++++分片上传++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    /**
     * 分片上传,要先调用claimUploadId方法,获取分片上传任务的uploadId
     *
     * @param key
     * @throws Exception
     */
    public static void uploadMultipart(String key, String uploadId, String sourceFileName, PartFileParam params) throws
            Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(params.getTaskSum());
        File file = new File(sourceFileName);
        long fileLength = file.length();
        params.setPartCount(fileLength);
        for (int i = 0; i < params.getPartCount(); i++) {
            long startPos = i * params.getPartSize();
            long curPartSize = (i + 1 == params.getPartCount()) ? (fileLength - startPos) : params.getPartSize();
            executorService.execute(new PartUploader(file, startPos, curPartSize, i + 1, uploadId, key));
        }

        /*
         * Waiting for all parts finished
         */
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            try {
                executorService.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /*
         * Verify whether all parts are finished
         */
        if (partETags.size() != params.getPartCount()) {
            throw new IllegalStateException("Upload multiparts fail due to some parts are not finished yet");
        } else {
            System.out.println("Succeed to complete multiparts into an object named " + key + "\n");
        }

        /*
         * View all parts uploaded recently
         */
        listAllParts(uploadId, key);

        /*
         * Complete to upload multiparts
         */
        completeMultipartUpload(uploadId, key);
        //如果完成了清空
        partETags.clear();
    }

    private static class PartUploader implements Runnable {

        private File localFile;
        private long startPos;

        private long partSize;
        private int partNumber;
        private String uploadId;

        private String key;

        public PartUploader(File localFile, long startPos, long partSize, int partNumber, String uploadId, String key) {
            this.localFile = localFile;
            this.startPos = startPos;
            this.partSize = partSize;
            this.partNumber = partNumber;
            this.uploadId = uploadId;
            this.key = key;
        }

        public void run() {
            InputStream instream = null;
            try {
                instream = new FileInputStream(this.localFile);
                instream.skip(this.startPos);

                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(key);
                uploadPartRequest.setUploadId(this.uploadId);
                uploadPartRequest.setInputStream(instream);
                uploadPartRequest.setPartSize(this.partSize);
                uploadPartRequest.setPartNumber(this.partNumber);

                UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                System.out.println("Part#" + this.partNumber + " done\n");
                synchronized (partETags) {
                    partETags.add(uploadPartResult.getPartETag());
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (instream != null) {
                    try {
                        instream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 创建分片上传事件,返回对应的 uploadId
     *
     * @param key
     * @return
     */
    public static String claimUploadId(String key) {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
        return result.getUploadId();
    }

    /**
     * 完成分片的操作
     *
     * @param uploadId
     */
    public static void completeMultipartUpload(String uploadId, String key) {
        // Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartETag>() {

            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });

        System.out.println("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    /**
     * 取消对应的 分片上传ID,会删除原先所有的part
     *
     * @param uploadId
     * @param key
     */
    public static void cancelMultipartUpload(String uploadId, String key) {
        // 取消分片上传，其中uploadId来自于initiateMultipartUpload
        AbortMultipartUploadRequest abortMultipartUploadRequest =
                new AbortMultipartUploadRequest(bucketName, key, uploadId);
        ossClient.abortMultipartUpload(abortMultipartUploadRequest);
    }

    /**
     * 获取对应 uploadId下的所有分片
     *
     * @param uploadId
     */
    public static void listAllParts(String uploadId, String key) {
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = ossClient.listParts(listPartsRequest);

        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            System.out.println("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
        System.out.println();
    }

    /**
     * ++++++++++++++++++++++++++++++++++++++++++++++++分片上传结束+++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    /**
     * 文件名添加前缀后缀
     *
     * @param pre
     * @param name
     * @param suf
     * @return
     */
    public static String setPreOrSuf(String pre, String name, String suf) {
        return pre + name + suf;
    }

    /**
     * 上传一个字符串
     *
     * @param yourString 源字符串
     * @param name       存储的名字
     */
    public static String uploadString(String yourString, String name) {
        ossClient.putObject(bucketName, name, new ByteArrayInputStream(yourString.getBytes()));
        return url + name;
    }

    /**
     * 获取字符串
     *
     * @param name 保存的key
     * @return 返回字符串
     */
    public static String downString(String name) {
        OSSObject ossObject = ossClient.getObject(bucketName, name);
        byte[] buf = new byte[1024];
        InputStream in = ossObject.getObjectContent();
        int count;
        StringBuffer sb = new StringBuffer();
        try {
            while ((count = in.read(buf)) != -1) {
                sb.append(new String(buf));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e2) {

                }
            }
        }
        return sb.toString();
    }

    /**
     * 上传一个byte数组
     *
     * @param key   保存的key
     * @param bytes 保存的比byte
     */
    public static String uploadBytes(String key, byte[] bytes) {
        ossClient.putObject(bucketName, key, new ByteArrayInputStream(bytes));
        return url + key;
    }

    /**
     * 获取byte数组
     *
     * @param name 保存的key
     * @return byte数组
     */
    public static byte[] downBytes(String name) {
        OSSObject ossObject = ossClient.getObject(bucketName, name);
        byte[] buf = new byte[1024];
        InputStream in = ossObject.getObjectContent();
        int count;
        StringBuffer sb = new StringBuffer();
        try {
            in.read(buf);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e2) {

                }
            }
        }
        return buf;
    }

    /**
     *
     * ==============================================下载开始==========================================================
     */
    /**
     * 下载object到文件
     */
    public static void downloadFile(String key, String filePath) {

        ossClient.getObject(new GetObjectRequest(bucketName, key), new File(filePath));
    }

    /**
     * 下载object到文件
     */
    public static void downloadFile(String key, File file) {

        ossClient.getObject(new GetObjectRequest(bucketName, key), file);
    }

    /**
     * 返回一个输出流
     *
     * @param key
     * @return
     */
    public static InputStream downloadIo(String key) {
        OSSObject ossObject = ossClient.getObject(bucketName, key);
        return ossObject.getObjectContent();
    }

    /**
     * 多线程下载(大文件)
     *
     * @param key
     * @throws Throwable
     */
    public static void downFilePart(String key, String filepath, PartFileParam params) throws Throwable {
        // 下载请求，10个任务并发下载，启动断点续传
        DownloadFileRequest downloadFileRequest = new DownloadFileRequest(bucketName, key);
        downloadFileRequest.setDownloadFile(filepath);
        downloadFileRequest.setPartSize(params.getPartSize());
        downloadFileRequest.setTaskNum(params.getTaskSum());
        downloadFileRequest.setEnableCheckpoint(true);
        // 下载文件
        DownloadFileResult downloadRes = ossClient.downloadFile(downloadFileRequest);
        // 下载成功时，会返回文件的元信息
        downloadRes.getObjectMetadata();
    }

    /**
     * 限定下载时间,如果当前时间晚于修改时间,允许修改
     *
     * @param key
     * @param filepath
     * @param isDownPart 是否启用大文件下载
     */
    public static void downFileRequest(String key, String filepath, boolean isDownPart) throws Throwable {
        GetObjectRequest request = new GetObjectRequest(bucketName, key);
        request.setUnmodifiedSinceConstraint(new Date());
        // 下载object到文件
        if (isDownPart) {
            downFilePart(key, filepath, new PartFileParam());
        } else {
            ossClient.getObject(request, new File(filepath));
        }
    }

/**
 * ==================================================删除==============================================================
 */
    /**
     * 删除指定的单个文件
     *
     * @param fileName
     */
    public static void deleteFile(String fileName) {
        ossClient.deleteObject(bucketName, fileName);
    }

    /**
     * 批量删除,返回删除结果
     *
     * @param fileNames
     * @return 返回删除成功的结果集
     */
    public static List<String> deleteAndRetSuccess(List<String> fileNames) {
        DeleteObjectsRequest del_obj_req = new DeleteObjectsRequest(bucketName);
        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(del_obj_req.withKeys(fileNames));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        return deletedObjects;
    }

    /**
     * 批量删除,并且返回删除的结果
     *
     * @param fileNames
     * @param retType
     * @return true : 返回删除成功的结果集
     * false : 返回失败的结果集
     */
    public static List<String> deleteAndRetSuccess(List<String> fileNames, boolean retType) {
        DeleteObjectsRequest del_obj_req = new DeleteObjectsRequest(bucketName);

        if (!retType) {
            del_obj_req.setQuiet(false);
        }

        DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(del_obj_req.withKeys(fileNames));
        List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
        return deletedObjects;
    }

    /**
     * ===================================================拷贝==========================================================
     */

    /**
     * 简单拷贝,少于 1G (不涉及文件头信息)
     *
     * @param sourceBucketName 源bucket
     * @param srcFile          源file
     * @param tagBucketName    目标bucket
     * @param tagFile          目标file
     * @return 返回对象CopyObjectResult信息
     * String getETag() :
     * 文件的唯一标识
     * Date getLastModified()
     * 源文件洗后修改日期
     */
    public static CopyObjectResult copyFileSimple(String sourceBucketName, String srcFile, String
            tagBucketName, String tagFile) {
        // 拷贝Object
        CopyObjectResult result = ossClient.copyObject(sourceBucketName, srcFile, tagBucketName, tagFile);
        return result;
    }

    /**
     * 文件拷贝 少于 1G ()
     *
     * @param srcBucketName
     * @param srcKey
     * @param destBucketName
     * @param destKey
     * @return 返回对象CopyObjectResult信息
     * String getETag() :
     * 文件的唯一标识
     * Date getLastModified()
     * 源文件洗后修改日期
     */
    public static CopyObjectResult copyObject(String srcBucketName, String srcKey, String destBucketName, String
            destKey) {
        // 创建CopyObjectRequest对象
        CopyObjectRequest copyObjectRequest = new CopyObjectRequest(srcBucketName, srcKey, destBucketName, destKey);
        // 复制Object
        CopyObjectResult result = ossClient.copyObject(copyObjectRequest);

        return result;
    }

    /**
     * 文件拷贝 少于 1G (更多的元信息设置)
     *
     * @param copyObjectRequest 保存了基本信息:源bucket,源路径,目标bucket,目标路径
     *                          源信息:查看api: https://help.aliyun.com/document_detail/32015.html?spm=5176.doc47505.6.667.uPSYBq
     * @return
     */
    public static CopyObjectResult copyObject(CopyObjectRequest copyObjectRequest) {
        // 复制Object
        CopyObjectResult result = ossClient.copyObject(copyObjectRequest);

        return result;
    }

    /**
     * 分片拷贝大文件
     *
     * @param srcBucketName
     * @param sourceFile
     * @param tagBucketName
     * @param tagFile
     */
    public static void copyPart(String srcBucketName, String sourceFile, String tagBucketName, String
            tagFile, PartFileParam params) {
        /**
         * 参数设置
         */
        // 得到被拷贝object大小
        ObjectMetadata objectMetadata = ossClient.getObjectMetadata(srcBucketName, sourceFile);
        long contentLength = objectMetadata.getContentLength();
        params.setPartCount(contentLength);
        // 分片大小，10MB
        long partSize = params.getPartSize();
        // 计算分块数目
        int partCount = params.getPartCount();
        System.out.println("total part count:" + partCount);
        /**
         * 初始化任务
         */
        // 初始化拷贝任务
        InitiateMultipartUploadRequest initiateMultipartUploadRequest = new InitiateMultipartUploadRequest(tagBucketName, tagFile);
        InitiateMultipartUploadResult initiateMultipartUploadResult = ossClient.initiateMultipartUpload(initiateMultipartUploadRequest);
        String uploadId = initiateMultipartUploadResult.getUploadId();
        // 分片拷贝
        List<PartETag> partETags = new ArrayList<PartETag>();
        for (int i = 0; i < partCount; i++) {
            // 计算每个分块的大小
            long skipBytes = partSize * i;
            long size = partSize < contentLength - skipBytes ? partSize : contentLength - skipBytes;
            // 创建UploadPartCopyRequest
            UploadPartCopyRequest uploadPartCopyRequest =
                    new UploadPartCopyRequest(srcBucketName, sourceFile, tagBucketName, tagFile);
            uploadPartCopyRequest.setUploadId(uploadId);
            uploadPartCopyRequest.setPartSize(size);
            uploadPartCopyRequest.setBeginIndex(skipBytes);
            uploadPartCopyRequest.setPartNumber(i + 1);
            UploadPartCopyResult uploadPartCopyResult = ossClient.uploadPartCopy(uploadPartCopyRequest);
            // 将返回的PartETag保存到List中
            partETags.add(uploadPartCopyResult.getPartETag());
        }
        /**
         * 执行任务
         */
        // 提交分片拷贝任务
        CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(
                tagBucketName, tagFile, uploadId, partETags);
        ossClient.completeMultipartUpload(completeMultipartUploadRequest);
    }

    /**
     * ============================================修改文件名===========================================================
     */
    /**
     * 修改文件名
     * 复制,再删除原先的文件,
     * 默认是小文件(小于 1G )
     *
     * @param oldKey
     * @param newKey
     */
    public static void updateObjName(String oldKey, String newKey) {
        updateObjName(oldKey, newKey, null);
    }

    /**
     * 修改文件名
     * 复制,再删除原先的文件,
     * 是否采取大文件拷贝的方式
     *
     * @param oldKey
     * @param newKey
     */
    public static void updateObjName(String oldKey, String newKey, PartFileParam param) {
        if (param == null) {
            copyObject(bucketName, oldKey, bucketName, newKey);
        } else {
            copyPart(bucketName, oldKey, bucketName, newKey, param);
        }
        deleteFile(oldKey);
    }

    /**
     * ============================================查询=================================================================
     */
    /**
     * key是否存在
     *
     * @param fileName 传入的key
     * @return true : 文件存在
     * false : 不存在
     */
    public static boolean isExist(String fileName) {
        return ossClient.doesObjectExist(bucketName, fileName);
    }

    /**
     * ++++++++++++++++++++++++++++++++++++++++++++条件查询+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */
    /**
     * 根据自定义的查询条件,查询列表
     *
     * @param qo   查询对象
     * @param keys 存放查询结果的list,最好是服务自己传入一个
     * @return 方法当前的查询对象, 用于下一页查询
     */
    public static QueryObject queryList(QueryObject qo, List<String> keys) {
        ListObjectsRequest objectsRequest = new ListObjectsRequest(bucketName).withMarker(qo.getNextMarker()).withMaxKeys(qo.getMaxKeys());
        objectsRequest.setPrefix(qo.getPrefix());
        //必须配合前缀使用
        if (qo.getPrefix().contains(qo.getDelimiter())) {
            objectsRequest.setDelimiter(qo.getDelimiter());
        }
        ObjectListing objectListing = ossClient.listObjects(objectsRequest);
        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
        for (OSSObjectSummary s : sums) {
            keys.add(s.getKey());
        }
        //没有查询完,就传入下一次的查询起始地址
        if (!objectListing.isTruncated()) {
            qo.setNextMarker(objectListing.getNextMarker());
        }
        return qo;
    }


    /**
     * ================================================其他功能=========================================================
     */
    /**
     * Java文件操作 获取文件扩展名
     * <p>
     * Created on: 2011-8-2
     *
     * @param filename
     * @return
     */

    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }

    /**
     * Java文件操作 获取不带扩展名的文件名
     * <p>
     * Created on: 2011-8-2
     * Author: blueeagle
     */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }
    public static void main(String[] args){
        Date date = DateUtil.parseDate("2018-03-13 17:22:00");
        FileUpOrDwUtil.uploadLocalFile("C:\\Users\\lenovo\\Desktop\\image\\taoke2.jpg", "haibao/"+"test/" + UUID.randomUUID().toString(), date);
        System.err.println(123);
    }
}
