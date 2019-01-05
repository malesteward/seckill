package com.bjpowernode.multi.mapper.mapper3309;

import com.bjpowernode.multi.model.User;
import org.springframework.stereotype.Component;

@Component("userMapper3309")
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}