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
@TableName("wallet")
public class WalletEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 余额
	 */
	private BigDecimal balance;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 代币id
	 */
	private Integer coinId;
	/**
	 * 代币名称
	 */
	private String coinName;
	/**
	 * 钱包类型
	 */
	private Integer walletTypeId;

}
