/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.mbrex.vip
 *
 * 版权所有，侵权必究！
 */

package com.tg.admin.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 角色与部门对应关系
 *
 * @author Mark sunlightcs@gmail.com
 */
@Data
@TableName("sys_role_dept")
public class SysRoleDeptEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@TableId
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 部门ID
	 */
	private Long deptId;

	
}