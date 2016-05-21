package io.github.yanglong.common.handler;

import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;

/**
 * package: zookeeper.common <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:触发器，用于PathChildrenCache触发触发watcher后调用
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/4/23
 */
public interface PathChildrenHandler {
    /**
     * 回调方法，在方法内实现事件的处理
     *
     * @param pathChildrenCache PathChildrenCache
     * @param event             触发的事件
     * @return PathChildrenCache
     */
    void childrenChanged(PathChildrenCache pathChildrenCache, PathChildrenCacheEvent event);
}
