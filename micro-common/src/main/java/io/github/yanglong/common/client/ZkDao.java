package io.github.yanglong.common.client;

import io.github.yanglong.common.handler.NodeCacheHandler;
import io.github.yanglong.common.handler.PathChildrenHandler;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;

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
public interface ZkDao {
    /**
     * 关闭客户端
     */
    void close();

    /**
     * 创建节点，如果含有未创建的父节点，将会一起创建
     *
     * @param path       节点路径
     * @param data       数据
     * @param createMode 节点类型，临时，持久，序列，默认临时 {@see org.apache.zookeeper.CreateMode}
     * @param acl        权限,默认world:anyone:crwda {@see org.apache.zookeeper.ZooDefs.Ids}
     * @return 成功或失败
     */
    boolean createNodeWithParent(String path, byte[] data, CreateMode createMode, ArrayList<ACL> acl);

    /**
     * 创建节点，如果含有未创建的父节点，将会失败
     *
     * @param path       节点路径
     * @param data       数据
     * @param createMode 节点类型，临时，持久，序列，默认临时 {@link CreateMode}
     * @param acl        权限,默认world:anyone:crwda {@link org.apache.zookeeper.ZooDefs.Ids}
     * @return 成功或失败
     */
    boolean createNodeOnly(String path, byte[] data, CreateMode createMode, ArrayList<ACL> acl);

    /**
     * 删除指定路径和版本的节点,如果含有子节点，将会一起删除
     *
     * @param path    路径
     * @param version 版本
     * @return true/false
     */
    boolean deleteNode(String path, int version);

    /**
     * 删除指定版本和路径的节点，如果含有子节点，将会一起删除，
     * 删除完成后调用回调函数<code>callback</code>
     *
     * @param path     节点路径
     * @param version  版本
     * @param callback 回调函数
     * @return true/false
     */
    boolean deleteNode(String path, int version, BackgroundCallback callback);

    /**
     * 删除指定路径和版本的节点，如果含有子节点，将会一起删除，
     * 删除完成后使用<code>executor</code>线程池调用回调函数<code>callback</code>
     *
     * @param path     路径
     * @param version  版本
     * @param callback 回调函数
     * @param executor 线程池
     * @return true/false
     */
    boolean deleteNode(String path, int version, BackgroundCallback callback, Executor executor);

    /**
     * 删除指定路径和版本的节点，传递上下文<code>context</code>，如果含有子节点，将会一起删除，
     * 删除完成后使用<code>executor</code>线程池调用回调函数<code>callback</code>
     *
     * @param path     路径
     * @param version  版本
     * @param callback 回调函数
     * @param executor 线程池
     * @param context  上下文对象
     * @return true/false
     */
    boolean deleteNode(String path, int version, BackgroundCallback callback, Executor executor, Object context);

    /**
     * 读取<code>path</code>节点的值(如果异常返回<code>null</code>)
     *
     * @param path 节点路径
     * @return byte数组/null
     */
    byte[] readData(String path);

    /**
     * 读取<code>path</code>节点的值(如果异常返回<code>null</code>),并且会将节点状态信息，如事务id，版本等写入<code>stat</code>对象
     *
     * @param path 节点路径
     * @param stat 节点状态信息
     * @return byte数组/null
     */
    byte[] readData(String path, Stat stat);

    /**
     * 更新节点数据，如果不清楚版本，指定<code>-1</code>，注意如果指定版本可能会造成版本覆盖
     *
     * @param path    节点路径
     * @param data    数据
     * @param version 版本
     * @return true/false
     */
    boolean updateDate(String path, byte[] data, int version);

    /**
     * 获取子节点
     *
     * @param path 节点路径
     * @return 子节点数组
     */
    List<String> getChildren(String path);

    /**
     * 获取子节点并且获取当前节点的状态信息
     *
     * @param path 节点路径
     * @param stat 节点状态
     * @return 子节点数组
     */
    List<String> getChildren(String path, Stat stat);


    /**
     * 获取指定节点的子节点，并设置watcher｛@link org.apache.curator.framework.api.CuratorWatcher｝，
     * 注意此watcher只能使用一次
     *
     * @param path    节点
     * @param watcher watcher
     * @return 子节点数组
     */
    List<String> getChildren(String path, CuratorWatcher watcher);

    /**
     * 获取指定节点的子节点，并设置watcher{@link CuratorWatcher}，
     * 注意此watcher只能使用一次。同时获取节点状态信息
     *
     * @param path    节点
     * @param watcher watcher
     * @param stat    节点状态
     * @return 子节点数组
     */
    List<String> getChildren(String path, CuratorWatcher watcher, Stat stat);

    /**
     * 获取指定节点的子节点，并设置watcher{@link Watcher}，
     * 注意此watcher只能使用一次
     *
     * @param path    节点
     * @param watcher watcher
     * @return 子节点数组
     */
    List<String> getChildren(String path, Watcher watcher);

    /**
     * 获取指定节点的子节点，并设置watcher{@link Watcher}，
     * 注意此watcher只能使用一次。同时获取节点状态信息
     *
     * @param path    节点
     * @param watcher watcher
     * @param stat    节点状态
     * @return 子节点数组
     */
    List<String> getChildren(String path, Watcher watcher, Stat stat);

    /**
     * 添加节点监听器
     *
     * @param path         接听节点
     * @param isCompressed 数据是否压缩
     * @param handler      监听器回调接口
     * @param executor     线程池
     */
    void addNodeWatcher(String path, boolean isCompressed, NodeCacheHandler handler, Executor executor) throws Exception;

    /**
     * 添加子节点监听器
     *
     * @param path      路径
     * @param startMode 初始化模式
     * @param isCached  是否缓存节点数据
     * @param handler   监听器回调接口
     * @param executor  线程池
     */
    void addChildWatcher(String path, PathChildrenCache.StartMode startMode, boolean isCached, PathChildrenHandler handler, Executor executor) throws Exception;
}
