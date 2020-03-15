package com.wyw.concurrency.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *注解定义
 *
 * 线程安全,存在范围：编译时忽略掉（仅做标识） 类中
 *
 * @author wyw
 * @date 2020/03/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface ThreadSafe {

    String value() default "";
}
