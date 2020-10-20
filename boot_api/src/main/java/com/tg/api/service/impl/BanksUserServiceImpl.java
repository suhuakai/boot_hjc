package com.tg.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.constant.MineParameter;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;
import com.tg.api.dao.BanksUserDao;
import com.tg.api.entity.BanksUserEntity;
import com.tg.api.service.BanksUserService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("banksUserService")
public class BanksUserServiceImpl extends ServiceImpl<BanksUserDao, BanksUserEntity> implements BanksUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BanksUserEntity> page = this.page(
                new Query<BanksUserEntity>().getPage(params),
                new QueryWrapper<BanksUserEntity>().eq(MineParameter.status,"yes").eq(MineParameter.user_id,params.get("userId")).orderByDesc(MineParameter.DATE)
        );

        return new PageUtils(page);
    }

}
