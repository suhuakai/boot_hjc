package com.tg.admin.modules.business.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 充值
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
@Data
@TableName("wallet_recharge")
public class WalletRechargeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 发送地址
	 */
	private String formAddress;
	/**
	 * 接受地址
	 */
	private String toAddress;
	/**
	 * 数量
	 */
	private BigDecimal number;
	/**
	 * hash值
	 */
	private String txid;
	/**
	 * 区块高度
	 */
	private Integer block;
	/**
	 * 区块下标
	 */
	private Integer blockIndex;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * token
	 */
	private String token;
	/**
	 * 创建时间
	 */
	private LocalDateTime createDate;
	/**
	 * 出块时间
	 */
	private LocalDateTime comeDate;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 
	 */
	private Integer coinId;
	/**
	 * 
	 */
	private Integer walletId;
	/**
	 * 0 确认中 1 成功 2 失败 3 删除 4 成功
	 */
	private Integer status;
	/**
	 * 代币名称
	 */
	private String coinName;
	/**
	 * 1 链上充值 2 内部充值
	 */
	private Integer type;
	/**
	 * 
	 */
	private Integer userId;
	/**
	 *  成功 未归集 手续费确认中  手续费支付失败 
usdt发送确认中 失败
	 */
	private String collectionType;
	/**
	 * 
	 */
	private String scanType;

}
