package com.atguigu.gulimall.product.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.atguigu.common.constant.ProductConstant;
import com.atguigu.common.utils.PageUtils;
import com.atguigu.common.utils.Query;
import com.atguigu.common.utils.ResultBean;
import com.atguigu.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.atguigu.gulimall.product.dao.AttrDao;
import com.atguigu.gulimall.product.dao.AttrGroupDao;
import com.atguigu.gulimall.product.dao.CategoryDao;
import com.atguigu.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.entity.CategoryEntity;
import com.atguigu.gulimall.product.service.AttrService;
import com.atguigu.gulimall.product.service.CategoryService;
import com.atguigu.gulimall.product.vo.AttrRespVo;
import com.atguigu.gulimall.product.vo.AttrVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Autowired
    AttrAttrgroupRelationDao attrgroupRelationDao;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryService categoryService;

    @Override
    public ResultBean queryPage(Map<String, Object> params) {
        Integer catelogId = (Integer) params.get("catelogId");
        String type = (String) params.get("type");
        LambdaQueryWrapper<AttrEntity> queryWrapper = new LambdaQueryWrapper<>();
        boolean AttrType = false;
        // 基本属性
        if ("base".equals(type)){
            AttrType = true;
            queryWrapper.eq(AttrEntity::getAttrType, ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        }
        // 销售属性
        if ("sale".equals(type)){
            queryWrapper.eq(AttrEntity::getAttrType, ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
        }
        String key = (String) params.get("key");
        if (!StringUtils.isBlank(key)) {
            queryWrapper.and((obj) -> {
                obj.eq(AttrEntity::getAttrId, key).or().like(AttrEntity::getAttrName, key);
            });
        }
        IPage<AttrEntity> page = null;
        if (0 == catelogId) {
            page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    queryWrapper
            );
        } else {
            queryWrapper.eq(AttrEntity::getCatelogId, catelogId);
            page = this.page(
                    new Query<AttrEntity>().getPage(params),
                    queryWrapper
            );
        }
        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();

        // 属性id集合
        List<Long> attrIds = records.stream().map(AttrEntity::getAttrId).collect(Collectors.toList());
        // 分类id集合
        List<Long> cateLogIds = records.stream().map(AttrEntity::getCatelogId).collect(Collectors.toList());

        // 分类集合
        List<CategoryEntity> categoryEntities = categoryDao.selectBatchIds(cateLogIds);
        // 分类id与分类name集合
        Map<Long, String> categoryEntitiesMap = categoryEntities.stream().collect(Collectors.toMap(CategoryEntity::getCatId, CategoryEntity::getName, (key1, key2) -> key1));

        Map<Long, Long> relationEntitiesMap = null;
        Map<Long, String> AttrGroupEntityMap = null;
        if (AttrType){
            // 属性分组关联集合
            List<AttrAttrgroupRelationEntity> relationEntities = attrgroupRelationDao.selectList(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().in(AttrAttrgroupRelationEntity::getAttrId, attrIds));
            // 属性id与属性分组id集合
            relationEntitiesMap = relationEntities.stream().collect(Collectors.toMap(AttrAttrgroupRelationEntity::getAttrId, AttrAttrgroupRelationEntity::getAttrGroupId, (key1, key2) -> key1));

            // 属性分组id集合
            List<Long> attrGroupIds = relationEntities.stream().map(AttrAttrgroupRelationEntity::getAttrGroupId).collect(Collectors.toList());

            if (attrGroupIds != null && attrGroupIds.size() > 0){
                // 属性分组集合
                List<AttrGroupEntity> attrGroupEntities = attrGroupDao.selectBatchIds(attrGroupIds);
                // 属性分组id与属性分子name集合
                AttrGroupEntityMap = attrGroupEntities.stream().collect(Collectors.toMap(AttrGroupEntity::getAttrGroupId, AttrGroupEntity::getAttrGroupName, (key1, key2) -> key1));
            }

        }
        boolean finalAttrType = AttrType;
        Map<Long, String> finalAttrGroupEntityMap = AttrGroupEntityMap;
        Map<Long, Long> finalRelationEntitiesMap = relationEntitiesMap;
        List<AttrRespVo> attrRespVoList = records.stream().map(e -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtil.copyProperties(e, attrRespVo);
            attrRespVo.setCatelogName(categoryEntitiesMap.get(e.getCatelogId()));
            if (finalAttrType){
                if (finalRelationEntitiesMap.get(e.getAttrId()) != null){
                    attrRespVo.setGroupName(finalAttrGroupEntityMap.get(finalRelationEntitiesMap.get(e.getAttrId())));
                }
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attrRespVoList);

        return ResultBean.success(pageUtils);
    }

    @Transactional
    @Override
    public ResultBean saveAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtil.copyProperties(attr, attrEntity);
        baseMapper.insert(attrEntity);
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId() != null){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationEntity.setAttrGroupId(attr.getAttrGroupId());

            attrgroupRelationDao.insert(relationEntity);
        }

        return ResultBean.success("");
    }

    @Override
    public ResultBean getAttrInfo(Long attrId) {
        AttrEntity attrEntity = baseMapper.selectById(attrId);
        AttrRespVo respVo = new AttrRespVo();
        BeanUtil.copyProperties(attrEntity, respVo);

        Long catelogId = attrEntity.getCatelogId();
        // 查询分类路径
        Long[] catalogPath = categoryService.findCatalogPath(catelogId);
        respVo.setCatelogPath(catalogPath);
        // 查询分类名称
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if (categoryEntity != null) {
            respVo.setCatelogName(categoryEntity.getName());

        }
        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            // 查询分组关联属性
            AttrAttrgroupRelationEntity relationEntity = attrgroupRelationDao.selectOne(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrEntity.getAttrId()));
            if (relationEntity != null) {
                Long attrGroupId = relationEntity.getAttrGroupId();
                // 查询分组名称
                AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrGroupId);
                if (attrGroupEntity != null) {
                    respVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
                respVo.setAttrGroupId(relationEntity.getAttrGroupId());
            }
        }

        return ResultBean.success(respVo);
    }

    @Override
    public ResultBean updateAttr(AttrVo attr) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtil.copyProperties(attr, attrEntity);
        baseMapper.updateById(attrEntity);

        if (attrEntity.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode()){
            AttrAttrgroupRelationEntity entity = new AttrAttrgroupRelationEntity();
            entity.setAttrId(attrEntity.getAttrId());
            entity.setAttrGroupId(attr.getAttrGroupId());

            Long aLong = attrgroupRelationDao.selectCount(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attr.getAttrId()));

            if (aLong > 0) {
                attrgroupRelationDao.update(entity, new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attr.getAttrId()));
            } else {
                attrgroupRelationDao.insert(entity);
            }
        }

        return ResultBean.success("");
    }

    @Override
    public List<Long> selectSearchAttrs(List<Long> attrIds) {
        return this.baseMapper.selectSearchAttrIds(attrIds);
    }

}