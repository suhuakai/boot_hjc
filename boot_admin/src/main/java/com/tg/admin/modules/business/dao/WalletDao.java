package com.tg.admin.modules.business.dao;

import com.tg.admin.modules.business.entity.WalletEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:01
 */
@Mapper
public interface WalletDao extends BaseMapper<WalletEntity> {

    @Select("SELECT * from wallet where id = #{id} for UPDATE")
    WalletEntity getLock(Integer id);

    @Select("update  wallet set balance = balance + #{balance} where id = #{id}")
    void increaseWalletBalance(WalletEntity walletEntity);
}
