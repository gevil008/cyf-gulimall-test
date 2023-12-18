package com.atguigu.gulimall.auth.controller;

import com.atguigu.common.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class OAuth2Controller {

    @GetMapping("/oauth2.0/weibo/success")
    public String weibo(@RequestParam("code") String code) throws Exception {
        System.out.println("微博：code"+code);
        Map<String,String> map = new HashMap<>();
        map.put("client_id","2070478803");
        map.put("client_secret","5c939d48b5b69f9f15dac94297ce5d76");
        map.put("grant_type","authorization_code");
        map.put("code",code);
        map.put("redirect_uri","http://auth.gulimall.com/oauth2.0/weibo/success");

        // 根据code换取accessToken
        HttpResponse post = HttpUtils.doPost("http://api.weibo.com", "/oauth2/access_token", "post", null, null, map);

        // 登录成功跳回首页
        return "redirect:http://gulimall.com";
    }
}
