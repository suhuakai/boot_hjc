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
 * @date 2020-09-30 14:32:04
 */
@Data
@TableName("annountcement")
public class AnnountcementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private LocalDateTime date;
	/**
	 * 标题
	 */
	private String topic;
	/**
	 * 内容
	 */
	private String contant;

}
