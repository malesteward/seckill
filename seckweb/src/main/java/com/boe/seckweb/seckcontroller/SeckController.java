package com.boe.seckweb.seckcontroller;/*
 *ClassName:SeckController
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/18 15:54
 *@author:tang@qq.com
 */

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.seckill.constants.Constants;
import com.bjpowernode.seckill.model.Goods;
import com.bjpowernode.seckill.model.Orders;
import com.bjpowernode.seckill.rto.ResultObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
//@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SeckController {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private JmsTemplate jmsTemplate;

    @RequestMapping("/")
    public String index(Model model) {
        Set<String> stringSet = redisTemplate.keys(Constants.REDIS_GOODS + "*");

        List<String> redisGoodsList = redisTemplate.opsForValue().multiGet(stringSet);
        List<Goods> goodsList = new ArrayList<>();
        for (String goodsJSON : redisGoodsList) {
            Goods goods = JSONObject.parseObject(goodsJSON, Goods.class);
            goodsList.add(goods);
        }
        model.addAttribute("goodsList", goodsList);
        return "index";
    }

    @GetMapping("/seckill/goods/{id}")
    public String showGoods(Model model, @PathVariable("id") Integer id) {
        String goodstr = redisTemplate.opsForValue().get(Constants.REDIS_GOODS + id);
        Goods goods = JSONObject.parseObject(goodstr, Goods.class);
        model.addAttribute("goods", goods);
        model.addAttribute("currentTime", System.currentTimeMillis());
        System.out.println("开始时间" + goods.getStarttime());
        System.out.println("结束时间" + goods.getEndtime());
        System.out.println(goods.getId());
        return "goods";

    }

    @RequestMapping(value = "/secking/random/{id}")
    public @ResponseBody
    ResultObject getRandom(@PathVariable("id") Integer id) {
        ResultObject resultObject = new ResultObject();
        String goodsJSON = redisTemplate.opsForValue().get(Constants.REDIS_GOODS + id);
        Goods goods = JSONObject.parseObject(goodsJSON, Goods.class);


        //该商品秒杀开始时间的毫秒数
        long startTime = goods.getStarttime().getTime();
        long endTine = goods.getEndtime().getTime();
        long currentTime = System.currentTimeMillis();

        if (currentTime < startTime) {
            //秒杀未开始
            resultObject.setErrorCode(Constants.ONE);
            resultObject.setErrorMessage("秒杀未开始");
            return resultObject;
        } else if (currentTime > endTine) {
            //秒杀已结束
            resultObject.setErrorCode(Constants.ONE);
            resultObject.setErrorMessage("秒杀已结束");
            return resultObject;

        } else {
            //秒杀已开始，可以返回商品秒杀的唯一标志
            resultObject.setErrorCode(Constants.ZERO);
            resultObject.setErrorMessage("秒杀已开始");
            resultObject.setData(goods.getRandomname());
            return resultObject;
        }

    }

    /**
     * 开始正真的秒杀  获取秒杀标志
     */
    @RequestMapping(value = "/seckweb/goods/{id}/{random}")
    public @ResponseBody
    ResultObject seckill(@PathVariable("id") Integer id, @PathVariable("random") String random) {
        ResultObject resultObject = new ResultObject();

        Integer uid = 888;
        //1、验证请求参数的合法性
        if (random.length() != 36) {
            //请求参数长度不合法
            resultObject.setErrorCode(Constants.ONE);
            resultObject.setErrorMessage("请求参数不合法");
            return resultObject;
        }
        String goodsStr = redisTemplate.opsForValue().get(Constants.REDIS_GOODS + id);
        //转换为对象
        Goods goods = JSONObject.parseObject(goodsStr, Goods.class);
        if (!StringUtils.equals(goods.getRandomname(), random)) {
            resultObject.setErrorCode(Constants.ONE);
            resultObject.setErrorMessage("请求参数不合法");
            return resultObject;
        }
        //2、再次验证一下秒杀时间是否合法（此步骤不是必须的，可以省略，但是为了万无一失，建议加上）
        //该商品秒杀开始时间的毫秒数
        long startTime = goods.getStarttime().getTime();
        long endTine = goods.getEndtime().getTime();
        long currentTime = System.currentTimeMillis();

        if (currentTime < startTime) {
            //秒杀未开始
            resultObject.setErrorCode(Constants.ONE);
            resultObject.setErrorMessage("秒杀未开始~");
            return resultObject;

        } else if (currentTime > endTine) {
            //秒杀已结束
            resultObject.setErrorCode(Constants.ONE);
            resultObject.setErrorMessage("秒杀已结束~");
            return resultObject;

        } else {
            //秒杀已开始，可以秒杀
            //3、验证商品库存是否抢光，store <= 0 就是卖光了
            Integer store = StringUtils.isEmpty(redisTemplate.opsForValue().get(Constants.REDIS_STORE + goods.getId())) ? 0 : Integer.parseInt(redisTemplate.opsForValue().get(Constants.REDIS_STORE + goods.getId()));
            if (store <= 0 ){
                resultObject.setErrorCode(Constants.ONE);
                resultObject.setErrorMessage("来晚了，商品已经抢光~");
                return resultObject;
            }
            //验证用户是否已经抢过了这个商品信息
            //在哪里放入这个信息？？？？？？？
            String buy = redisTemplate.opsForValue().get(Constants.REDIS_BUY + id + ":" + uid);
            if (StringUtils.isNotEmpty(buy)){
                resultObject.setErrorCode(Constants.ONE);
                resultObject.setErrorMessage("你已经购买过这个商品了~");
                return resultObject;
            }
            //限流
            //如何把请求放入redis 中呢下面开始放的请求=======================================
            Long currentSize = redisTemplate.opsForList().size(Constants.REDIS_LIMIT + id);
            if (currentSize >= Constants.MAX_LIMIT) {
                resultObject.setErrorCode(Constants.ONE);
                //产品经理给我们的（怎么提示，你可以和产品经理协商）
                resultObject.setErrorMessage("服务器太挤了，没有挤进去~");
                return resultObject;
            } else {
                //请求放入list一个==============================
                Long afterPushSize =  redisTemplate.opsForList().leftPush(Constants.REDIS_LIMIT + id, String.valueOf(uid));
                if (afterPushSize > Constants.MAX_LIMIT) {
                    //不能放的，把刚才已经放进来的这个元素要弹出去（也就是删掉）
                    redisTemplate.opsForList().rightPop(Constants.REDIS_LIMIT + id);

                    resultObject.setErrorCode(Constants.ONE);
                    //产品经理给我们的（怎么提示，你可以和产品经理协商）
                    resultObject.setErrorMessage("服务器太挤了，没有挤进去~");
                    return resultObject;
                }
            }

            //6、可以执行秒杀了
            //a、减库存
            //思路：
            //传统的思路，就是去数据库减库存，此处是秒杀场景，大流量高并发访问，使用数据库直接减库存，会给数据库造成巨大压力
            //优化的思路，就是采用Redis减库存，redis可以支撑大流量高并发访问

            //减库存常见问题：行业的术语：“超卖问题”--> 本来只有1000件商品，但时间卖出去了1008件商品，下了1008个订单，卖多了
            //1、锁 --> 线程锁（只能用于单机版），分布式锁（集群、分布式下都可以），数据库锁（乐观锁、悲观锁）
            //2、队列 --> redis内部减库存是队列机制（单线程排队进行）

            //减完后的库存值
            Long leftStore = redisTemplate.opsForValue().decrement(Constants.REDIS_STORE + id, 1);
            //这里和前面的三元是有关系的
            if (leftStore >= 0) {
              //减库存成功之后，就立刻把用户对该商品的购买标记放入到redis，以避免用户同一个商品抢购多次
                redisTemplate.opsForValue().set(Constants.REDIS_BUY + id + ":" + uid, String.valueOf(uid));
                Orders orders = new Orders();
                orders.setBuynum(1);
                orders.setBuyprice(goods.getPrice());
                orders.setCreatetime(new Date());
                orders.setGoodsid(id);
                orders.setOrdermoney(goods.getPrice().multiply(new BigDecimal(1)));
                orders.setStatus(1);//1下单待支付
                orders.setUid(uid);

                //不直接发对象消息ObjectMessage，采用文本消息TextMessage来发送
                String ordersJSON = JSONObject.toJSONString(orders);

                //MQ出场，MQ流量削峰，原来 1 秒内50万请求，削峰之后，1秒内 1万请求
                jmsTemplate.send(new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        return session.createTextMessage(ordersJSON);
                    }
                });
                //我们给页面提示一个中间结果（不是最终结果，引导前端用户去等待最终结果，页面到时候通过ajax异步去后台获取结果）
                resultObject.setErrorCode(Constants.ZERO);
                resultObject.setErrorMessage("秒杀请求已提交，正在处理......");
                return resultObject;
            }else {
               // 这里库存为负数了
                //不能下订单
                //减成负数了，把库存值恢复回去 （如果不想redis被减成负数，那么采用redis的乐观锁机制来实现）
                //我们没有采用乐观锁，是因为乐观锁的失败率比较高，需要反复地尝试，尝试3~5，或者就是一直尝试直到成功或失败
                redisTemplate.opsForValue().increment(Constants.REDIS_STORE + id,1);
                resultObject.setErrorCode(Constants.ONE);
                resultObject.setErrorMessage("来晚了，商品已经抢光了~");
                return resultObject;
            }
        }
    }
}
