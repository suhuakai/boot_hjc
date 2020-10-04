package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.AnnountcementDao;
import com.tg.admin.modules.business.entity.AnnountcementEntity;
import com.tg.admin.modules.business.service.AnnountcementService;


@Service("annountcementService")
public class AnnountcementServiceImpl extends ServiceImpl<AnnountcementDao, AnnountcementEntity> implements AnnountcementService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AnnountcementEntity> page = this.page(
                new Query<AnnountcementEntity>().getPage(params),
                new QueryWrapper<AnnountcementEntity>()
        );

        return new PageUtils(page);
    }

}
