package com.boe.springbootmybatis.mapper.mapper3310;

import com.boe.springbootmybatis.model.User;
import org.springframework.stereotype.Component;

@Component("userMapper3310")
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}