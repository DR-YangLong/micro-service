package io.github.yanglong.demo.config.freemarker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.yanglong.demo.config.shiro.ShiroFreemarkTagsConfigurer;

/**
 * functional describe:spring boot freemarker 配置生成
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-7-25
 */
@Configuration
public class FreemarkerShiroTagConfig {

    @Autowired
    private freemarker.template.Configuration freemarkerConfiguration;

    /**
     * 启用shiro标签
     * @return
     */
    @Bean(name = "shiroFreemarkerConfig")
    public freemarker.template.Configuration shiroFreemarkTagsConfigurer(){
        ShiroFreemarkTagsConfigurer configurer=new ShiroFreemarkTagsConfigurer();
        configurer.setConfiguration(freemarkerConfiguration);
        return configurer.getConfiguration();

    }

}
