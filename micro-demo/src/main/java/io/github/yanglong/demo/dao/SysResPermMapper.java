package io.github.yanglong.demo.dao;


import io.github.yanglong.demo.config.mybatis.MyBatisRepository;
import io.github.yanglong.demo.model.SysResPerm;

@MyBatisRepository
public interface SysResPermMapper {
    
    int deleteByPrimaryKey(Integer id);

    int insert(SysResPerm record);

    int insertSelective(SysResPerm record);

    SysResPerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysResPerm record);

    int updateByPrimaryKey(SysResPerm record);
}