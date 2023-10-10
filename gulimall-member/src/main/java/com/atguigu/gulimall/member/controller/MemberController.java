package com.atguigu.gulimall.member.controller;

import com.atguigu.common.exception.CustomCodeEnum;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.R;
import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.member.entity.MemberEntity;
import com.atguigu.gulimall.member.exception.PhoneExistException;
import com.atguigu.gulimall.member.exception.UsernameExistException;
import com.atguigu.gulimall.member.feign.CouponFeignService;
import com.atguigu.gulimall.member.service.MemberService;
import com.atguigu.gulimall.member.vo.MemberLoginVo;
import com.atguigu.gulimall.member.vo.MemberRegistVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;



/**
 * 会员
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-11 10:53:22
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    CouponFeignService couponFeignService;

    @PostMapping("/login")
    public ResultBean login(@RequestBody MemberLoginVo vo){
        MemberEntity memberEntity = memberService.login(vo);
        if (memberEntity != null) {
            return ResultBean.success(memberEntity);
        }else {
            return ResultBean.fail(CustomCodeEnum.LOGIN_ACCOUNT_PASSWORD_EXCEPTION.getCode(),CustomCodeEnum.LOGIN_ACCOUNT_PASSWORD_EXCEPTION.getMsg());
        }
    }

    @PostMapping("/regist")
    public ResultBean regist(@RequestBody MemberRegistVo vo){
        try {
            memberService.regist(vo);
        } catch (PhoneExistException e) {
            return ResultBean.fail(CustomCodeEnum.PHONE_EXIST_EXCEPTION.getCode(),CustomCodeEnum.PHONE_EXIST_EXCEPTION.getMsg());
        } catch (UsernameExistException e){
            return ResultBean.fail(CustomCodeEnum.USER_EXIST_EXCEPTION.getCode(),CustomCodeEnum.USER_EXIST_EXCEPTION.getMsg());
        }
        return ResultBean.success("");
    }

    @RequestMapping("/coupons")
    public R test(){
        MemberEntity entity = new MemberEntity();
        entity.setNickname("张三");
        R membercoupons = couponFeignService.membercoupons();
        return R.ok().put("member",entity).put("coupons",membercoupons.get("coupons"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("member:member:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("member:member:info")
    public R info(@PathVariable("id") Long id){
		MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("member:member:save")
    public R save(@RequestBody MemberEntity member){
		memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("member:member:update")
    public R update(@RequestBody MemberEntity member){
		memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("member:member:delete")
    public R delete(@RequestBody Long[] ids){
		memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
