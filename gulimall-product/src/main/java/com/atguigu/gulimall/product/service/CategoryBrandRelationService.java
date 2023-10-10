package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    ResultBean queryPage(Map<String, Object> params);

    ResultBean saveDetail(CategoryBrandRelationEntity params);

    ResultBean updateCategoryBrand(CategoryBrandRelationEntity map);

    List<BrandEntity> getBrandByCatId(Long catId);
}

