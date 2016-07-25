package io.github.yanglong.demo.dao;


import io.github.yanglong.demo.config.mybatis.MyBatisRepository;
import io.github.yanglong.demo.model.SysRole;

@MyBatisRepository
public interface SysRoleMapper {
    
    int deleteByPrimaryKey(Integer roleId);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(Integer roleId);
    
    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);
}