package com.atguigu.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 返回信息枚举类
 */
@ApiModel(value = "枚举类,用于返回异常信息")
public enum BizCodeEnum {
    SUCCESS(200, "操作成功!"),
    ERROR_500(500, "服务器内部异常!"),
    ERROR_IO_500(500, "io异常!"),
    ERROR_SQL_500(500, "sql语句异常!"),
    ERROR_201(201, "数据重复,请检查!"),
    ERROR_401(401, "尚未登陆,请登录!"),
    ERROR_403(403, "权限不足,请联系管理员!"),
    ERROR_400(400, "参数错误!"),
    UNKNOW_EXCEPTION(10000, "系统未知异常"),
    VAILD_EXCEPTION(10001, "参数校验异常"),
    PRODUCT_UP_EXCEPTION(11000, "商品上架异常"),;


    @ApiModelProperty(value = "状态码")
    private int code;

    @ApiModelProperty(value = "返回的消息")
    private String msg;

    BizCodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
