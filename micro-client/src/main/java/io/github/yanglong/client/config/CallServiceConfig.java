package io.github.yanglong.client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

/**
 * functional describe:client config for what services to called.
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-5-30
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "micro-service")
public class CallServiceConfig {
    private ConcurrentHashMap<String,String> serviceRef;

    public ConcurrentHashMap<String, String> getServiceRef() {
        return serviceRef;
    }

    public void setServiceRef(ConcurrentHashMap<String, String> serviceRef) {
        this.serviceRef = serviceRef;
    }
}
