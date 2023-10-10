package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrGroupWithAttrsVo;
import com.atguigu.gulimall.product.vo.SpuItemAttrGroupVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 属性分组
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    ResultBean queryPage(Map<String, Object> params);

    List<AttrEntity> attrRelation(Long attrgroupId);

    ResultBean attrRelationDelete(List<AttrGroupRelationVo> params);

    ResultBean noAttrRelation(Map params);

    List<AttrGroupWithAttrsVo> getAttrGroupWithAttrsByCatelogId(Long catelogId);

    List<SpuItemAttrGroupVo> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId);
}

