package io.github.yanglong.client.reference;

import io.github.yanglong.client.config.CallServiceConfig;
import io.github.yanglong.client.listen.RemoteProviderHandler;
import io.github.yanglong.common.client.ZkDao;
import io.github.yanglong.common.utils.URLConvertor;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * functional describe:
 * remote service manager.client use <code>getRemoteAddress</code> method to get the provider.
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-5-30
 */
@Component
public class RemoteManager {
    private static final Logger logger = LoggerFactory.getLogger(RemoteManager.class);
    //需要使用的所有远程服务名称列表
    private HashSet<String> serviceNames = new HashSet<>();
    //服务url和远程服务地址映射
    private ConcurrentHashMap<String, Vector<String>> urlMappingNode = new ConcurrentHashMap<>(8);
    @Autowired
    private CallServiceConfig callServiceConfig;
    @Autowired
    private ZkDao zkDao;
    //注册监听
    private ExecutorService executor = Executors.newFixedThreadPool(4);

    /**
     * 初始化
     */
    @PostConstruct
    public void init() throws Exception {
        //获取配置
        //从配置中获取请求链接映射,清洗不符合标准的url
        ConcurrentHashMap<String, String> nameMapping = callServiceConfig.getServiceRef();
        nameMapping.forEach((key, val) -> {
            serviceNames.add(key);
            //   1.转换为节点名称
            String nodeName = URLConvertor.urlToNodeName(val);
            //更新不符合标准的节点
            nameMapping.put(key, val);
            //   2.加监听器
            try {
                zkDao.addChildWatcher(nodeName, PathChildrenCache.StartMode.POST_INITIALIZED_EVENT, true, new RemoteProviderHandler(val, urlMappingNode), executor);
            } catch (Exception e) {
                logger.error("============添加监听器从远程获取provider列表出错,程序结束！================", e);
                System.exit(1);
            }
            //   3.获取当前服务提供者---init事件中方法中
        });
    }


    /**
     * 服务是否已经离线
     *
     * @param serviceName 服务名称
     * @return true/false 如果服务已经离线，则返true，否则返回false
     */
    private boolean isAborted(String serviceName) {
        boolean aborted;
        aborted = serviceNames.contains(serviceName);
        if (aborted) {
            String url = getUlrByName(serviceName);
            aborted = CollectionUtils.isEmpty(this.urlMappingNode.get(url));

        }
        return aborted;
    }

    /**
     * 用服务名称获取URL
     *
     * @param serviceName 服务名称
     * @return url
     */
    private String getUlrByName(String serviceName) {
        String url = callServiceConfig.getServiceRef().get(serviceName);
        return url;
    }

    /**
     * 用服务名称获取远程服务地址，只会获取一个{@link #isAborted(String)}，总是获取第一个，扩展服务降级等功能。
     *
     * @param serviceName 服务名
     * @return 如果服务没有离线，返回请求地址（此地址包含url），否则返回null
     */
    public String getRemoteAddress(String serviceName) {
        if (isAborted(serviceName)) return null;
        String url = getUlrByName(serviceName);
        String address = this.urlMappingNode.get(url).iterator().next();
        if (!StringUtils.isEmpty(address)) {
            address += url;
        }
        return address;
    }

    public HashSet<String> getServiceNames() {
        return serviceNames;
    }

    public void setServiceNames(HashSet<String> serviceNames) {
        this.serviceNames = serviceNames;
    }

    public ConcurrentHashMap<String, Vector<String>> getUrlMappingNode() {
        return urlMappingNode;
    }
}
