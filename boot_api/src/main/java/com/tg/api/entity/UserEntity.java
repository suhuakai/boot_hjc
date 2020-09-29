package com.tg.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * LID 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 助记词
	 */
	private String mnemonic;
	/**
	 * 私钥
	 */
	private String privateKey;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 昵称
	 */
	private String name;
	/**
	 * 正常/禁用 yes/no
	 */
	private String status;
	/**
	 * 
	 */
	private LocalDateTime date;
	/**
	 * 设备号
	 */
	private String equipmentNumber;
	/**
	 * 交易密码
	 */
	private String dealPassword;
	/**
	 * 上级用户编号
	 */
	private Integer upUserId;
	/**
	 * 阶梯
	 */
	private Integer grade;
	/**
	 * 阶梯路径
	 */
	private String gradeUrl;
	/**
	 * 直推人数
	 */
	private Integer recommendCount;
	/**
	 * 激活码
	 */
	private String identityCard;

}
