package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.AnnountcementDao;
import com.tg.api.entity.AnnountcementEntity;
import com.tg.api.service.AnnountcementService;


@Service("annountcementService")
public class AnnountcementServiceImpl extends ServiceImpl<AnnountcementDao, AnnountcementEntity> implements AnnountcementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnnountcementEntity> page = this.page(
                new Query<AnnountcementEntity>().getPage(params),
                new QueryWrapper<AnnountcementEntity>().orderByDesc("id")
        );
        return new PageUtils(page);
    }

}
