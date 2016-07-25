package io.github.yanglong.demo.service;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.yanglong.demo.config.shiro.authentication.RealmService;
import io.github.yanglong.demo.config.shiro.authentication.ShiroRealm;
import io.github.yanglong.demo.dao.UserMapper;
import io.github.yanglong.demo.model.User;

/**
 * package: com.webarch.core <br/>
 * functional describe:shiro realm查询实现
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/9 16:16
 */
@Service
public class RealmServiceImpl implements RealmService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public Object getUniqueIdentity(String userName) {
        Integer id = userMapper.selectIdentity(userName);
        return id;
    }

    @Override
    public Object getUserPassword(String userName) {
        return null;
    }

    @Override
    public Map<String, Object> getUserUniqueIdentityAndPassword(String userName) {
        Map<String, Object> idAndPwd = userMapper.selectIdAndPasswordByUserName(userName);
        return idAndPwd;
    }

    @Override
    public Collection<String> getPermissions(Object uniqueIdentity) {
        //此处为演示，将权限和角色都放到了user表中，并用","分隔开，一般是分2个表
        User user = userMapper.selectPrivileges((Integer) uniqueIdentity);
        String perms = user.getPerms();
        String[] arr = StringUtils.split(perms, ",");
        List<String> list = Arrays.asList(arr);
        return list;
    }

    @Override
    public Collection<String> getRoles(Object uniqueIdentity) {
        //此处为演示，将权限和角色都放到了user表中，并用","分隔开，一般是分2个表
        User user = userMapper.selectPrivileges((Integer) uniqueIdentity);
        String roles = user.getRoles();
        String[] arr = StringUtils.split(roles, ",");
        List<String> list = Arrays.asList(arr);
        return list;
    }

    @Override
    public Map<String, Collection<String>> getUserRolesAndPerms(Object uniqueIdentity) {
        //此处为演示，将权限和角色都放到了user表中，并用","分隔开，一般是分2个表
        User user = userMapper.selectPrivileges((Integer) uniqueIdentity);
        String perms = user.getPerms();
        String[] permArr = StringUtils.split(perms, ",");
        List<String> permList = Arrays.asList(permArr);
        String roles = user.getRoles();
        String[] arr = StringUtils.split(roles, ",");
        List<String> list = Arrays.asList(arr);
        Map<String, Collection<String>> permsAndRoles = new HashMap<>(2);
        permsAndRoles.put(ShiroRealm.getRoles_in_map_key(), list);
        permsAndRoles.put(ShiroRealm.getPerms_in_map_key(), permList);
        return permsAndRoles;
    }
}
