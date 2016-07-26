package io.github.yanglong.demo.config.freemarker;

import freemarker.template.TemplateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

import io.github.yanglong.demo.config.shiro.tags.ShiroTags;

/**
 * functional describe:freemarker config 配置
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-7-25
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "freemarker")
public class FreemarkerConfig {
    private static final transient Logger logger= LoggerFactory.getLogger(FreemarkerConfig.class);
    private HashMap<String,String> properties;
    private String templateLoaderPath="classpath:templates";
    /**
     * 配置freemarker基本设置，此处可以配置freemarkerVariables但不推荐是使用，
     * 每次配置需要重新启动应用，并且将业务与freemarker绑定在了一起
     * @return Freemarker Configuration
     */
    @Bean(name = "freemarkerConfiguration")
    public freemarker.template.Configuration freeMarkerConfigurationFactory(){
        FreeMarkerConfigurationFactory factoryBean=new FreeMarkerConfigurationFactory();
        factoryBean.setTemplateLoaderPath(templateLoaderPath);
        Properties property = new Properties();
        //如果没有设置自定义配置，使用如下设置，如果设置了，则使用自定义配置
        if(CollectionUtils.isEmpty(properties)) {
            property.put("tag_syntax", "auto_detect");
            //缓存时间，0为不缓存
            property.put("template_update_delay", 0);
            property.put("defaultEncoding", "UTF-8");
            property.put("url_escaping_charset", "UTF-8");
            property.put("locale", "zh_CN");
            property.put("date_format", "yyyy-MM-dd");
            property.put("datetime_format", "yyyy-MM-dd HH:mm:ss");
            property.put("time_format", "HH:mm:ss");
            property.put("number_format", "0.######");
            property.put("whitespace_stripping", true);
            //设置自动导入的宏模板位置，想对于TemplateLoaderPath的位置
            property.put("auto_import", "/common/commonMacros.ftl as macro");
        }else {
            property.putAll(properties);
        }
        factoryBean.setFreemarkerSettings(property);
        freemarker.template.Configuration configuration=null;
        try {
            configuration=factoryBean.createConfiguration();
            configuration.setSharedVariable("shiro", new ShiroTags());
        } catch (IOException|TemplateException e) {
           logger.error(" freemarker config failure,shiro tags can not be used! use spring boot default config!",e);
        }
        return configuration;
    }

    public HashMap<String, String> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<String, String> properties) {
        this.properties = properties;
    }

    public String getTemplateLoaderPath() {
        return templateLoaderPath;
    }

    public void setTemplateLoaderPath(String templateLoaderPath) {
        this.templateLoaderPath = templateLoaderPath;
    }
}
