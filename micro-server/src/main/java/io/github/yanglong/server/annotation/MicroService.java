package io.github.yanglong.server.annotation;

import java.lang.annotation.*;

/**
 * package: micro.service.yanglong.common.annotation <br/>
 * functional describe:微服务RPC注解，用于自动注入
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/16 16:48
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MicroService {
    String value() default "";
}
