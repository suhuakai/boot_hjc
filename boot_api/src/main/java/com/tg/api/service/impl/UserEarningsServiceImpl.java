package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserEarningsDao;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.service.UserEarningsService;


@Service("userEarningsService")
public class UserEarningsServiceImpl extends ServiceImpl<UserEarningsDao, UserEarningsEntity> implements UserEarningsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEarningsEntity> page = this.page(
                new Query<UserEarningsEntity>().getPage(params),
                new QueryWrapper<UserEarningsEntity>()
        );

        return new PageUtils(page);
    }

}
