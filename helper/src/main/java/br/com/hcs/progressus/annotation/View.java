package br.com.hcs.progressus.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.hcs.progressus.enumerator.Separator;

@Inherited
@Documented
@Target(value={ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface View {
	String menu() default "";
	String name() default "";
	String description() default "";
	Separator separator() default Separator.NONE;
	String[] permissions() default {};
}
