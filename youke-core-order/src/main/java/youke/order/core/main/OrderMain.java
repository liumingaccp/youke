package youke.order.core.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import youke.order.common.util.DateUtil;
import youke.order.core.service.OrderService;

public class OrderMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/spring-*.xml");
        context.start();
        System.out.println(DateUtil.getDateTime()+" 服务启动成功!");
    }
}
