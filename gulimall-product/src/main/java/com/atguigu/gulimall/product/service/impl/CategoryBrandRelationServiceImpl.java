package com.atguigu.gulimall.product.service.impl;

import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.dao.BrandDao;
import com.atguigu.gulimall.product.dao.CategoryBrandRelationDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.atguigu.gulimall.product.entity.CategoryBrandRelationEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.CategoryBrandRelationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    BrandDao brandDao;

    @Override
    public ResultBean queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new LambdaQueryWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getBrandId, params.get("brandId"))
        );

        return ResultBean.success(new PageUtils(page));
    }

    @Transactional
    @Override
    public ResultBean saveDetail(CategoryBrandRelationEntity params) {
        Long brandId = params.getBrandId();
        Long catelogId = params.getCatelogId();

        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        BrandEntity brandEntity = brandDao.selectById(brandId);

        params.setBrandName(brandEntity.getName());
        params.setCatelogName(categoryEntity.getName());

        int i = baseMapper.insert(params);
        if (i > 0) {
            return ResultBean.success("");
        } else {
            return ResultBean.fail("");
        }
    }

    @Override
    public ResultBean updateCategoryBrand(CategoryBrandRelationEntity params) {
        if (null == params.getBrandId() && null == params.getCatelogId()) {
            return ResultBean.fail("缺少必要参数");
        }
        int i = baseMapper.updateCategoryBrand(params);
        if (i > 0) {
            return ResultBean.success("");
        } else {
            return ResultBean.fail("");
        }
    }

    @Override
    public List<BrandEntity> getBrandByCatId(Long catId) {
        List<CategoryBrandRelationEntity> relationEntityList = this.baseMapper.selectList(new LambdaQueryWrapper<CategoryBrandRelationEntity>().eq(CategoryBrandRelationEntity::getCatelogId, catId));
        List<Long> brandIds = relationEntityList.stream().map(CategoryBrandRelationEntity::getBrandId).collect(Collectors.toList());
        List<BrandEntity> brandEntities = brandDao.selectBatchIds(brandIds);
        return brandEntities;
    }

}