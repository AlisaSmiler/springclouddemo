package com.xiajianhx.demo.springcloud.netflix.client.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserSeckillVO {

    /**
     * 商品编号
     */
    private String goodsNo;

    /**
     * 库存数量
     */
    private String mobile;

}
