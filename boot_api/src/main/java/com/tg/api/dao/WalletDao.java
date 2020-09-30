package com.tg.api.dao;

import com.tg.api.entity.WalletEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tg.api.vo.WalletEntityVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
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

    @Select("select id,balance,address,user_id,coin_id,coin_name,wallet_type_id ," +
            "case when wallet_type_id =1 then '余额'" +
            "when wallet_type_id =2 then '平台币'" +
            "when wallet_type_id =3 then '银矿池'" +
            "when wallet_type_id =4 then '金矿池'" +
            "when wallet_type_id =5 then '贡献池'" +
            "when wallet_type_id =6 then '期股权'" +
            "else '' end walletTypeName" +
            " from wallet where user_id =#{userId}")
    List<WalletEntityVo> getByUserId(Integer userId);
}
