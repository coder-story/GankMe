package dawn.com.gankme.aop.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by mac on 2018/5/2.
 */


@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface SingleClick {
}
