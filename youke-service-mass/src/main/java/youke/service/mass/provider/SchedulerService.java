package youke.service.mass.provider;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import youke.common.base.Base;
import youke.common.utils.DateUtil;
import youke.facade.mass.vo.MassSMSMessage;
import youke.service.mass.job.MassExecuteJob;

import java.util.Date;

@Service
public class SchedulerService extends Base {

    @Autowired
    private Scheduler scheduler;

    public void startSchedule(MassSMSMessage message) {
        //任务执行时间
        Date taskDate = new Date();
        Date date = DateUtil.parseDate(message.getTaskTime());
        if (DateUtil.compareDate(date)) {
            taskDate = date;
        }
        taskDate = DateUtil.addSeconds(taskDate, 30);
        try {
            TriggerKey triggerKey = new TriggerKey(message.getDykId() + "-" + message.getTaskId(), message.getDykId() + "mass-trigger-group");
            JobKey jobKey = new JobKey(message.getDykId() + "-" + message.getTaskId(), message.getDykId() + "mass-job-group");
            //获取trigger
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                // 1、创建一个JobDetail实例，指定Quartz
                JobDetail jobDetail = JobBuilder.newJob(MassExecuteJob.class)
                        // 任务名，任务组
                        .withIdentity(jobKey)
                        .build();
                jobDetail.getJobDataMap().put("message", message);
                // 2、创建Trigger
                SimpleScheduleBuilder builder = SimpleScheduleBuilder
                        .repeatSecondlyForTotalCount(1);
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(message.getDykId() + "-" + message.getTaskId(), message.getDykId() + "mass-trigger-group")
                        .startAt(taskDate)
                        .withSchedule(builder).build();
                // 3、开启调度器
                scheduler.scheduleJob(jobDetail, trigger);
            } else {
                // 2、创建Trigger
                SimpleScheduleBuilder builder = SimpleScheduleBuilder
                        .repeatSecondlyForTotalCount(1);
                trigger = TriggerBuilder.newTrigger()
                        .withIdentity(message.getDykId() + "-" + message.getTaskId(), message.getDykId() + "mass-trigger-group")
                        .startAt(taskDate)
                        .withSchedule(builder).build();
                //3、重启任务
                scheduler.rescheduleJob(triggerKey, trigger);
            }
            // 4、调度执行
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个job任务
     */
    public void deleteJob(JobKey jobKey, TriggerKey triggerKey) {
        try {
            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(jobKey);//删除任务
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
