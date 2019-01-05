package com.bjpowernode.springboot.service;/*
 *ClassName:UserService
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/7 19:39
 *@author:tang@qq.com
 */

import com.bjpowernode.springboot.model.PageVo;
import com.bjpowernode.springboot.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    /**用户查询
     * */
    PageVo<User> queryUser(Map<String,Object> map);

    /**
     * 用户添加
     * */
    int insertUser(User user);

    int deleteUser(Integer id);


    User queryUserById(Integer id);

    int updateUserById(User user);
}
