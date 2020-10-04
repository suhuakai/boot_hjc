package com.tg.admin.modules.business.service.impl;

import com.tg.admin.modules.business.entity.UserEntity;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.WalletDao;
import com.tg.admin.modules.business.entity.WalletEntity;
import com.tg.admin.modules.business.service.WalletService;


@Service("walletService")
public class WalletServiceImpl extends ServiceImpl<WalletDao, WalletEntity> implements WalletService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WalletEntity> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("walletTypeId") && !"".equals(params.get("walletTypeId"))) {
            queryWrapper.eq("wallet_type_id", params.get("walletTypeId"));
        }
        IPage<WalletEntity> page = this.page(
                new Query<WalletEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
