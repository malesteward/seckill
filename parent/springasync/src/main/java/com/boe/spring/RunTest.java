package com.boe.spring;/*
 *ClassName:RunTest
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/13 19:20
 *@author:tang@qq.com
 */

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class RunTest {
    public static void main(String[] args) {
    /*    String path = "classpath:applinocation.xml";
         new ClassPathXmlApplicationContext(path);*/
        new ClassPathXmlApplicationContext("classpath:applinocation.xml");
    }

}
