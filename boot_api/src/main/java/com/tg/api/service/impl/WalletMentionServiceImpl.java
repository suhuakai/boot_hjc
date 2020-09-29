package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.WalletMentionDao;
import com.tg.api.entity.WalletMentionEntity;
import com.tg.api.service.WalletMentionService;


@Service("walletMentionService")
public class WalletMentionServiceImpl extends ServiceImpl<WalletMentionDao, WalletMentionEntity> implements WalletMentionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WalletMentionEntity> page = this.page(
                new Query<WalletMentionEntity>().getPage(params),
                new QueryWrapper<WalletMentionEntity>()
        );

        return new PageUtils(page);
    }

}
