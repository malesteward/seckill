package com.boe.seckweb.date;/*
 *ClassName:DateFormat
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/18 23:51
 *@author:tang@qq.com
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormat {
                                                    //Wed Feb 13 08:00:00 +0800 2012
    public static final String SOURCE = "Thu Feb 01 00:00:00 GMT+08:00 2018";
        //Mon Feb 12 22:41:37 GMT+08:00 2018
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", new Locale("ENGLISH", "CHINA"));
        Date myDate = sdf.parse(SOURCE);
        System.out.println(myDate);
        sdf.applyPattern("EEE MMM dd HH:mm:ss Z yyyy");
        System.out.println(sdf.format(myDate));
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", new Locale("CHINESE", "CHINA"));
        System.out.println(sdf2.format(myDate));
        sdf2.applyPattern("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println(sdf2.format(myDate));
        long miliSeconds = myDate.getTime();
        System.out.println("自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象经过的毫秒数为：" + miliSeconds + "毫秒");
    }

}
