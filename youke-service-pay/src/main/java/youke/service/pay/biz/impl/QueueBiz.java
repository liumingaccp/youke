package youke.service.pay.biz.impl;

import org.springframework.stereotype.Service;
import youke.common.queue.message.PayFailMessage;
import youke.common.queue.message.SysOpenMessage;
import youke.common.queue.message.SysPayFailMessage;
import youke.common.queue.message.SysRechargeMessage;
import youke.service.pay.biz.IQueueBiz;
import youke.service.pay.queue.producter.QueueSender;

import javax.annotation.Resource;

@Service
public class QueueBiz implements IQueueBiz {

    @Resource
    private QueueSender queueSender;

    @Override
    public void sendSysRecharge(String openId, String title, String type, Integer price) {
        queueSender.send("sysRecharge.queue",new SysRechargeMessage(openId,title,type,price));
    }

    @Override
    public void sendSysOpenAccount(String openId, Integer vip, Integer price, String title, String endTime) {
        queueSender.send("sysOpenAccount.queue",new SysOpenMessage(openId,vip,price,title,endTime));
    }

    @Override
    public void sendPayFailMsg(String mobile, int money, String reason) {
        queueSender.send("payFailMsg.queue",new PayFailMessage(mobile,money,reason));
    }

    @Override
    public void sendSysPayFail(String openId, int money, String reason) {
        queueSender.send("sysPayFail.queue",new SysPayFailMessage(openId,money,reason));
    }
}
