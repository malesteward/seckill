package com.bjpowernode.multi.test;/*
 *ClassName:Test
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/15 22:43
 *@author:tang@qq.com
 */

import com.bjpowernode.multi.dynamic.DynamicDataSource;
import com.bjpowernode.multi.dynamic.ThreadLocalHoder;
import com.bjpowernode.multi.model.User;
import com.bjpowernode.multi.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        //启动spring容器
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-only-mybatis.xml");
        //从spring容器中获取MessageService这个bean
        //MessageService messageService = (MessageService)context.getBean("messageService");
        UserService userService = context.getBean("userService", UserService.class);
      /*  Integer id = 7;
        User user3307 = userService.userMapper3307(7);
        System.out.println("user3307" + user3307.getName());

        User user3308 = userService.userMapper3308(7);
        System.out.println("user3308" + user3308.getName());

        User user3309 = userService.userMapper3309(7);
        System.out.println("user3309" + user3309.getName());

        User user3310 = userService.userMapper3310(7);
        System.out.println("user3310" + user3310.getName());
*/
        /*
         * 事务处理跟新
         * */
        ThreadLocalHoder.setDatSourceKey(DynamicDataSource.DB3310);
        User user = new User();
        user.setId(6);
        user.setName("宋");
        user.setAge("28");

        int count = userService.updateUserMapper3307(user);
        System.out.println("user3307------" +count);



    }


}
