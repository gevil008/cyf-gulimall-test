package com.atguigu.gulimall.auth.feign;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.auth.vo.UserLoginVo;
import com.atguigu.gulimall.auth.vo.UserRegistVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "gulimall-member", url = "localhost:8082/gulimall-member")
public interface MeberFeignService {

    @PostMapping("/member/member/regist")
    ResultBean regist(@RequestBody UserRegistVo vo);

    @PostMapping("/member/member/login")
    ResultBean login(@RequestBody UserLoginVo vo);
}
