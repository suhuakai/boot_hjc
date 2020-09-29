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
 * @date 2020-09-27 20:25:05
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
	 * 
	 */
	private String status;

}
