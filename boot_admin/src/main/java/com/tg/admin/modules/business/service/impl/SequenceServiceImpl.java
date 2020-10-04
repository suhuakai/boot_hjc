package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.SequenceDao;
import com.tg.admin.modules.business.entity.SequenceEntity;
import com.tg.admin.modules.business.service.SequenceService;


@Service("sequenceService")
public class SequenceServiceImpl extends ServiceImpl<SequenceDao, SequenceEntity> implements SequenceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SequenceEntity> page = this.page(
                new Query<SequenceEntity>().getPage(params),
                new QueryWrapper<SequenceEntity>()
        );

        return new PageUtils(page);
    }

}
