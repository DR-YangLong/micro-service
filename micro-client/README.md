# 客户端
读取yml配置中micro-service下的serviceRef作为初始化的map<服务别名，服务请求链接>，自动将服务请求链接转换为zk节点名，初始化后获取
zk中提供此链接的服务地址，放入vector中，同时提供一个url->vector的map进行provider的管理。使用时
~~~ java
String url=remoteManager.getRemoteAddress("register");
doPost(url,params);
~~~

来获得一个请求服务器地址【包含定义的url pattern】，此处获取vector中的第一个server地址+url pattern。

测试：
配置server和client项目中的yml。
启动zk。
启动server test。
启动client test。

请求：127.0.0.1:8082可以看到client从zk中读取到提供**register**服务的服务器列表。