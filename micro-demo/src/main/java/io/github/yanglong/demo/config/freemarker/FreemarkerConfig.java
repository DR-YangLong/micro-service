package io.github.yanglong.demo.config.freemarker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.util.HashMap;
import java.util.Properties;

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
    private HashMap<?,?> properties;
    private String templateLoaderPath;
    /**
     * 配置freemarker基本设置，此处可以配置freemarkerVariables但不推荐是使用，
     * 每次配置需要重新启动应用，并且将业务与freemarker绑定在了一起
     * @return Freemarker Configuration
     */
    @Bean(name = "freemarkerConfiguration")
    public freemarker.template.Configuration freeMarkerConfigurationFactoryBean(){
        FreeMarkerConfigurationFactoryBean factoryBean=new FreeMarkerConfigurationFactoryBean();
        factoryBean.setTemplateLoaderPath("classpath:/template");
        Properties properties=new Properties();
        properties.put("tag_syntax","auto_detect");
        //缓存时间，0为不缓存
        properties.put("template_update_delay","0");
        properties.put("defaultEncoding","UTF-8");
        properties.put("url_escaping_charset","UTF-8");
        properties.put("locale","zh_CN");
        properties.put("date_format","yyyy-MM-dd");
        properties.put("datetime_format","yyyy-MM-dd HH:mm:ss");
        properties.put("time_format","HH:mm:ss");
        properties.put("number_format","0.######");
        properties.put("whitespace_stripping",true);
        //设置自动导入的宏模板位置，想对于TemplateLoaderPath的位置
        properties.put("auto_import","/common/commonMacros.ftl as macro");
        factoryBean.setFreemarkerSettings(properties);
        return factoryBean.getObject();
    }

    public HashMap<?, ?> getProperties() {
        return properties;
    }

    public void setProperties(HashMap<?, ?> properties) {
        this.properties = properties;
    }

    public String getTemplateLoaderPath() {
        return templateLoaderPath;
    }

    public void setTemplateLoaderPath(String templateLoaderPath) {
        this.templateLoaderPath = templateLoaderPath;
    }
}
