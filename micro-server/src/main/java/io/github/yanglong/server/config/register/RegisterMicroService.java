package io.github.yanglong.server.config.register;

import io.github.yanglong.common.client.ZkDao;
import io.github.yanglong.common.utils.URLConvertor;
import io.github.yanglong.server.annotation.MicroService;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * package: micro.service.yanglong.server.config <br/>
 * functional describe:
 * <p>微服务连提取注册器，提取有{@link io.github.yanglong.server.annotation.MicroService}<br/>
 * 注解的类，生成链接转换为zk节点名称，并将本节点ip地址，端口，注册到远程注册中心
 * </p>
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/16 18:06
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "server")
public class RegisterMicroService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterMicroService.class);
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private ZkDao zkDao;
    private String port;

    @PostConstruct
    public void registerService() {
        try {
            port = port == null || "".equals(port) ? "80" : port;
            //获取本机的ip地址，加上端口号，形成微服务请求地址
            String host= InetAddress.getLocalHost().getHostAddress().toString();
            host+=":"+port;
            final String serverString=host;
            //使用applicationContext提取bean
            String[] serviceBeanNames=applicationContext.getBeanNamesForAnnotation(MicroService.class);
            //提取bean中的url
            Arrays.stream(serviceBeanNames).forEach(beanName->{
                String[] urlMapping=ApplicationBeanUtils.determineUrls(applicationContext,beanName);
                //将url转换为节点名称
                Arrays.stream(urlMapping).forEach(url->{
                    //转换到node名称
                    String nodeName= URLConvertor.urlToNodeName(url);
                    //注册
                    String nodePath=nodeName+"/"+serverString;
                    //TODO 注意此处的实现不安全，需要改变节点创建时的权限策略
                    zkDao.createNodeWithParent(nodePath,"1".getBytes(), CreateMode.EPHEMERAL, ZooDefs.Ids.OPEN_ACL_UNSAFE);
                    logger.info("将url："+url+" 注册到了ZK节点："+nodePath);
                });
            });
        } catch (UnknownHostException e) {
            logger.error("could not get ip address!",e);
        }
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public ZkDao getZkDao() {
        return zkDao;
    }

    public void setZkDao(ZkDao zkDao) {
        this.zkDao = zkDao;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
