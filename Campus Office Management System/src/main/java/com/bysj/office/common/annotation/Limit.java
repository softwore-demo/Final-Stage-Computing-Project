package com.bysj.office.common.annotation;


import com.bysj.office.common.entity.LimitType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Limit {

    /**
     *
     */
    String name() default "";

    /**
     *
     */
    String key() default "";

    /**
     *
     */
    String prefix() default "";

    /**
     *
     */
    int period();

    /**
     *
     */
    int count();

    /**
     *
     */
    LimitType limitType() default LimitType.CUSTOMER;
}
