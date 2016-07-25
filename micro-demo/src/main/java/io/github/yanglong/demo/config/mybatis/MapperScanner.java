package io.github.yanglong.demo.config.mybatis;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.annotation.Annotation;

/**
 * package: springboot.simple.config <br/>
 * functional describe:配置Mybatis的mapper接口自动注入
 * 此配置必须在{@see MyBatisConfig}配置完成后才能配置
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/3/13 17:08
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "mybatis")
public class MapperScanner {
    //mapper接口所在的包名
    private String basePackage="io.github.yanglong.demo.dao";
    //只有设置了此注解的接口才会被mybatis管理
    private Class<? extends Annotation> annotationClass=MyBatisRepository.class;

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        //配置mapper接口所在的包
        scanner.setBasePackage(basePackage);
        //配置默认的sqlSessionFactory，与MyBatisConfig中配置的别名一致
        scanner.setSqlSessionFactoryBeanName("sqlSessionFactory");
        //配置只扫描某个注解的接口
        scanner.setAnnotationClass(annotationClass);
        return scanner;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }
}
