package com.jep.github.mvc.annotation;

/*
 * @author: enping.jep
 * @create: 2021-10-09 3:20 下午
 */

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ManualAutowired {

  String value() default "";
}
