package youke.order.common.source;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default DataSource.dataSource1;

    String dataSource1 = "dataSource1";

    String dataSource2 = "dataSource2";

 
}
