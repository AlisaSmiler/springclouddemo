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
public class ResultTemplate<T> {
    private Integer code;
    private String msg;
    private T response;
    private Long systemTime = System.currentTimeMillis();

    public ResultTemplate(Integer code, String msg, T response) {
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public ResultTemplate(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
