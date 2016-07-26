package io.github.yanglong.demo.config.shiro.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import io.github.yanglong.demo.config.shiro.authentication.RealmService;
import io.github.yanglong.demo.config.shiro.authentication.ShiroRealm;

/**
 * functional describe:对不能在容器启动时注入的属性在此类中注入
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    16-7-26
 */
@Component
public class ConfigAfterInit {
    @Autowired
    private RealmService realmService;
    @Autowired
    private ShiroRealm shiroRealm;

    @PostConstruct
    public void setServiceForRealm(){
        shiroRealm.setRealmService(realmService);
    }
}
