package io.github.yanglong.client.listen;

import io.github.yanglong.common.handler.PathChildrenHandler;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import java.util.LinkedHashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * functional describe:监听器，负责监听zk事件，并维护服务提供者列表
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-5-30
 */
public class RemoteProviderHandler implements PathChildrenHandler {
    //监听的服务节点URL
    private String serviceNode;
    //URL和远程服务地址映射
    private ConcurrentHashMap<String, LinkedHashSet<String>> urlMappingNade;

    @Override
    public void childrenChanged(PathChildrenCache pathChildrenCache, PathChildrenCacheEvent event) {
        //获取通知类型
        PathChildrenCacheEvent.Type notifyType = event.getType();
        if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(notifyType)) {
            //新节点创建，有新的provider加入
            //使用并发队列，处理事件通知
            ChildData data = event.getData();
            String node = data.getPath();
            urlMappingNade.get(serviceNode).add(node);
        } else if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(notifyType)) {
            //节点删除，有provider离线
            ChildData data = event.getData();
            String node = data.getPath();
            urlMappingNade.get(serviceNode).remove(node);
        } else if (PathChildrenCacheEvent.Type.CHILD_UPDATED.equals(notifyType)) {
            //节点更新，provider的描述更新
            //do nothing now
        } else if (PathChildrenCacheEvent.Type.INITIALIZED.equals(notifyType)) {
            //do nothing

        } else if (PathChildrenCacheEvent.Type.CONNECTION_LOST.equals(notifyType)) {
            //do nothing

        } else if (PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED.equals(notifyType)) {
            //do nothing
        } else if (PathChildrenCacheEvent.Type.CONNECTION_SUSPENDED.equals(notifyType)) {
            //do nothing
        }
    }

    public RemoteProviderHandler(String serviceNode, ConcurrentHashMap<String, LinkedHashSet<String>> urlMappingNade) {
        this.serviceNode = serviceNode;
        this.urlMappingNade = urlMappingNade;
    }
}
