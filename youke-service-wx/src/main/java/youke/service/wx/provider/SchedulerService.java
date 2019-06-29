package youke.service.wx.provider;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import youke.common.dao.IMassTaskDao;
import youke.common.model.TMassTask;
import youke.common.redis.RedisUtil;
import youke.common.utils.DateUtil;
import youke.service.wx.jobs.MassJob;
import youke.service.wx.jobs.ScheduleJob;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * Created By LiHaifeng
 * Date: 2018/1/13
 * Time: 9:42
 */
@Service
public class SchedulerService {
    private static String JOB_GROUP_NAME = "mass";
    private static String TRIGGER_GROUP_NAME = "massTrigger";
    @Resource
    private Scheduler scheduler;

    /**
     * 开始一个simpleSchedule()调度
     */
    public void startSchedule(TMassTask task) {
        if (task == null) {
            return;
        }
        //任务执行时间
        Date taskDate = new Date();
        Date date = task.getTasktime();
        if (DateUtil.compareDate(date)) {
            taskDate = date;
            DateUtil.addSeconds(taskDate, 30); //加30秒执行
        }
        ScheduleJob job = new ScheduleJob();
        job.setJobName(task.getId() + "");
        job.setJobGroup(task.getAppid());
        //存入redis
        RedisUtil.set(task.getAppid() + task.getId(), job);

        try {
            // 1、创建一个JobDetail实例，指定Quartz
            JobDetail jobDetail = JobBuilder.newJob(MassJob.class)
                    // 任务执行类
                    .withIdentity(task.getId() + "", JOB_GROUP_NAME + task.getAppid())
                    // 任务名，任务组
                    .build();
            jobDetail.getJobDataMap().put("task", task);
            // 2、创建Trigger
            SimpleScheduleBuilder builder = SimpleScheduleBuilder
                    .simpleSchedule()
                    // 设置执行次数
                    .repeatSecondlyForTotalCount(1);
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(task.getId() + "", TRIGGER_GROUP_NAME + task.getAppid())
                    .startAt(taskDate)
                    .withSchedule(builder).build();
            // 3、创建Scheduler,开启调度器
            if (!scheduler.isStarted()) {
                scheduler.start();
            }
            // 4、调度执行
            scheduler.scheduleJob(jobDetail, trigger);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //scheduler.shutdown();

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个job
     *
     * @param scheduleJob
     * @throws SchedulerException
     */
    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

}
