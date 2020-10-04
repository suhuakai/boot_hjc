package com.tg.admin.modules.business.entity;

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
 * @date 2020-10-03 20:12:00
 */
@Data
@TableName("address_prestore")
public class AddressPrestoreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private String address;
	/**
	 * 
	 */
	private Integer coinId;
	/**
	 * 
	 */
	private String mnemonic;
	/**
	 * 
	 */
	private String privateKey;
	/**
	 * 
	 */
	private Integer userId;
	/**
	 * 
	 */
	private LocalDateTime createDate;
	/**
	 *  yes 可用  no 不可用
	 */
	private String status;

}
