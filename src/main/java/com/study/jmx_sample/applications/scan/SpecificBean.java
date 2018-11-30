package com.study.jmx_sample.applications.scan;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface SpecificBean {
    String myValue() default "myValue";
}
