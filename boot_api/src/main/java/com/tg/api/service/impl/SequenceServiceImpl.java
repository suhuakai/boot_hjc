package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.SequenceDao;
import com.tg.api.entity.SequenceEntity;
import com.tg.api.service.SequenceService;


@Service("sequenceService")
public class SequenceServiceImpl extends ServiceImpl<SequenceDao, SequenceEntity> implements SequenceService {

    @Override
    public Integer nextval(String name) {
        return baseMapper.nextval(name);
    }

    @Override
    public Integer currval(String name) {
        return baseMapper.currval(name);
    }
}
