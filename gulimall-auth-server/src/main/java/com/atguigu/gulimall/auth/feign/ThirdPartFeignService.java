package com.atguigu.gulimall.auth.feign;

import com.atguigu.common.utils.ResultBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "gulimall-third-party", url = "localhost:8086/gulimall-third-party")
public interface ThirdPartFeignService {

    @GetMapping(value = "/sms/sendCode")
    ResultBean sendCode(@RequestParam("phone") String phone, @RequestParam("code") String code);
}
