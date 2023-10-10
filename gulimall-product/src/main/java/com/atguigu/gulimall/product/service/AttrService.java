package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
public interface AttrService extends IService<AttrEntity> {

    ResultBean queryPage(Map<String, Object> params);

    ResultBean saveAttr(AttrVo attr);

    ResultBean getAttrInfo(Long attrId);

    ResultBean updateAttr(AttrVo attr);

    List<Long> selectSearchAttrs(List<Long> attrIds);
}

