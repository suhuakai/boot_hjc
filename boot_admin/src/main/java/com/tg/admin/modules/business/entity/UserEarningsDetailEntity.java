package com.tg.admin.modules.business.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-30 17:55:44
 */
@Data
@TableName("user_earnings_detail")
public class UserEarningsDetailEntity implements Serializable {
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
	 * 变化值显示值
	 */
	private String numberZifu;
	/**
	 * 来源id
	 */
	private Integer orderId;
	/**
	 * 钱包类型
	 */
	private Integer walletTypeId;
	/**
	 * 说明
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private LocalDateTime date;
	/**
	 * 钱包当前余额
	 */
	private BigDecimal balance;
	/**
	 * 变化值计算值
	 */
	private BigDecimal number;
	/**
	 * 操作 动态收益   静态收益
	 */
	private String type;
	/**
	 * 加权利率
	 */
	private BigDecimal weightingRate;
	/**
	 * 来源用户id
	 */
	private Integer upUserId;
	/**
	 * 推荐收益 签到收益 无
	 */
	private String earningsType;
	/**
	 * 阶梯
	 */
	private Integer grade;
	/**
	 * 未结算，已结算
	 */
	private String settleStatus;
	/**
	 * 分流利率
	 */
	private BigDecimal shuntRate;

}
