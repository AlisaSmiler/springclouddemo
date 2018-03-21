package com.xiajianhx.demo.springcloud.netflix.client.bean;

import javax.persistence.*;

@Table(name = "bao_seckill_goods")
public class BaoSeckillGoods {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商品编号
     */
    @Column(name = "goods_no")
    private String goodsNo;

    /**
     * 商品名称
     */
    @Column(name = "goods_name")
    private String goodsName;

    /**
     * 库存数量
     */
    @Column(name = "stock_num")
    private Integer stockNum;

    /**
     * 开始秒杀时间
     */
    @Column(name = "start_time")
    private Long startTime;

    /**
     * 结束秒杀时间
     */
    @Column(name = "end_time")
    private Long endTime;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Long createTime;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商品编号
     *
     * @return goods_no - 商品编号
     */
    public String getGoodsNo() {
        return goodsNo;
    }

    /**
     * 设置商品编号
     *
     * @param goodsNo 商品编号
     */
    public void setGoodsNo(String goodsNo) {
        this.goodsNo = goodsNo;
    }

    /**
     * 获取商品名称
     *
     * @return goods_name - 商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名称
     *
     * @param goodsName 商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取库存数量
     *
     * @return stock_num - 库存数量
     */
    public Integer getStockNum() {
        return stockNum;
    }

    /**
     * 设置库存数量
     *
     * @param stockNum 库存数量
     */
    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    /**
     * 获取开始秒杀时间
     *
     * @return start_time - 开始秒杀时间
     */
    public Long getStartTime() {
        return startTime;
    }

    /**
     * 设置开始秒杀时间
     *
     * @param startTime 开始秒杀时间
     */
    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    /**
     * 获取结束秒杀时间
     *
     * @return end_time - 结束秒杀时间
     */
    public Long getEndTime() {
        return endTime;
    }

    /**
     * 设置结束秒杀时间
     *
     * @param endTime 结束秒杀时间
     */
    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Long getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
}