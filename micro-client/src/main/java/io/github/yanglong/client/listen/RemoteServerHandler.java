package io.github.yanglong.client.listen;

import io.github.yanglong.common.handler.PathChildrenHandler;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

import java.util.LinkedHashSet;
import java.util.concurrent.*;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-5-30
 */
public class RemoteServerHandler implements PathChildrenHandler {
    //URL和远程服务地址映射
    private ConcurrentHashMap<String, LinkedHashSet<String>> urlMappingNade;
    //新增节点队列
    private ConcurrentLinkedQueue<ChildData> addBasket=new ConcurrentLinkedQueue<>();
    //删除节点队列
    private ConcurrentLinkedQueue<ChildData> removeBasket=new ConcurrentLinkedQueue<>();
    //新增线程池
    private Executor addExecutor = Executors.newFixedThreadPool(2);
    //删除线程池
    private Executor removeExecutor = Executors.newFixedThreadPool(2);

    @Override
    public void childrenChanged(PathChildrenCache pathChildrenCache, PathChildrenCacheEvent event) {
        //获取通知类型
        PathChildrenCacheEvent.Type notifyType = event.getType();
        if (PathChildrenCacheEvent.Type.CHILD_ADDED.equals(notifyType)) {
            //新节点创建，有新的provider加入
            //使用并发队列，处理事件通知
            ChildData data=event.getData();
            addBasket.add(data);


        } else if (PathChildrenCacheEvent.Type.CHILD_REMOVED.equals(notifyType)) {
            //节点删除，有provider离线

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


    class RemoveProvider implements Runnable{
        private ConcurrentLinkedQueue<ChildData> addBasket;
        private ConcurrentHashMap<String, LinkedHashSet<String>> urlMappingNade;

        @Override
        public void run() {


        }

        public RemoveProvider(ConcurrentLinkedQueue<ChildData> addBasket, ConcurrentHashMap<String, LinkedHashSet<String>> urlMappingNade) {
            this.addBasket = addBasket;
            this.urlMappingNade = urlMappingNade;
        }
    }

    class AddProvider implements Runnable{
        private ConcurrentLinkedQueue<ChildData> removeBasket;
        private ConcurrentHashMap<String, LinkedHashSet<String>> urlMappingNade;

        @Override
        public void run() {

        }

        public AddProvider(ConcurrentLinkedQueue<ChildData> removeBasket, ConcurrentHashMap<String, LinkedHashSet<String>> urlMappingNade) {
            this.removeBasket = removeBasket;
            this.urlMappingNade = urlMappingNade;
        }
    }
}
