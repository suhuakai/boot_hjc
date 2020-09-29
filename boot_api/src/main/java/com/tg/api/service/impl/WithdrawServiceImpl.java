package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.WithdrawDao;
import com.tg.api.entity.WithdrawEntity;
import com.tg.api.service.WithdrawService;


@Service("withdrawService")
public class WithdrawServiceImpl extends ServiceImpl<WithdrawDao, WithdrawEntity> implements WithdrawService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WithdrawEntity> page = this.page(
                new Query<WithdrawEntity>().getPage(params),
                new QueryWrapper<WithdrawEntity>()
        );

        return new PageUtils(page);
    }

}
