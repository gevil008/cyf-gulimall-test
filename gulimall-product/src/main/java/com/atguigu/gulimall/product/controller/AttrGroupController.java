package com.atguigu.gulimall.product.controller;

import com.atguigu.common.utils.ResultBean;
import com.atguigu.common.valid.AddGroup;
import com.atguigu.common.valid.UpdateGroup;
import com.atguigu.gulimall.product.entity.AttrEntity;
import com.atguigu.gulimall.product.entity.AttrGroupEntity;
import com.atguigu.gulimall.product.service.AttrAttrgroupRelationService;
import com.atguigu.gulimall.product.service.AttrGroupService;
import com.atguigu.gulimall.product.vo.AttrGroupRelationVo;
import com.atguigu.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 属性分组
 *
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    AttrAttrgroupRelationService relationService;

    /**
     * 列表
     */
    @PostMapping("/list")
    //@RequiresPermissions("product:attrgroup:list")
    public ResultBean list(@RequestBody Map<String, Object> params) {
        return attrGroupService.queryPage(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public ResultBean info(@PathVariable("attrGroupId") Long attrGroupId) {
        AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
        return ResultBean.success(attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public ResultBean save(@Validated(value = {AddGroup.class}) @RequestBody AttrGroupEntity attrGroup) {
        boolean b = attrGroupService.save(attrGroup);
        if (b) {
            return ResultBean.success("");
        } else {
            return ResultBean.fail("");
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public ResultBean update(@Validated(value = {UpdateGroup.class}) @RequestBody AttrGroupEntity attrGroup) {
        boolean b = attrGroupService.updateById(attrGroup);
        if (b) {
            return ResultBean.success("");
        } else {
            return ResultBean.fail("");
        }
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public ResultBean delete(@RequestBody Long[] attrGroupIds) {
        boolean b = attrGroupService.removeByIds(Arrays.asList(attrGroupIds));
        if (b) {
            return ResultBean.success("");
        } else {
            return ResultBean.fail("");
        }
    }

    /**
     * 根据分组id查询关联的所有属性
     *
     * @param attrgroupId
     * @return
     */
    @GetMapping("/attr/relation/{attrgroupId}")
    public ResultBean attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> attrEntities = attrGroupService.attrRelation(attrgroupId);
        return ResultBean.success(attrEntities);

    }

    @PostMapping("/attr/relation/delete")
    public ResultBean attrRelationDelete(@RequestBody List<AttrGroupRelationVo> params) {
        return attrGroupService.attrRelationDelete(params);
    }

    @PostMapping("/noattr/relation")
    public ResultBean noAttrRelation(@RequestBody Map params) {
        return attrGroupService.noAttrRelation(params);
    }

    @PostMapping("/attr/relation")
    public ResultBean attrRelation(@RequestBody List<AttrGroupRelationVo> params) {
        return relationService.attrRelation(params);
    }

    /**
     * 获取分类下所有分组&关联属性
     *
     * @param catelogId
     * @return
     */
    @GetMapping("/withattr/{catelogId}")
    public ResultBean getAttrGroupWithAttrs(@PathVariable("catelogId") Long catelogId) {
        // 1、查询当前分类下的所属分组
        // 2、查询每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> withAttrsVoList = attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return ResultBean.success(withAttrsVoList);
    }


}
