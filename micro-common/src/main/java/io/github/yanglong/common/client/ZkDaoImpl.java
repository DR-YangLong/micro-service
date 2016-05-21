package io.github.yanglong.common.client;

import io.github.yanglong.common.handler.NodeCacheHandler;
import io.github.yanglong.common.handler.PathChildrenHandler;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.listen.ListenerContainer;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * package: zookeeper.one <br/>
 * blog:<a href="http://dr-yanglong.github.io/">dr-yanglong.github.io</a><br/>
 * functional describe:zk客户端封装
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/3/2
 */
public class ZkDaoImpl implements ZkDao {
    private static final Logger log = LoggerFactory.getLogger(ZkDaoImpl.class);
    private static final String DEFAULT_CONNECT_STRING = "localhost:2181";
    private static final String DEFAULT_CONNECT_NAMESPACE = "microservice";
    private CuratorFramework client = null;
    //zk服务端连接字符串
    private String connectString = DEFAULT_CONNECT_STRING;
    //初始化连接目录
    private String nameSpace = DEFAULT_CONNECT_NAMESPACE;


    public ZkDaoImpl(String nameSpace) {
        this(DEFAULT_CONNECT_STRING, nameSpace);
    }

    public ZkDaoImpl() {
        this(DEFAULT_CONNECT_STRING, DEFAULT_CONNECT_NAMESPACE);
    }

