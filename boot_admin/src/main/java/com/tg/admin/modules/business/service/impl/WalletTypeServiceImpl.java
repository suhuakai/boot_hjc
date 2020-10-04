package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.WalletTypeDao;
import com.tg.admin.modules.business.entity.WalletTypeEntity;
import com.tg.admin.modules.business.service.WalletTypeService;


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
