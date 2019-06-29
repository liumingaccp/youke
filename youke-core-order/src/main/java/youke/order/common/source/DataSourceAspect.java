package youke.order.common.source;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

public class DataSourceAspect implements MethodBeforeAdvice,AfterReturningAdvice
{
	private final static Log logger = LogFactory.getLog(DataSourceAspect.class.getName());
	@Override
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		// TODO Auto-generated method stub
		DataSourceContextHolder.clearDataSourceType();
	}

	@Override
	public void before(Method method, Object[] args, Object target)
			throws Throwable {

		if (method.isAnnotationPresent(DataSource.class))
		{
			DataSource datasource = method.getAnnotation(DataSource.class);
			logger.info("数据源切换:" + datasource.name());
			DataSourceContextHolder.setDataSourceType(datasource.name());
		}
		else
		{
			DataSourceContextHolder.setDataSourceType("dataSource1");
		}
		
	}
}
