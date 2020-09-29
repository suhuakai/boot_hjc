package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.CoinDao;
import com.tg.api.entity.CoinEntity;
import com.tg.api.service.CoinService;


@Service("coinService")
public class CoinServiceImpl extends ServiceImpl<CoinDao, CoinEntity> implements CoinService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CoinEntity> page = this.page(
                new Query<CoinEntity>().getPage(params),
                new QueryWrapper<CoinEntity>()
        );

        return new PageUtils(page);
    }

}
