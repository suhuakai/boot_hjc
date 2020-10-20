package com.tg.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-08-24 20:50:22
 */
@Data
@TableName("banks_user")
public class BanksUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
		 * 银行类型
	 */
	private String banksType;
	/**
	 * 子行
	 */
	private String banksSonType;
	/**
	 * 卡号
	 */
	private String cardNumber;
	/**
	 * 
	 */
	private String status;
	/**
	 * 
	 */
	private LocalDateTime date;
	/**
	 * 
	 */
	private Integer userId;

}
