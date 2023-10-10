package com.atguigu.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 返回统一格式数据
 *
 * @param <T>
 */
@Data
@NoArgsConstructor
@Slf4j
@ApiModel(value = "返回统一格式数据")
public class ResultBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否成功")
    private boolean successful;

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "承载数据")
    private T resultValue;

    @ApiModelProperty(value = "返回的消息")
    private String message;

    /**
     * 私有的构造方法
     *
     * @param code
     * @param message
     * @param data
     */
    private ResultBean(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.resultValue = data;
        //如果状态码为200则返回true,不为200全是false
        this.successful = this.code.equals(BizCodeEnum.SUCCESS.getCode());
    }

    public static <T> ResultBean success(String msg) {
        return new ResultBean(BizCodeEnum.SUCCESS.getCode(), msg, "");
    }

    public static <T> ResultBean success(T data) {
        return new ResultBean(BizCodeEnum.SUCCESS.getCode(), BizCodeEnum.SUCCESS.getMsg(), (T) data);
    }

    public static <T> ResultBean fail(String message) {
        return new ResultBean(BizCodeEnum.ERROR_500.getCode(), message, "");
    }

    public static <T> ResultBean fail(String message, T data) {
        return new ResultBean(BizCodeEnum.ERROR_500.getCode(), message, (T) data);
    }

    public static <T> ResultBean fail(Integer code, String message) {
        return new ResultBean(code, message, "");
    }
}
