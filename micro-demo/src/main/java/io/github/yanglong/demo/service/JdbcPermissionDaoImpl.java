package io.github.yanglong.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.github.yanglong.demo.config.shiro.dynamic.JdbcPermissionDao;
import io.github.yanglong.demo.dao.SysResourcesMapper;
import io.github.yanglong.demo.model.PrivilegeModel;

/**
 * package: com.webarch.core <br/>
 * functional describe: 使用jdbc获取shiro拦截器生成字符串
 *
 * @author DR.YangLong [410357434@163.com]
 * @version 1.0    2015/8/6 19:27
 */
@Repository
public class JdbcPermissionDaoImpl implements JdbcPermissionDao {
    @Autowired
    private SysResourcesMapper resourcesMapper;

    @Override
    public LinkedHashMap<String, String> generateDefinitions() {
        //获取url和角色及细粒度权限的list生成LinkedHashMap
        List<PrivilegeModel> privileges=resourcesMapper.selectResourceRoles();
        LinkedHashMap<String,String> linkedHashMap=null;
        if(privileges!=null && privileges.size()>0){
            linkedHashMap=new LinkedHashMap<String,String>(privileges.size());
            for(PrivilegeModel pri:privileges){
                if(pri!=null){
                    List<String> roles=pri.getRoleName();
                    if(roles!=null && roles.size()>0){
                        String roleStr=String.join(",",roles);
                        roleStr="roles["+roleStr+"]";
                        linkedHashMap.put(pri.getResUrl(),roleStr);
                    }
                }
            }
        }
        return linkedHashMap;
    }

    @Override
    public Map<String, String> findDefinitionsMap() {
        return this.generateDefinitions();
    }
}
