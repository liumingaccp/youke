package youke.core.scheduler.main;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import youke.core.scheduler.service.*;

import java.util.Date;

public class SchedulerMain {
	
	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/spring-*.xml");
		context.start();
		System.out.println("启动成功");

	}

}
