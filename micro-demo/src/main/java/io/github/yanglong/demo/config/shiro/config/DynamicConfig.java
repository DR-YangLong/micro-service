package io.github.yanglong.demo.config.shiro.config;

import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import io.github.yanglong.demo.config.shiro.dynamic.DynamicPermissionDao;
import io.github.yanglong.demo.config.shiro.dynamic.DynamicPermissionService;
import io.github.yanglong.demo.config.shiro.dynamic.DynamicPermissionServiceImpl;

/**
 * package: org.apache.mycat.advisor.web.config.shiro <br/> functional describe:
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2016/4/26 14:43
 */
@Configuration
public class DynamicConfig {
    @Autowired
    private AbstractShiroFilter shiroFilter;
    @Autowired
    private DynamicPermissionDao dynamicPermissionDao;

    @Bean(name = "filterChainDefinitionsFactory")
    @DependsOn("shiroFilter")
    public DynamicPermissionService dynamicPermissionService() {
        DynamicPermissionServiceImpl permissionService = new DynamicPermissionServiceImpl();
        permissionService.setDynamicPermissionDao(dynamicPermissionDao);
        permissionService.setShiroFilter(shiroFilter);
        return permissionService;
    }
}
