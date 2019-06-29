package youke.core.mq.demo;

import youke.core.mq.producer.QueueSender;
import youke.core.mq.producer.TopicSender;

import javax.annotation.Resource;

public class DemoAction {

    @Resource
    QueueSender queueSender;
    @Resource
    TopicSender topicSender;

    /**
     * 发送消息到队列
     * Queue队列：仅有一个订阅者会收到消息，消息一旦被处理就不会存在队列中
     * @return String
     */
    public String queueSender(){
        String opt="";
        try {
            queueSender.send("test.queue", "holle queue");
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

    /**
     * 发送消息到主题
     * Topic主题 ：放入一个消息，所有订阅者都会收到
     * 这个是主题目的地是一对多的
     * @return String
     */
    public String topicSender(){
        String opt = "";
        try {
            topicSender.send("test.topic", "holle topic");
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

}
