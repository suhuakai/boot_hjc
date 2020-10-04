package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.WithdrawDao;
import com.tg.admin.modules.business.entity.WithdrawEntity;
import com.tg.admin.modules.business.service.WithdrawService;


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
