package youke.service.wx.queue.consumer;

import org.springframework.stereotype.Service;
import youke.common.dao.IShopOrderDao;
import youke.facade.wx.provider.IWeixinMessageService;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.List;
import java.util.Map;

@Service
public class OrderTempListener implements MessageListener {

    @Resource
    private IShopOrderDao shopOrderDao;
    @Resource
    private IWeixinMessageService weixinMessageService;

    public void onMessage(Message message) {
        try {
            //获取到接收的数据
            ObjectMessage objectMessage = (ObjectMessage) message;
            String[] datas =objectMessage.getObject().toString().split("#");
            String orderno = datas[0];
            int state = Integer.parseInt(datas[1]);
            String youkeId = datas[2];
            //查找订单是否有参与活动
            List<Map> maps = shopOrderDao.selectOrder(orderno,youkeId);
            if(maps!=null&&maps.size()>0){
                for (Map map:maps){
                    String appId = (String) map.get("appId");
                    String openId = (String) map.get("openId");
                    String title = (String)map.get("title");
                    if(state==1)
                        weixinMessageService.sendTempOrderPay(appId,openId,title,orderno,0);
                    else if(state==2)
                        weixinMessageService.sendTempOrderDeliver(appId,openId,title,orderno,0);
                    else if(state==3)
                        weixinMessageService.sendTempOrderReceive(appId,openId,title,orderno,0);
                }
            }
            //确认接收，并成功处理了消息
            message.acknowledge();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
