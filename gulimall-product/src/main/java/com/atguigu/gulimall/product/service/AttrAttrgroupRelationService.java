package com.atguigu.gulimall.product.service;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 属性&属性分组关联
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
public interface AttrAttrgroupRelationService extends IService<AttrAttrgroupRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    ResultBean attrRelation(List<AttrGroupRelationVo> params);
}

