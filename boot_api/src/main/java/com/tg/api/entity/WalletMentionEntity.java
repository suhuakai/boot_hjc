package com.tg.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 提币
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@Data
@TableName("wallet_mention")
public class WalletMentionEntity implements Serializable {
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
	 * 接收地址
	 */
	private String toAddress;
	/**
	 * 数量
	 */
	private BigDecimal number;
	/**
	 * 交易hash
	 */
	private String txid;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * 0 确认中 1 成功 2 失败 3 删除 4 成功
	 */
	private Integer status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 钱包id
	 */
	private Integer walletId;
	/**
	 * 
	 */
	private Integer coinId;
	/**
	 * 创建时间
	 */
	private LocalDateTime createDate;
	/**
	 * 处理时间
	 */
	private LocalDateTime disposeDate;
	/**
	 * 区块高度
	 */
	private Integer block;
	/**
	 * 下标
	 */
	private Integer blockIndex;
	/**
	 * 代币名称
	 */
	private String coinName;
	/**
	 * 1 链上转账 2 内部转账
	 */
	private Integer type;
	/**
	 * 
	 */
	private Integer userId;
	/**
	 * 
	 */
	private String scanType;

}
