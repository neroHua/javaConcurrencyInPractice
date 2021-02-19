package common;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@Documented
@Retention(SOURCE)
@Target({ TYPE, FIELD, METHOD, CONSTRUCTOR, LOCAL_VARIABLE })
public @interface Immutable {

}
