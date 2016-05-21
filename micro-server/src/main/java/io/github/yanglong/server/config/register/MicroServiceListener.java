package io.github.yanglong.server.config.register;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * package: micro.service.yanglong.provider.config.listener <br/>
 * functional describe:微服务注册监听器，在spring上下文初始化后执行,将URL转化为服务注册到注册中心<br/>
 * 需要注册到spring：
 * <p>
 * SpringApplication springApplication =new SpringApplication(Application.class);
 * springApplication.addListeners(new MicroServiceListener());
 * springApplication.run(args);
 * </p>
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/17 10:02
 */
public class MicroServiceListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        RegisterMicroService register = contextRefreshedEvent.getApplicationContext().getBean(RegisterMicroService.class);
        //do something
        // register.registerService();
    }
}
