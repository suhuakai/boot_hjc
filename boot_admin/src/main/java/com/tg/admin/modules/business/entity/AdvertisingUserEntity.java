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
 * @date 2020-10-11 14:45:40
 */
@Data
@TableName("advertising_user")
public class AdvertisingUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 消耗金卷
	 */
	private BigDecimal number;
	/**
	 * 预约id
	 */
	private Integer advertistingId;
	/**
	 * 预约的标题
	 */
	private String advertistingName;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 购买钱包类型
	 */
	private Integer walletTypeId;
	/**
	 * 预约时间
	 */
	private LocalDateTime dateSubscribe;
	/**
	 * 预约中，已观看，进行中，本金已退，完成
	 */
	private String status;
	/**
	 * 加入时间
	 */
	private LocalDateTime dateUnderway;
	/**
	 * 本金退出时间
	 */
	private LocalDateTime datePrincipalrefunded;
	/**
	 * 完成时间
	 */
	private LocalDateTime dateAccomplish;
	/**
	 * 观看时间
	 */
	private LocalDateTime dateYiWatch;
	/**
	 * 金卷价格
	 */
	private BigDecimal price;

}
