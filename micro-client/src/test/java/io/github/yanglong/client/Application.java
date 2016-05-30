package io.github.yanglong.client;

import io.github.yanglong.client.config.CallServiceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * package: springboot.simple <br/>
 * functional describe:程序启动入口，如果要将应用转为war部署，需要继承{@link SpringBootServletInitializer}<br/>
 * 并重写{@link SpringBootServletInitializer#configure(SpringApplicationBuilder)}方法<br>，同时修改pom类型以及目录结构修改。<br/>
 * EG：
 * <p>
 * protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
 * return builder.sources(Application.class);
 * }
 * </p>
 * <br/>
 * main方法中
 * SpringApplication.run(Application.class, args);
 * <br/>
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/3/13 13:49
 */
@SpringBootApplication
@ComponentScan("io.github.yanglong.client")
public class Application{
    /**
     * 程序启动入口
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        System.out.println("*********************正在启动*********************************");
        //SpringApplication.run(springboot.simple.ServerStart.class,args);
        ConfigurableApplicationContext context=SpringApplication.run(Application.class, args);
        CallServiceConfig ref=context.getBean(CallServiceConfig.class);
        System.out.println("*********************启动成功*********************************");
    }
}
