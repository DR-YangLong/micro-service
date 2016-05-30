package io.github.yanglong.client.reference;

import io.github.yanglong.client.config.CallServiceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * functional describe:
 * remote service manager.client use <code>getRemoteAddress</code> method to get the provider.
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-5-30
 */
@Component
public class RemoteManager {
    //需要使用的所有远程服务名称列表
    private HashSet<String> serviceNames;
    //服务url和远程服务地址映射
    private ConcurrentHashMap<String,LinkedHashSet<String>> urlMappingNade;
    @Autowired
    private CallServiceConfig callServiceConfig;

    /**
     * 初始化
     */
    @PostConstruct
    public void init(){
        //获取配置
        //从配置中获取请求链接映射,清洗不符合标准的url
        //根据请求链接到zk中获取服务提供者地址使用异步多线程
        //   1.转换为节点名称
        //   2.加监听器
        //   3.获取当前服务提供者
    }


    /**
     * 服务是否已经离线
     * @param serviceName 服务名称
     * @return true/false 如果服务已经离线，则返true，否则返回false
     */
    private boolean isAborted(String serviceName){
        boolean aborted;
        aborted=serviceNames.contains(serviceName);
        if(aborted){
            String url=callServiceConfig.getServiceRef().get(serviceName);
            aborted=CollectionUtils.isEmpty(this.urlMappingNade.get(url));

        }
        return  aborted;
    }

    /**
     * 用服务名称获取URL
     * @param serviceName 服务名称
     * @return url
     */
    private String getRemoteAddrByName(String serviceName){
        String url=callServiceConfig.getServiceRef().get(serviceName);
        return url;
    }

    /**
     * 用服务名称获取远程服务地址，只会获取一个{@link #isAborted(String)}，总是获取第一个，扩展服务降低等功能。
     * @param serviceName 服务名
     * @return 如果服务没有离线，返回请求地址，否则返回null
     */
    public String getRemoteAddress(String serviceName){
        if(isAborted(serviceName))return null;
        return this.urlMappingNade.get(serviceName).iterator().next();
    }

}
