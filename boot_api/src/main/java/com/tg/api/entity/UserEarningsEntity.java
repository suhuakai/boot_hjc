package com.tg.api.entity;

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
 * @date 2020-09-29 13:21:15
 */
@Data
@TableName("user_earnings")
public class UserEarningsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 数量
	 */
	private BigDecimal number;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 创建时间
	 */
	private LocalDateTime date;
	/**
	 * 钱包类型
	 */
	private Integer walletTypeId;
	/**
	 * 注册  推荐收益
	 */
	private String type;
	/**
	 * 未结算，已结算
	 */
	private String settleStatus;

	private Integer upUserId;

}
