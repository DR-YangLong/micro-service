package io.github.yanglong.client.controller;

import io.github.yanglong.client.reference.RemoteManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-6-1
 */
@RestController
public class GetServerList {
    @Autowired
    private RemoteManager remoteManager;

    @RequestMapping("test/server")
    public List<String> getServer() {
        String test=remoteManager.getRemoteAddress("register");
        System.out.print(test);
        ConcurrentHashMap<String,Vector<String>> map = remoteManager.getUrlMappingNode();
        Vector<String> servers=map.get("/test/hello");
        return new ArrayList<>(servers);
    }
}
