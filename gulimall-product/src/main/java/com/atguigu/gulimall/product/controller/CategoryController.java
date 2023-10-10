package com.atguigu.gulimall.product.controller;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;



/**
 * 商品三级分类
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 列表
     */
    @RequestMapping("/list/tree")
    public ResultBean list(){
        return categoryService.listWithTree();
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    public ResultBean info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);
		return ResultBean.success(category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public ResultBean save(@RequestBody CategoryEntity category){
		categoryService.save(category);
		return ResultBean.success("");
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    public ResultBean update(@RequestBody CategoryEntity category){
		return categoryService.updateCategory(category);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public ResultBean delete(@RequestBody Long[] catIds){
        return categoryService.removeMenuByIds(Arrays.asList(catIds));
    }

}
