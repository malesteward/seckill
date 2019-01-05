package com.bjpowernode.seckill.mapper;

import com.bjpowernode.seckill.model.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Goods record);

    int insertSelective(Goods record);

    Goods selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Goods record);

    int updateByPrimaryKey(Goods record);

    /**
     * 秒杀预热，查询所有的商品信息
     * @return
     */
    List<Goods> selectAllSeckGoods();
}