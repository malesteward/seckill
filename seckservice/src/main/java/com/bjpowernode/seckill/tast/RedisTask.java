package com.bjpowernode.seckill.tast;/*
 *ClassName:RedisTask
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/18 17:35
 *@author:tang@qq.com
 */

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.seckill.constants.Constants;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
public class RedisTask {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Scheduled(cron = "0/5 * * * * *")
    public void initRedisGoods(){
       List<Goods> goodsList =  goodsMapper.selectAllSeckGoods();
        for (Goods goods : goodsList){
            String goodsJson = JSONObject.toJSONString(goods);
            redisTemplate.opsForValue().set(Constants.REDIS_GOODS+goods.getId(),goodsJson);
            System.out.println("加载数据到redis中"+new Date());
            //放置redis的商品信息的同时，也放置一份商品的库存（商品库存是单独放的）
            // IfAbsent if:如果，Absent：缺席的，不在场的 -->如果Constants.REDIS_STORE + goods.getId()不存在，就设置，存在就不设置
            //redis中的库存数据只初始化一遍
            redisTemplate.opsForValue().setIfAbsent(Constants.REDIS_STORE+goods.getId(),String.valueOf(goods.getStore()));
        }

    }

}
