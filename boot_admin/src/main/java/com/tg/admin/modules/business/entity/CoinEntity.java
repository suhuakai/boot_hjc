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
@TableName("coin")
public class CoinEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String name;
	/**
	 * 
	 */
	private String status;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * 
	 */
	private Integer sortIndex;
	/**
	 * 
	 */
	private BigDecimal interiorFee;
	/**
	 * 是否划转
	 */
	private String isTrans;
	/**
	 * 划转手续费
	 */
	private BigDecimal transFee;
	/**
	 * 钱包类型
	 */
	private Integer walletTypeId;

}
