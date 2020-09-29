package com.tg.api.dao;

import com.tg.api.entity.SequenceEntity;
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
public interface SequenceDao extends BaseMapper<SequenceEntity> {

    @Select("select currval(#{name})")
    Integer currval(String name);

    @Select("select nextval(#{name})")
    Integer nextval(String name);

}
