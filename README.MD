# 微服务小demo

使用Spring boot，restful的框架，用servlet的方式向外提供服务，服务注册到zookeeper。

## 服务者
服务者（provider）框架自动读取带有 MicroService注解的控制器类，并将对应的URL转换为zk的节点，与自身ip地址和端口组成node path注册到zk。

## 消费者
消费者（consumer）指定需要调用的URL地址列表，框架自动转换为zk的节点并在初始化读取服务者（provider）列表，并维护此列表。维护通过zk的监听器实现。