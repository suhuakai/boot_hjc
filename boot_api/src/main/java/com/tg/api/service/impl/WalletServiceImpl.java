package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.WalletDao;
import com.tg.api.entity.WalletEntity;
import com.tg.api.service.WalletService;


@Service("walletService")
public class WalletServiceImpl extends ServiceImpl<WalletDao, WalletEntity> implements WalletService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WalletEntity> page = this.page(
                new Query<WalletEntity>().getPage(params),
                new QueryWrapper<WalletEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public WalletEntity selectByUserId(Integer upUserId, Integer type) {
        return baseMapper.selectByUserId(upUserId,type);
    }

}
