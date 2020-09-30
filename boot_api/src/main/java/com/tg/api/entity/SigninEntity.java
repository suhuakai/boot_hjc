package com.tg.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("signIn")
public class SigninEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 签到主id
	 */
	@TableId
	private Integer signId;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 签到日期
	 */
	private LocalDateTime signDate;
	/**
	 * 签到金额
	 */
	private BigDecimal signBalance;
	/**
	 * 签到类型   1 签到   2 关注  3 浏览
	 */
	private Integer signType;
	/**
	 * 钱包类型 id
	 */
	private Integer walletTypeId;

	@TableField(exist = false)
	private String signTypeName;

}
