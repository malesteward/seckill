package com.bjpowernode.multi.dynamic;/*
 *ClassName:ThreadLocalHoder
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/17 18:59
 *@author:tang@qq.com
 */

public class ThreadLocalHoder {

    public static final ThreadLocal<String> holder = new ThreadLocal<String>();

    /**
     * 在访问数据库之前，我们往当前线程变量中设置一个数据源的key
     *
     * @param dataSourceKey
     */
    public static void setDatSourceKey(String dataSourceKey) {
        holder.set(dataSourceKey);
    }

    /**
     * 我们的那个动态数据源，在运行的时候会先从当前线程变量中获取一个数据源的key，就知道要采用哪个数据源了，然后再执行具体数据库操作
     *
     */
    public static String getDatSourceKey() {
        return holder.get();
    }

}
