package youke.core.mq.demo;

import org.junit.Test;
import youke.core.mq.producer.QueueSender;
import youke.core.mq.producer.TopicSender;

import javax.annotation.Resource;

public class DemoActionTest extends BaseJunit4Test {

    @Resource
    QueueSender queueSender;
    @Resource
    TopicSender topicSender;

    @Test
    public void queueSender() {
        String opt="";
        try {
            queueSender.send("test.queue", "holle queue");
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        logger.info("queue 发送完毕"+opt);
    }

    @Test
    public void topicSender() {
        String opt = "";
        try {
            topicSender.send("test.topic", "holle topic");
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        logger.info("topic 发送完毕"+opt);
    }
}