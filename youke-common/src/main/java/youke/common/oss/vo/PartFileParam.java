package youke.common.oss.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.File;

/**
 * Created with IntelliJ IDEA
 * Created By LHF
 * Date: 2017/12/14
 * Time: 17:44
 * 封装分片上传和下载用到的参数
 */
public class PartFileParam {
    private static final long DEFUALT_PART_SIZE = 5 * 1024 * 1024L;   // 默认的分片size:5MB
    private static final int DEFUALT_TASK_SIZE = 2;   // 默认的线程数:5个
    private long partSize = DEFUALT_PART_SIZE;
    private int partCount;
    private int taskSum = DEFUALT_TASK_SIZE;

    public long getPartSize() {
        return partSize;
    }

    public void setPartSize(long partSize) {
        this.partSize = partSize;
    }

    public int getPartCount() {
        return partCount;
    }

    public void setPartCount(int partCount) {
        this.partCount = partCount;
    }

    public int getTaskSum() {
        return taskSum;
    }

    public void setTaskSum(int taskSum) {
        this.taskSum = taskSum;
    }

    public PartFileParam(){

    }
    public PartFileParam(long partSize, int taskSum){
        this.partSize = partSize;
        this.taskSum = taskSum;
    }

    public void setPartCount(long fileLength){
        partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        }
    }

    public int getPartCount(long fileLength){
        /*partCount = (int) (fileLength / partSize);
        if (fileLength % partSize != 0) {
            partCount++;
        }
        if (partCount > 10000) {
            throw new RuntimeException("Total parts count should not exceed 10000");
        }else{
            return partCount;
        }*/
        return this.partCount;
    }



}
