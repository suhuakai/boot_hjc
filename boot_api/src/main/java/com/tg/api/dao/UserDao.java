package com.tg.api.dao;

import com.tg.api.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * LID 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    @Select("select * from user where id =#{id} and password =#{password}")
    UserEntity findUserInfoByParameter(@Param("id") String id, @Param("password") String password);

    @Select("select * from user where mnemonic =#{mnemonic}")
    UserEntity selectByMnemonic(String mnemonic);
}
