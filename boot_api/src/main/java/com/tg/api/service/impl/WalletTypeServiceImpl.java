package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.WalletTypeDao;
import com.tg.api.entity.WalletTypeEntity;
import com.tg.api.service.WalletTypeService;


@Service("walletTypeService")
public class WalletTypeServiceImpl extends ServiceImpl<WalletTypeDao, WalletTypeEntity> implements WalletTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WalletTypeEntity> page = this.page(
                new Query<WalletTypeEntity>().getPage(params),
                new QueryWrapper<WalletTypeEntity>()
        );

        return new PageUtils(page);
    }

}
