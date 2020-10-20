package com.tg.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-08-06 20:02:59
 */
@Data
@TableName("dictionaries")
public class DictionariesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 激活下级费用
	 */
	private BigDecimal agencyNumberFee;
	/**
	 * 推非代理奖励
	 */
	private BigDecimal oneAwardRate;

	private String imgUrl;

	/**
	 * 申请成代理人奖励
	 */
	private BigDecimal agencyNumberAward;

	private Integer period;

	private BigDecimal oneAgency;


	private BigDecimal twoAgency;

	private BigDecimal twoFreeze;

	private String adversionType;

}
