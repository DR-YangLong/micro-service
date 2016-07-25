package io.github.yanglong.demo.dao;


import io.github.yanglong.demo.config.mybatis.MyBatisRepository;
import io.github.yanglong.demo.model.SysResRole;

@MyBatisRepository
public interface SysResRoleMapper {
    
    int deleteByPrimaryKey(Integer id);
    
    int insert(SysResRole record);
    
    int insertSelective(SysResRole record);
    
    SysResRole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysResRole record);

    int updateByPrimaryKey(SysResRole record);
}