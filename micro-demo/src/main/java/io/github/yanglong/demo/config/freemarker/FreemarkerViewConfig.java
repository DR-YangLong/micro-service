package io.github.yanglong.demo.config.freemarker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.util.HashMap;
import java.util.Properties;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-7-25
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ftlviewcofing")
public class FreemarkerViewConfig {
    /**
     * freemarker宏 静态方法导入配置
     */
    private HashMap<String,String> staticAttrabutes;
    private HashMap<String,String> attrabutes;
    private boolean cache;
    private int cacheLimit=0;
    private String prefix="";
    private String suffix=".ftl";
    private String contentType="text/html;charset=UTF-8";
    private String requestContextAttribute="request";
    private boolean exposeSpringMacroHelpers=true;
    private boolean exposeRequestAttributes=true;
    private boolean exposeSessionAttributes=true;
    private int order=0;
    /**
     * 配置freemarker视图解析器
     * @return viewResolver
     */
    @Bean(name = "freeMarkerViewResolver")
    public FreeMarkerViewResolver freeMarkerViewResolver(){
        FreeMarkerViewResolver freeMarkerViewResolver=new FreeMarkerViewResolver();
        freeMarkerViewResolver.setViewClass(org.springframework.web.servlet.view.freemarker.FreeMarkerView.class);
        freeMarkerViewResolver.setAttributesMap(staticAttrabutes);
        if(!CollectionUtils.isEmpty(attrabutes)) {
            Properties properties = new Properties();
            properties.putAll(attrabutes);
            freeMarkerViewResolver.setAttributes(properties);
        }
        //是否启用缓存
        freeMarkerViewResolver.setCache(cache);
        //缓存刷新间隔
        freeMarkerViewResolver.setCacheLimit(cacheLimit);
        //模板前后缀
        freeMarkerViewResolver.setPrefix(prefix);
        freeMarkerViewResolver.setSuffix(suffix);
        //设置编码
        freeMarkerViewResolver.setContentType(contentType);
        freeMarkerViewResolver.setRequestContextAttribute(requestContextAttribute);
        freeMarkerViewResolver.setExposeSpringMacroHelpers(exposeSpringMacroHelpers);
        freeMarkerViewResolver.setExposeRequestAttributes(exposeRequestAttributes);
        freeMarkerViewResolver.setExposeSessionAttributes(exposeSessionAttributes);
        freeMarkerViewResolver.setOrder(order);
        return freeMarkerViewResolver;
    }

    public HashMap<String, ?> getStaticAttrabutes() {
        return staticAttrabutes;
    }

    public void setStaticAttrabutes(HashMap<String, String> staticAttrabutes) {
        this.staticAttrabutes = staticAttrabutes;
    }

    public HashMap<String, String> getAttrabutes() {
        return attrabutes;
    }

    public void setAttrabutes(HashMap<String, String> attrabutes) {
        this.attrabutes = attrabutes;
    }

    public boolean isCache() {
        return cache;
    }

    public void setCache(boolean cache) {
        this.cache = cache;
    }

    public int getCacheLimit() {
        return cacheLimit;
    }

    public void setCacheLimit(int cacheLimit) {
        this.cacheLimit = cacheLimit;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getRequestContextAttribute() {
        return requestContextAttribute;
    }

    public void setRequestContextAttribute(String requestContextAttribute) {
        this.requestContextAttribute = requestContextAttribute;
    }

    public boolean isExposeSpringMacroHelpers() {
        return exposeSpringMacroHelpers;
    }

    public void setExposeSpringMacroHelpers(boolean exposeSpringMacroHelpers) {
        this.exposeSpringMacroHelpers = exposeSpringMacroHelpers;
    }

    public boolean isExposeRequestAttributes() {
        return exposeRequestAttributes;
    }

    public void setExposeRequestAttributes(boolean exposeRequestAttributes) {
        this.exposeRequestAttributes = exposeRequestAttributes;
    }

    public boolean isExposeSessionAttributes() {
        return exposeSessionAttributes;
    }

    public void setExposeSessionAttributes(boolean exposeSessionAttributes) {
        this.exposeSessionAttributes = exposeSessionAttributes;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
