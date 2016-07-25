package io.github.yanglong.demo.dao;



import java.util.Map;

import io.github.yanglong.demo.config.mybatis.MyBatisRepository;
import io.github.yanglong.demo.model.User;

@MyBatisRepository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);
    int insert(User record);
    int insertSelective(User record);
    User selectByPrimaryKey(Integer id);
    int updateByPrimaryKeySelective(User record);
    int updateByPrimaryKey(User record);
    User selectPrivileges(Integer id);
    Map<String,Object> selectIdAndPasswordByUserName(String account);
    Integer selectIdentity(String account);
    User selectUserByAccount(String account);
}