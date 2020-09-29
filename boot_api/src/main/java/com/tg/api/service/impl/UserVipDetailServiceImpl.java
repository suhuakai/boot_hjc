package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserVipDetailDao;
import com.tg.api.entity.UserVipDetailEntity;
import com.tg.api.service.UserVipDetailService;


@Service("userVipDetailService")
public class UserVipDetailServiceImpl extends ServiceImpl<UserVipDetailDao, UserVipDetailEntity> implements UserVipDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserVipDetailEntity> page = this.page(
                new Query<UserVipDetailEntity>().getPage(params),
                new QueryWrapper<UserVipDetailEntity>()
        );

        return new PageUtils(page);
    }

}
