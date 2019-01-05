package com.bjpowernode.springboot.mapper;

import com.bjpowernode.springboot.model.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    /**
     * 查询所有的用户信息
    * */
    List<User> selectUser(Map<String,Object> map);

    Long selectTotalPage();
}