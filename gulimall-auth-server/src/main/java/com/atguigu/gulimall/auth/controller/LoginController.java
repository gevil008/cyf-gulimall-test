package com.atguigu.gulimall.auth.controller;

import com.atguigu.common.constant.AuthServiceConstant;
import com.atguigu.common.exception.CustomCodeEnum;
import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.auth.feign.MeberFeignService;
import com.atguigu.gulimall.auth.feign.ThirdPartFeignService;
import com.atguigu.gulimall.auth.vo.UserLoginVo;
import com.atguigu.gulimall.auth.vo.UserRegistVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Controller
public class LoginController {
    @Autowired
    ThirdPartFeignService thirdPartFeignService;

    @Autowired
    MeberFeignService meberFeignService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @ResponseBody
    @GetMapping("/sms/sendCode")
    public ResultBean sendCode(@RequestParam("phone") String phone) {
        // 1、接口防刷

        // 2、高频访问
        String s = redisTemplate.opsForValue().get(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + phone);
        if (!StringUtils.isEmpty(s)) {
            Long s1 = Long.parseLong(s.split("_")[1]);
            if (System.currentTimeMillis() - s1 < 60000) {
                return ResultBean.fail(CustomCodeEnum.SMS_CODE_EXCEPTION.getCode(),CustomCodeEnum.SMS_CODE_EXCEPTION.getMsg());
            }
        }
        String code = String.valueOf(System.currentTimeMillis()).substring(0, 5) + "_" + System.currentTimeMillis();
        redisTemplate.opsForValue().set(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + phone, code, 5, TimeUnit.MINUTES);
        return thirdPartFeignService.sendCode(phone, code.split("_")[0]);
    }


    @PostMapping("/regist")
    public String regist(@Valid UserRegistVo vo, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()){
            Map<String, Object> errors = result.getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
            // model.addAttribute("errors", errors);
            redirectAttributes.addFlashAttribute("errors", errors);

            // 校验失败，重定向到注册页
            return "redirect:http://auth.gulimall.com/reg.html";
        }
        String code = vo.getCode();
        String redisCode = redisTemplate.opsForValue().get(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
        if (!StringUtils.isEmpty(redisCode)){
            if (code.equals(redisCode.split("_")[0])){
                // 删除验证码，令牌机制
                redisTemplate.delete(AuthServiceConstant.SMS_CODE_CACHE_PREFIX + vo.getPhone());
                // 调用注册服务
                ResultBean regist = meberFeignService.regist(vo);
                if (regist.isSuccessful()){
                    return "redirect:http://auth.gulimall.com/login.html";
                }else{
                    Map<String, Object> errors = new HashMap<>();
                    errors.put("msg", regist.getMessage());
                    redirectAttributes.addFlashAttribute("errors", errors);
                    // 校验失败，重定向到注册页
                    return "redirect:http://auth.gulimall.com/reg.html";
                }
            }
        }else{
            Map<String, Object> errors = new HashMap<>();
            errors.put("code", "验证码错误");
            redirectAttributes.addFlashAttribute("errors", errors);
            // 校验失败，重定向到注册页
            return "redirect:http://auth.gulimall.com/reg.html";
        }

        // 注册成功回到首页，登录页
        return "redirect:http://auth.gulimall.com/login.html";
    }

    @PostMapping("/login")
    public String login(UserLoginVo vo, RedirectAttributes attributes, HttpSession session){
        ResultBean bean = meberFeignService.login(vo);
        if (bean.isSuccessful()) {
            session.setAttribute(AuthServiceConstant.LOGIN_USER, bean.getResultValue());
            return "redirect:http://gulimall.com";
        }else{
            Map<String, String> errors = new HashMap<>();
            errors.put("msg", bean.getMessage());
            attributes.addFlashAttribute("errors", errors);
            return "redirect:http://auth.gulimall.com/login.html";
        }
    }
}
