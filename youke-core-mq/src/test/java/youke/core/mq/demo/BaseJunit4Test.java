package youke.core.mq.demo;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)   //使用junit4进行测试
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-*.xml")  //加载配置文件
public class BaseJunit4Test extends AbstractJUnit4SpringContextTests {
}
