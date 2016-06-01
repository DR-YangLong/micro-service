package io.github.yanglong.client.listen;

import io.github.yanglong.common.handler.PathChildrenHandler;
import io.github.yanglong.common.utils.URLConvertor;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * functional describe:监听器，负责监听zk事件，并维护服务提供者列表
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-5-30
 */
public class RemoteProviderHandler implements PathChildrenHandler {
    private static final Logger logger = LoggerFactory.getLogger(RemoteProviderHandler.class);
    //监听的服务节点URL
    private String serviceUrl;
    //URL和远程服务地址映射
    private ConcurrentHashMap<String, Vector<String>> urlMappingNode;

    @Override
    public void childrenChanged(PathChildrenCache pathChildrenCache, PathChildrenCacheEvent event) {
        //获取通知类型
        PathChildrenCacheEvent.Type notifyType = event.getType();
        if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(notifyType)) {
            //新节点创建，有新的provider加入
            //TODO 改进处理方式：使用并发队列，处理事件通知
            ChildData data = event.getData();
            String node = data.getPath();
            node=URLConvertor.pathClean(serviceUrl,node);
            Vector<String> servers = getServerList();
            //如果是新的服务节点，添加到服务列表
            if (!servers.contains(node)) {
                servers.add(node);
                urlMappingNode.put(serviceUrl, servers);
            }
        } else if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(notifyType)) {
            //节点删除，有provider离线
            ChildData data = event.getData();
            String node = data.getPath();
            node=URLConvertor.pathClean(serviceUrl,node);
            urlMappingNode.get(serviceUrl).remove(node);
        } else if (PathChildrenCacheEvent.Type.CHILD_UPDATED.equals(notifyType)) {
            //节点更新，provider的描述更新
            //do nothing now
        } else if (PathChildrenCacheEvent.Type.INITIALIZED.equals(notifyType)) {
            //初始化，获取当前已经有的节点信息
            List<ChildData> childDataList = pathChildrenCache.getCurrentData();
            if (!CollectionUtils.isEmpty(childDataList)) {
                List<String> serverAddress = childDataList.stream().map(childData -> URLConvertor.pathClean(serviceUrl,childData.getPath())).
                        collect(Collectors.toList());
                Vector<String> servers = getServerList();
                serverAddress.addAll(servers);
                serverAddress=serverAddress.stream().distinct().collect(Collectors.toList());
                servers=new Vector<>(serverAddress);
                Collections.shuffle(servers);
                urlMappingNode.put(serviceUrl, servers);
            }
        } else if (PathChildrenCacheEvent.Type.CONNECTION_LOST.equals(notifyType)) {
            //do nothing
            logger.debug("丢失链接！！！");
        } else if (PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED.equals(notifyType)) {
            //do nothing
            logger.debug("重新链接上！！！");
        } else if (PathChildrenCacheEvent.Type.CONNECTION_SUSPENDED.equals(notifyType)) {
            //do nothing
            logger.debug("链接中断！！！");
        }
    }

    /**
     * 获取当前url映射中的服务提供者列表，如果没有则创建一个空的返回
     * @return vector 空的或者原来的
     */
    private Vector<String> getServerList(){
        Vector<String> servers =urlMappingNode.get(serviceUrl);
        servers=CollectionUtils.isEmpty(servers)?new Vector<>():servers;
        return servers;
    }

    public RemoteProviderHandler(String serviceUrl, ConcurrentHashMap<String, Vector<String>> urlMappingNode) {
        this.serviceUrl = serviceUrl;
        this.urlMappingNode = urlMappingNode;
    }
}
