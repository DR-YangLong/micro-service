package io.github.yanglong.common.handler;

import org.apache.curator.framework.recipes.cache.NodeCache;

/**
 * package: zookeeper.common <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:触发器，用于NodeCache触发watcher后调用
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/4/23
 */
public interface NodeCacheHandler {
    /**
     * 回调方法，可以在方法内实现事件的处理
     * @param nodeCache 节点缓存
     * @return 节点缓存
     */
    void nodeChanged(NodeCache nodeCache);
}
