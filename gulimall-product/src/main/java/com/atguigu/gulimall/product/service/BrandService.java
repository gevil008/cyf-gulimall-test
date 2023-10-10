package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.entity.BrandEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 品牌
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
public interface BrandService extends IService<BrandEntity> {

    ResultBean queryPage(Map<String, Object> params);

    ResultBean updateDetail(BrandEntity brand);
}

