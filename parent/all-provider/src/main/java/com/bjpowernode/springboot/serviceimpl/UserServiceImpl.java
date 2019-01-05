package com.bjpowernode.springboot.serviceimpl;/*
 *ClassName:UserServiceImpl
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/7 19:39
 *@author:tang@qq.com
 */

import com.bjpowernode.springboot.mapper.UserMapper;
import com.bjpowernode.springboot.model.PageVo;
import com.bjpowernode.springboot.model.User;
import com.bjpowernode.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@com.alibaba.dubbo.config.annotation.Service(timeout = 100000)
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public PageVo<User> queryUser(Map<String, Object> map) {
        PageVo<User> vo = new PageVo<>();
        List<User> userList =  userMapper.selectUser(map);
        vo.setPageList(userList);
        vo.setTotalPage(userMapper.selectTotalPage());
        return vo;
    }

    @Override
    public int insertUser(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int updateUserById(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User queryUserById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }
}
