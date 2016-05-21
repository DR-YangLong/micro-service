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
        String nodeName = url.replace(".", "-");
        nodeName = nodeName.replace("/", ".");
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
        //去除路径第一个“/”
        String url = nodeName.substring(1, nodeName.length());
        url = url.replace(".", "/");
        url = url.replace("-", ".");
        return url;
    }

    public static void main(String args[]) {
        String url="/test/hello/";
        String nodeName=urlToNodeName(url);
        String covert=nodeNameToUrl(nodeName);
        System.out.print(url.equals(covert));
    }
}
