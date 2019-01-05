package com.bjpowernode.seckill.constants;/*
 *ClassName:Constants
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/18 19:06
 *@author:tang@qq.com
 */

public class Constants {
    public  static  final String REDIS_GOODS = "redis:goods:";
    /**
     * 0代表成功
     */
    public static final int ZERO = 0;

    /**
     * 1代表失败
     */
    public static final int ONE = 1;

    /**
     * 商品库存
     */
    public static final String REDIS_STORE = "redis:store:";
    /**
     * 商品购买标记商品ID  + 用户ID
     *      * redis:buy:1:1029
     */
    public static final String REDIS_BUY = "redis:buy:";

    /**
     * 商品购买标记 每个商品进行一个限流
     */
    public static final String REDIS_LIMIT = "redis:limit:";

    /**
     * 秒杀商品最大限流值 10w
     */
    public static final int MAX_LIMIT = 100000;
    /**
     * Redis的key标准命名方式是 冒号 分割
     * Redis中的商品秒杀结果的key的前缀
     *
     * redis:result:1:11268
     * redis:result:2:11268
     * redis:result:3:11268
     * redis:result:4:11268
     * redis:result:5:11268
     * redis:result:6:11268
     * redis:result:7:11268
     */
    public static final String REDIS_RESULT = "redis:result:";
}
