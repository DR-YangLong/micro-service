package io.github.yanglong.demo.dao;



import java.util.List;

import io.github.yanglong.demo.config.mybatis.MyBatisRepository;
import io.github.yanglong.demo.model.PrivilegeModel;
import io.github.yanglong.demo.model.SysResources;

@MyBatisRepository
public interface SysResourcesMapper {

    int deleteByPrimaryKey(Integer resId);

    int insert(SysResources record);

    int insertSelective(SysResources record);

    SysResources selectByPrimaryKey(Integer resId);

    int updateByPrimaryKeySelective(SysResources record);

    int updateByPrimaryKey(SysResources record);

    List<PrivilegeModel> selectResourcePris();

    List<PrivilegeModel> selectResourcePerms();

    List<PrivilegeModel> selectResourceRoles();

}