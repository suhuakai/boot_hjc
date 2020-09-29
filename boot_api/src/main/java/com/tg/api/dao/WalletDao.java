package com.tg.api.dao;

import com.tg.api.entity.WalletEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@Mapper
public interface WalletDao extends BaseMapper<WalletEntity> {

    @Select("select * from wallet  where user_id =#{userId} and wallet_type_id =#{type}")
    WalletEntity selectByUserId(@Param("userId") Integer userId, @Param("type") Integer type);

    @Select("SELECT * from wallet where id = #{id} for UPDATE")
    WalletEntity getLock(Integer id);

    @Select("update  wallet set balance = balance - #{balance} where id = #{id}")
    void reduceWalletBalance(WalletEntity walletEntity);

    @Select("update  wallet set balance = balance + #{balance} where id = #{id}")
    void increaseWalletBalance(WalletEntity walletEntity);
}
