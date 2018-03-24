package com.xiajianhx.demo.springcloud.netflix.client.dao;

import com.xiajianhx.demo.springcloud.netflix.client.bean.BaoSeckillGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

public interface BaoSeckillGoodsMapper extends Mapper<BaoSeckillGoods> {
    @Update("update bao_seckill_goods set stock_num = stock_num - 1 where stock_num >= 1 and goods_no = #{goodsNo}")
    int decreaseStockNum(@Param("goodsNo") String goodsNo);

}