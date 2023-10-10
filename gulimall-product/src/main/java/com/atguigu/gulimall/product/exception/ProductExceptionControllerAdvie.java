package com.atguigu.gulimall.product.exception;

import com.atguigu.common.utils.BizCodeEnum;
import com.atguigu.common.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice(basePackages = "com.atguigu.gulimall.product.controller")
public class ProductExceptionControllerAdvie {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultBean handleVaildException(MethodArgumentNotValidException e) {
        log.error("参数校验异常", e);
        BindingResult bindingResult = e.getBindingResult();
        StringBuffer sb = new StringBuffer();
        bindingResult.getFieldErrors().forEach(item -> {
            sb.append(item.getDefaultMessage());
            sb.append(",");
        });
        String substring = sb.substring(0,sb.length() - 1);
        return ResultBean.fail(BizCodeEnum.VAILD_EXCEPTION.getCode(), substring);
    }

    @ExceptionHandler(value = Exception.class)
    public ResultBean handleException(Exception e) {
        log.error("未知异常", e);
        return ResultBean.fail(BizCodeEnum.UNKNOW_EXCEPTION.getCode(), BizCodeEnum.UNKNOW_EXCEPTION.getMsg());
    }
}
