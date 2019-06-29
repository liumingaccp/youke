package youke.service.pay.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.constants.ComeType;
import youke.common.dao.ISubscrFansDao;
import youke.common.queue.message.ActiveMassMessage;
import youke.common.queue.message.TempMassMessage;
import youke.facade.pay.util.WXConstants;
import youke.service.pay.biz.IFansIntegralBiz;
import youke.service.pay.biz.IFansMoneyBiz;
import youke.service.pay.biz.IYoukePayBiz;
import youke.service.pay.queue.producter.QueueSender;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

@Service
public class ActivePayListener implements MessageListener {

    @Resource
    private QueueSender queueSender;
    @Resource
    private IFansIntegralBiz fansIntegralBiz;
    @Resource
    private IFansMoneyBiz fansMoneyBiz;
    @Resource
    private IYoukePayBiz youkePayBiz;
    @Resource
    private ISubscrFansDao subscrFansDao;

    @Override
    public void onMessage(Message message) {
        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            ActiveMassMessage massMessage = (ActiveMassMessage) objectMessage.getObject();
            if(massMessage.getState()==1) {
                if (massMessage.getMoney() != null && massMessage.getMoney()>0) {
                    if (youkePayBiz.doPayMoney(massMessage.getComeType(), massMessage.getMoney(), massMessage.getIntegral() == null ? 0 : massMessage.getIntegral(), massMessage.getOpenId(), massMessage.getAppId(), massMessage.getYoukeId())) {
                        youkePayBiz.updateActiveState(massMessage.getRecordId(), massMessage.getComeType(), 0);
                        try {
                            fansMoneyBiz.addMoney(massMessage.getComeType(), massMessage.getTitle(), massMessage.getOpenId().split(",")[0], massMessage.getMoney(), massMessage.getAppId(), massMessage.getYoukeId());
                            if (massMessage.getComeType() == ComeType.WEI_TAO_KE&&massMessage.getIntegral() > 0){
                               fansMoneyBiz.addMoney(massMessage.getComeType(), massMessage.getTitle(), massMessage.getOpenId().split(",")[1], massMessage.getIntegral(), massMessage.getAppId(), massMessage.getYoukeId());
                            }
                        }catch (Exception e){}
                    } else {
                        youkePayBiz.updateActiveState(massMessage.getRecordId(), massMessage.getComeType(), 1);
                    }
                } else if (massMessage.getIntegral() != null && massMessage.getIntegral() !=0) {
                    fansIntegralBiz.addIntegral(massMessage.getComeType(), massMessage.getTitle(), massMessage.getOpenId(), massMessage.getIntegral(), massMessage.getAppId(), massMessage.getYoukeId());
                    subscrFansDao.updateIntegralByOpenId(massMessage.getOpenId(), massMessage.getAppId(), massMessage.getIntegral());
                    if(massMessage.getIntegral()>0)
                       youkePayBiz.updateActiveState(massMessage.getRecordId(), massMessage.getComeType(), 0);
                    TempMassMessage temp = new TempMassMessage();
                    temp.setAppId(massMessage.getAppId());
                    temp.setOpenId(massMessage.getOpenId().split(",")[0]);
                    temp.setComeType(massMessage.getComeType());
                    temp.setTitle(massMessage.getTitle());
                    temp.setIntegral(massMessage.getIntegral());
                    queueSender.send("integralTemplate.queue", temp);
                } else {
                    youkePayBiz.updateActiveState(massMessage.getRecordId(), massMessage.getComeType(), 0);
                }
            }
            message.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
