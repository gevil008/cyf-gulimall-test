package com.atguigu.gulimall.product.controller;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.common.valid.AddGroup;
import com.atguigu.common.valid.UpdateGroup;
import com.atguigu.common.valid.UpdateProductStateGroup;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;


/**
 * 品牌
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
@RestController
@RequestMapping("product/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("product:brand:list")
    public ResultBean list(@RequestBody Map<String, Object> params){
        return brandService.queryPage(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{brandId}")
    //@RequiresPermissions("product:brand:info")
    public ResultBean info(@PathVariable("brandId") Long brandId){
		BrandEntity brand = brandService.getById(brandId);
        return ResultBean.success(brand);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:brand:save")
    public ResultBean save(@Validated(value = {AddGroup.class}) @RequestBody BrandEntity brand){
		brandService.save(brand);
		return ResultBean.success("");
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:brand:update")
    public ResultBean update(@Validated(value = {UpdateGroup.class}) @RequestBody BrandEntity brand){
		return brandService.updateDetail(brand);
    }

    /**
     * 修改
     */
    @RequestMapping("/updateState")
    //@RequiresPermissions("product:brand:update")
    public ResultBean updateState(@Validated(value = {UpdateProductStateGroup.class}) @RequestBody BrandEntity brand){
        brandService.updateById(brand);
        return ResultBean.success("");
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:brand:delete")
    public ResultBean delete(@RequestBody Long[] brandIds){
		brandService.removeByIds(Arrays.asList(brandIds));
        return ResultBean.success("");
    }

}
