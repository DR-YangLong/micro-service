package io.github.yanglong.demo.dao;


import io.github.yanglong.demo.config.mybatis.MyBatisRepository;
import io.github.yanglong.demo.model.SysPermission;

@MyBatisRepository
public interface SysPermissionMapper {
    
    int deleteByPrimaryKey(Integer permId);

    int insert(SysPermission record);
    
    int insertSelective(SysPermission record);
    
    SysPermission selectByPrimaryKey(Integer permId);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);
}