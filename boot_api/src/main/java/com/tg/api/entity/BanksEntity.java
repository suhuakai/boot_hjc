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
 * @date 2020-09-29 13:21:15
 */
@Data
@TableName("banks")
public class BanksEntity implements Serializable {
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

}
