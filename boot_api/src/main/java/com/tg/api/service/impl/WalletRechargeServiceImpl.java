package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.WalletRechargeDao;
import com.tg.api.entity.WalletRechargeEntity;
import com.tg.api.service.WalletRechargeService;


@Service("walletRechargeService")
public class WalletRechargeServiceImpl extends ServiceImpl<WalletRechargeDao, WalletRechargeEntity> implements WalletRechargeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WalletRechargeEntity> page = this.page(
                new Query<WalletRechargeEntity>().getPage(params),
                new QueryWrapper<WalletRechargeEntity>()
        );

        return new PageUtils(page);
    }

}
