package com.bjpowernode.multi.service;/*
 *ClassName:UserService
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/15 22:25
 *@author:tang@qq.com
 */


import com.bjpowernode.multi.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private com.bjpowernode.multi.mapper.mapper3307.UserMapper userMapper3307;
    @Autowired
    private com.bjpowernode.multi.mapper.mapper3308.UserMapper userMapper3308;
    @Autowired
    private com.bjpowernode.multi.mapper.mapper3309.UserMapper userMapper3309;
    @Autowired
    private com.bjpowernode.multi.mapper.mapper3310.UserMapper userMapper3310;


    public User userMapper3307(Integer id){
        return userMapper3307.selectByPrimaryKey(id);
    }
    public User userMapper3308(Integer id){
        return userMapper3308.selectByPrimaryKey(id);
    }
    public User userMapper3309(Integer id){
        return userMapper3309.selectByPrimaryKey( id);
    }
    public User userMapper3310(Integer id){
        return userMapper3310.selectByPrimaryKey( id);
    }

    /**
     * 更新3307这个mapper
     * @param user
     * @return
     */
    @Transactional(transactionManager = "transactionManager")
    public int updateUserMapper3307(User user) {
        int cout = userMapper3307.updateByPrimaryKeySelective(user);
        System.out.println("userMapper3307" + cout);
      //  int num = 10/0;
        return cout;
    }
}
