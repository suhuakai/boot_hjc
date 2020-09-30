package com.tg.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDateTime;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
@Data
@TableName("withdraw")
public class WithdrawEntity implements Serializable {
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
	 * 创建/审核中/成功/失败
	 */
	private String status;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 驳回说明
	 */
	private String reject;
	/**
	 * 创建时间
	 */
	private LocalDateTime date;
	/**
	 * 审核时间
	 */
	private LocalDateTime accomplishDate;
	/**
	 *  提现 充值
	 */
	private String type;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * 订单号
	 */
	private long randomCode;
	/**
	 * 图片路径
	 */
	private String url;
	/**
	 * 实际充值金额
	 */
	private Integer realityNumber;
	/**
	 * 银行卡 支付宝
	 */
	private String useType;
	/**
	 * 卡号
	 */
	private String account;
	/**
	 * 账号名称
	 */
	private String accountName;
	/**
	 * 银行类型
	 */
	private String banksType;
	/**
	 * 子行
	 */
	private String banksSonType;

}
