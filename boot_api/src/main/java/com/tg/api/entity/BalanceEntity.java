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
@TableName("balance")
public class BalanceEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 全局总金额id
	 */
	@TableId
	private Integer balanceId;
	/**
	 * 总金额
	 */
	private BigDecimal balanceMoney;
	/**
	 * 钱包类型
	 */
	private Integer walletTypeId;

}
