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
@TableName("advertising")
public class AdvertisingEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 购买价格
	 */
	private BigDecimal number;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 总数量
	 */
	private Integer grossCount;
	/**
	 * 已购买数量
	 */
	private Integer buyCount;
	/**
	 * 视频url
	 */
	private String url;
	/**
	 * 开启 关闭
	 */
	private String status;

}
