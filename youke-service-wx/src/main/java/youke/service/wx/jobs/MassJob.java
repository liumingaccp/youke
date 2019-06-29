package youke.service.wx.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;
import youke.common.model.TMassTask;
import youke.common.redis.RedisSlaveUtil;
import youke.common.redis.RedisUtil;
import youke.common.spring.SpringContextHolder;
import youke.facade.wx.provider.IWeixinMassService;
import youke.facade.wx.queue.message.SuperMassMessage;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/13
 * Time: 9:46
 */
@Service
public class MassJob implements Job {

    public void execute(JobExecutionContext context) throws JobExecutionException {
        TMassTask task = (TMassTask) context.getJobDetail().getJobDataMap().get("task");
        IWeixinMassService massService = SpringContextHolder.getBean(IWeixinMassService.class);
        if(RedisUtil.hasKey(task.getAppid() + task.getId())){
            ScheduleJob job = (ScheduleJob) RedisSlaveUtil.get(task.getAppid() + task.getId());
            //删除redis Job
            RedisUtil.del(task.getAppid() + task.getId());
            massService.mass(task);
        }
    }
}
