package com.atguigu.gulimall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public ResultBean attrRelation(List<AttrGroupRelationVo> params) {
        List<AttrAttrgroupRelationEntity> entityList = params.stream().map((item)->{
            AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
            BeanUtil.copyProperties(item, entity);
            return entity;
        }).collect(Collectors.toList());
        boolean b = saveBatch(entityList);
        if (b) {
            return ResultBean.success("");
        } else {
            return ResultBean.fail("");
        }
    }

}