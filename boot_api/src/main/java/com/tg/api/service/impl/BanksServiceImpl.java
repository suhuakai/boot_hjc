package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.BanksDao;
import com.tg.api.entity.BanksEntity;
import com.tg.api.service.BanksService;


@Service("banksService")
public class BanksServiceImpl extends ServiceImpl<BanksDao, BanksEntity> implements BanksService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BanksEntity> page = this.page(
                new Query<BanksEntity>().getPage(params),
                new QueryWrapper<BanksEntity>()
        );

        return new PageUtils(page);
    }

}
