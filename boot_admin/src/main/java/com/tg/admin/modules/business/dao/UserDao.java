package com.tg.admin.modules.business.dao;

import com.tg.admin.modules.business.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * LID 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
