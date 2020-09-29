package com.tg.api.dao;

import com.tg.api.entity.AddressPrestoreEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 
 * 
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@Mapper
public interface AddressPrestoreDao extends BaseMapper<AddressPrestoreEntity> {



    @Select("SELECT * from address_prestore where coin_id = #{coinId} AND status = 'yes' limit 1")
    AddressPrestoreEntity getAddressPrestore(Integer coinId);
}
