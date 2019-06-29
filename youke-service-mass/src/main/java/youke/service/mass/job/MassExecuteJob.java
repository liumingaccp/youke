package youke.service.mass.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.stereotype.Component;
import youke.common.spring.SpringContextHolder;
import youke.facade.mass.vo.MassSMSMessage;
import youke.service.mass.biz.IMassSMSBiz;

@Component
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MassExecuteJob implements Job {
    //提供无参构造器
    public MassExecuteJob() {
    }

    public void execute(JobExecutionContext context) {
        try {
            MassSMSMessage message = (MassSMSMessage) context.getJobDetail().getJobDataMap().get("message");
            IMassSMSBiz massSendBiz = SpringContextHolder.getBean(IMassSMSBiz.class);
            if (message.getSendType() == 0) {
                massSendBiz.doMass(message);
            } else {
                massSendBiz.doMemberMass(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
