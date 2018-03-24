package com.xiajianhx.demo.springcloud.netflix.client.vo;

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
public class ResultTemplate<T> {
    private Integer code;
    private String msg;
    private T response;

    public ResultTemplate(Integer code, String msg, T response) {
        this.code = code;
        this.msg = msg;
        this.response = response;
    }

    public ResultTemplate(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultTemplate fail(String msg) {
        return new ResultTemplate<>(1, msg, null);
    }

    public static ResultTemplate success(Object response) {
        return new ResultTemplate<>(0, "处理成功", response);
    }

    public static ResultTemplate fail() {
        return fail("处理失败");
    }

    public static ResultTemplate success() {
        return success(null);
    }
}
