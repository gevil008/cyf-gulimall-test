package com.atguigu.gulimall.ware.feign;

import com.atguigu.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "gulimall-product", url = "localhost:8084/gulimall-coupon")
public interface ProductFeignService {

    /**
     * /product/skuinfo/info/{skuId}
     * <p>
     * 1)、让所有请求过网关；
     * 1、@FeignClient("gulimall-gateway")：给gulimall-gateway所在的机器发请求
     * 2、/api/product/skuinfo/info/{skuId}
     * 2）、直接让后台指定服务处理
     * 1、@FeignClient("gulimall-gateway")
     * 2、/product/skuinfo/info/{skuId}
     *
     * @return R
     */
    @GetMapping("/product/skuinfo/info/{skuId}")
    ResultBean info(@PathVariable("skuId") Long skuId);
}
