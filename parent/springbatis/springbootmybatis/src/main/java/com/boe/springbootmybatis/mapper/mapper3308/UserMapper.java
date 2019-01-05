package com.boe.springbootmybatis.mapper.mapper3308;

import com.bjpowernode.multi.model.User;
import org.springframework.stereotype.Component;

@Component("userMapper3308")
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}