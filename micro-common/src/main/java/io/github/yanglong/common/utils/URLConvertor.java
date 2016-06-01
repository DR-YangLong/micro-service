package io.github.yanglong.common.utils;

/**
 * package: io.github.yanglong.common.utils <br/>
 * functional describe:网络请求地址和zk节点地址转换器
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/5/21 16:35
 */
public class URLConvertor {

    /**
     * 将请求的url转换为zk的node名称
     *
     * @param url 请求路径
     * @return node名称
     */
    public static String urlToNodeName(final String url) {
        String nodeName = url.startsWith("/") ? url : "/" + url;
        //将url链接中的"."转换成"-"，"/"转换成"."。
        nodeName = nodeName.replace(".", "-").replace("/", ".");
        //生成zk节点名称
        nodeName = "/" + nodeName;
        return nodeName;
    }

    /**
     * 将zk的node名称转换为请求路径
     *
     * @param nodeName 路径名称
     * @return url
     */
    public static String nodeNameToUrl(final String nodeName) {
        //传入zk的节点名称，去除路径第一个“/”
        String url = nodeName.startsWith("/") ? nodeName.substring(1, nodeName.length()) : nodeName;
        //将节点名称中的"."换成"/"，"-"换成"."，还原url
        url = url.replace(".", "/").replace("-", ".");
        return url;
    }


    /**
     * @param url  服务请求链接，对应监听的path的url形式
     * @param path watcher事件中获取到的路径
     * @return String 服务地址
     */
    public static String pathClean(final String url, final String path) {
        String server = path.replace(URLConvertor.urlToNodeName(url), "");
        server = server.startsWith("/") ? server.substring(1, server.length()) : server;
        return server;
    }

    public static void main(String args[]) {
        String url = "/test/hello/";
        String nodeName = urlToNodeName(url);
        String covert = nodeNameToUrl(nodeName);
        System.out.print(url.equals(covert));
    }
}
