package io.github.yanglong.common.config;

import io.github.yanglong.common.client.ZkDao;
import io.github.yanglong.common.client.ZkDaoImpl;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * package: micro.service.yanglong.common.zookeeper.config <br/>
 * functional describe:zk配置器，配置zk dao。
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/17 10:17
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "micro-service")
public class ZkBeanFactory {
    private String nameSpace;
    private String connectString;

    @Bean(name = "zkDao")
    public ZkDao zkDao() {
        ZkDao zkDao = new ZkDaoImpl(this.connectString, this.nameSpace);
        return zkDao;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }
}
