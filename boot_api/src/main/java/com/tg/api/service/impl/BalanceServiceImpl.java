package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.BalanceDao;
import com.tg.api.entity.BalanceEntity;
import com.tg.api.service.BalanceService;


@Service("balanceService")
public class BalanceServiceImpl extends ServiceImpl<BalanceDao, BalanceEntity> implements BalanceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BalanceEntity> page = this.page(
                new Query<BalanceEntity>().getPage(params),
                new QueryWrapper<BalanceEntity>()
        );

        return new PageUtils(page);
    }

}
