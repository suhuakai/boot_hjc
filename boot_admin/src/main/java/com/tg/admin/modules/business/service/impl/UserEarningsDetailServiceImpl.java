package com.tg.admin.modules.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;
import com.tg.admin.modules.business.dao.UserEarningsDetailDao;
import com.tg.admin.modules.business.entity.UserEarningsDetailEntity;
import com.tg.admin.modules.business.service.UserEarningsDetailService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("userEarningsDetailService")
public class UserEarningsDetailServiceImpl extends ServiceImpl<UserEarningsDetailDao, UserEarningsDetailEntity> implements UserEarningsDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserEarningsDetailEntity> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("userId") && !"".equals(params.get("userId"))) {
            queryWrapper.eq("user_id", params.get("userId"));
        }

        IPage<UserEarningsDetailEntity> page = this.page(
                new Query<UserEarningsDetailEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
