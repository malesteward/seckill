package com.bjpowernode.seckill.orderService;/*
 *ClassName:OrderService
 *Package:${PACKAGE_BANE}
 *Descripion
 *@date:2018/12/20 21:31
 *@author:tang@qq.com
 */

import com.alibaba.fastjson.JSONObject;
import com.bjpowernode.seckill.constants.Constants;
import com.bjpowernode.seckill.mapper.OrdersMapper;
import com.bjpowernode.seckill.model.Orders;
import com.bjpowernode.seckill.rto.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.connection.SortParameters;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
@Autowired
private OrdersMapper ordersMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

public int addOrders(Orders orders){


    int addRow = ordersMapper.insertSelective(orders);

    if (addRow > 0) {
        //下订单成功，那么整个秒杀流程就处理完了，那么久应该告诉前端页面的结果

        //我们这里不用直接告诉页面，是页面它自己每隔3秒来查询一下结果，但是我们要把结果放在一个地方，这样页面才好查询

        //结果放在哪里？ 数据库、redis （数据库如果每隔3秒查一次，性能较差，所以使用redis存放结果）

        ResultObject resultObject = new ResultObject();
        resultObject.setErrorCode(Constants.ZERO);
        resultObject.setErrorMessage("秒杀成功");
        resultObject.setData(orders);
        String resultJSON = JSONObject.toJSONString(resultObject);
        redisTemplate.opsForValue().set(Constants.REDIS_RESULT + orders.getGoodsid() + ":" + orders.getUid(), resultJSON);

        //说明当前该用户秒杀流程处理完了，那么需要把该用户从限流列表中删除，让后面的人可以再进来一个
        redisTemplate.opsForList().rightPop(Constants.REDIS_LIMIT + orders.getGoodsid());
    } else {
        throw new RuntimeException("下单失败");
    }
    return addRow;

}
    /**
     * 处理订单失败
     *
     * @param orders
     */
    public void processOrderFail(Orders orders) {
        //1、限流列表需要删除一个元素
        redisTemplate.opsForList().rightPop(Constants.REDIS_LIMIT + orders.getGoodsid());

        //2、用户已经购买的标记要删除
        redisTemplate.delete(Constants.REDIS_BUY + orders.getGoodsid() + ":" + orders.getUid());

        //3、库存要恢复回去一个
        redisTemplate.opsForValue().increment(Constants.REDIS_STORE + orders.getGoodsid());
    }

}