    /**
     * 初始化客户端
     *
     * @param connectString 链接字符串
     * @param nameSpace     节点根目录
     */
    public ZkDaoImpl(String connectString, String nameSpace) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder().connectString(connectString).sessionTimeoutMs(10000).
                retryPolicy(retryPolicy).namespace(nameSpace).build();
        client.start();
        this.connectString=connectString;
        this.nameSpace=nameSpace;
    }

    public void close() {
        if (this.client != null) {
            this.client.close();
        }
    }

    public boolean createNodeWithParent(String path, byte[] data, CreateMode createMode, ArrayList<ACL> acl) {
        boolean flag = false;
        try {
            client.create().creatingParentContainersIfNeeded().
                    withMode(createMode != null ? createMode : CreateMode.EPHEMERAL).
                    withACL(acl != null ? acl : ZooDefs.Ids.OPEN_ACL_UNSAFE).
                    forPath(path, data);
            flag = true;
        } catch (Exception e) {
            log.error("create node:" + path + " with data:" + new String(data) + "failure!", e);
        }
        return flag;
    }

    public boolean createNodeOnly(String path, byte[] data, CreateMode createMode, ArrayList<ACL> acl) {
        boolean flag = false;
        try {
            client.create().withMode(createMode != null ? createMode : CreateMode.EPHEMERAL).
                    withACL(acl != null ? acl : ZooDefs.Ids.OPEN_ACL_UNSAFE).
                    forPath(path, data);
            flag = true;
        } catch (Exception e) {
            log.error("create node:" + path + " with data:" + new String(data) + "failure!", e);
        }
        return flag;
    }

    public boolean deleteNode(String path, int version) {
        boolean flag = false;
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(version).forPath(path);
            flag = true;
        } catch (Exception e) {
            log.error("delete the node:" + path + ",with version:" + version + " failure!", e);
        }
        return flag;
    }

    public boolean deleteNode(String path, int version, BackgroundCallback callback) {
        boolean flag = false;
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(version).inBackground(callback).forPath(path);
            flag = true;
        } catch (Exception e) {
            log.error("delete the node:" + path + ",with version:" + version + " failure!", e);
        }
        return flag;
    }

    public boolean deleteNode(String path, int version, BackgroundCallback callback, Executor executor) {
        boolean flag = false;
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(version).inBackground(callback, executor).forPath(path);
            flag = true;
        } catch (Exception e) {
            log.error("delete the node:" + path + ",with version:" + version + " failure!", e);
        }
        return flag;
    }

    public boolean deleteNode(String path, int version, BackgroundCallback callback, Executor executor, Object context) {
        boolean flag = false;
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().withVersion(version).inBackground(callback, context, executor).forPath(path);
            flag = true;
        } catch (Exception e) {
            log.error("delete the node:" + path + ",with version:" + version + " failure!", e);
        }
        return flag;
    }

    public byte[] readData(String path) {
        byte[] data = null;
        try {
            data = client.getData().forPath(path);
        } catch (Exception e) {
            log.error("get the data of node:" + path + ", failure!", e);
        }
        return data;
    }

    public byte[] readData(String path, Stat stat) {
        byte[] data = null;
        try {
            data = client.getData().storingStatIn(stat).forPath(path);
        } catch (Exception e) {
            log.error("get the data of node:" + path + ", failure!", e);
        }
        return data;
    }

    public boolean updateDate(String path, byte[] data, int version) {
        boolean flag = false;
        try {
            client.setData().withVersion(version).forPath(path, data);
            flag = true;
        } catch (Exception e) {
            log.error("update the data of node:" + path + " with value:" + new String(data) + ", failure!", e);
        }
        return flag;
    }


    public List<String> getChildren(String path) {
        List<String> children = null;
        try {
            children = client.getChildren().forPath(path);
        } catch (Exception e) {
            log.error("get the children of node：" + path + " failure！", e);
        }
        return children;
    }


    public List<String> getChildren(String path, Stat stat) {
        List<String> children = null;
        try {
            children = client.getChildren().storingStatIn(stat).forPath(path);
        } catch (Exception e) {
            log.error("get the children of node：" + path + " failure！", e);
        }
        return children;
    }

    public List<String> getChildren(String path, CuratorWatcher watcher) {
        List<String> children = null;
        try {
            children = client.getChildren().usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            log.error("get the children of node：" + path + " failure！", e);
        }
        return children;
    }

    public List<String> getChildren(String path, CuratorWatcher watcher, Stat stat) {
        List<String> children = null;
        try {
            children = client.getChildren().storingStatIn(stat).usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            log.error("get the children of node：" + path + " failure！", e);
        }
        return children;
    }

    public List<String> getChildren(String path, Watcher watcher) {
        List<String> children = null;
        try {
            children = client.getChildren().usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            log.error("get the children of node：" + path + " failure！", e);
        }
        return children;
    }

    public List<String> getChildren(String path, Watcher watcher, Stat stat) {
        List<String> children = null;
        try {
            children = client.getChildren().storingStatIn(stat).usingWatcher(watcher).forPath(path);
        } catch (Exception e) {
            log.error("get the children of node：" + path + " failure！", e);
        }
        return children;
    }

    public void addNodeWatcher(String path, boolean isCompressed, final NodeCacheHandler handler, Executor executor) throws Exception {
        final NodeCache nodeCache = new NodeCache(this.client, path, isCompressed);
        nodeCache.start(true);
        ListenerContainer<NodeCacheListener> listenerContainer = nodeCache.getListenable();
        if (listenerContainer != null) {
            listenerContainer.addListener(() -> {
                handler.nodeChanged(nodeCache);
            }, executor);
        } else {
            listenerContainer.addListener(() -> {
                handler.nodeChanged(nodeCache);
            });
        }
    }

    public void addChildWatcher(String path, PathChildrenCache.StartMode startMode, boolean isCached, PathChildrenHandler handler, Executor executor) throws Exception {
        final PathChildrenCache childrenCache = new PathChildrenCache(this.client, path, isCached);
        childrenCache.start(startMode);
        ListenerContainer<PathChildrenCacheListener> listenerContainer = childrenCache.getListenable();
        if (executor != null) {
            listenerContainer.addListener((CuratorFramework client, PathChildrenCacheEvent event) -> {
                handler.childrenChanged(childrenCache, event);
            }, executor);
        } else {
            listenerContainer.addListener((CuratorFramework client, PathChildrenCacheEvent event) -> {
                handler.childrenChanged(childrenCache, event);
            });
        }
    }

    public String getConnectString() {
        return connectString;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }
}
