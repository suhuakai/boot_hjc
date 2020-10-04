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
@TableName("vip_grade_type")
public class VipGradeTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 等级名称
	 */
	private String name;
	/**
	 * 一代推荐奖励
	 */
	private BigDecimal recommendOneRate;
	/**
	 * 签到奖励
	 */
	private BigDecimal sign;
	/**
	 * 价值
	 */
	private BigDecimal worth;
	/**
	 * 消耗福豆
	 */
	private BigDecimal consumeBlessingBean;
	/**
	 * 消耗余额
	 */
	private BigDecimal consumeBalance;
	/**
	 * 二代推荐奖励
	 */
	private BigDecimal recommendTwoRate;
	/**
	 * 签到次数
	 */
	private Integer signDay;

}
