package youke.service.helper.provider;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)   //使用junit4进行测试
@ContextConfiguration(locations = "classpath:META-INF/spring/spring-*.xml")  //加载配置文件
@Transactional   //如果不加入这个注解配置，事务控制就会完全失效！
public class BaseJunit4Test extends AbstractJUnit4SpringContextTests {
}
