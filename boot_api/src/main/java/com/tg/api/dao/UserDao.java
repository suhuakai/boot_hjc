package com.tg.api.dao;

import com.tg.api.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 * LID 
 *
 * @date 2020-09-27 20:25:05
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    @Select("select * from user where id =#{id} and password =#{password}")
    UserEntity findUserInfoByParameter(@Param("id") String id, @Param("password") String password);

    @Select("select * from user where mnemonic =#{mnemonic}")
    UserEntity selectByMnemonic(String mnemonic);

    @Select("select id,name ,up_user_id upUserId from user where  user_id in <foreach collection='map.upUserId' item='id' open='(' separator=',' close=')'> #{id}</foreach>")
    Page<Map<String, Object>> queryList(@Param("map") Map<String, Object> map);
}
