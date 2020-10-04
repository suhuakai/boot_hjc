package com.tg.admin.modules.business.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:01
 */
@Data
@TableName("user_vip_detail")
public class UserVipDetailEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 消耗数量
	 */
	private BigDecimal number;
	/**
	 * 消耗福豆
	 */
	private BigDecimal consumeBlessingBean;
	/**
	 * 消耗余额
	 */
	private BigDecimal consumeBalance;
	/**
	 * 创建时间
	 */
	private LocalDateTime date;
	/**
	 * 等级id
	 */
	private Integer vipId;
	/**
	 * 升级 购买
	 */
	private String type;
	/**
	 * 未结算，已结算
	 */
	private String settleStatus;
	/**
	 * 初始等级
	 */
	private Integer originalVpiId;

}
