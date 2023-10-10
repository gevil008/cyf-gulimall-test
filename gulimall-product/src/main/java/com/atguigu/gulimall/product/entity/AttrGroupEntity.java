package com.atguigu.gulimall.product.entity;

import com.atguigu.common.valid.AddGroup;
import com.atguigu.common.valid.UpdateGroup;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 属性分组
 * 
 * @author cyf
 * @email gin258@163.com
 * @date 2022-09-10 17:38:26
 */
@Data
@TableName("pms_attr_group")
public class AttrGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 分组id
	 */
	@TableId
	@NotNull(message = "分组id不能为空", groups = UpdateGroup.class)
	private Long attrGroupId;
	/**
	 * 组名
	 */
	@NotBlank(message = "组名不能为空", groups = {AddGroup.class,UpdateGroup.class})
	private String attrGroupName;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空", groups = {AddGroup.class,UpdateGroup.class})
	private Integer sort;
	/**
	 * 描述
	 */
	@NotBlank(message = "描述不能为空", groups = {AddGroup.class,UpdateGroup.class})
	private String descript;
	/**
	 * 组图标
	 */
	@NotBlank(message = "组图标不能为空", groups = {AddGroup.class,UpdateGroup.class})
	private String icon;
	/**
	 * 所属分类id
	 */
	@NotNull(message = "所属分类id不能为空", groups = {AddGroup.class,UpdateGroup.class})
	private Long catelogId;

}
