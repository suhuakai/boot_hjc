package com.tg.admin.modules.business.service.impl;

import com.tg.admin.modules.business.entity.WalletEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.AdvertisingUserDao;
import com.tg.admin.modules.business.entity.AdvertisingUserEntity;
import com.tg.admin.modules.business.service.AdvertisingUserService;


@Service("advertisingUserService")
public class AdvertisingUserServiceImpl extends ServiceImpl<AdvertisingUserDao, AdvertisingUserEntity> implements AdvertisingUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AdvertisingUserEntity> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("userId") && !"".equals(params.get("userId"))) {
            queryWrapper.eq("user_id", params.get("userId"));
        }
        if (params.containsKey("status") && !"-1".equals(params.get("status"))) {
            queryWrapper.eq("status", params.get("status"));
        }
        IPage<AdvertisingUserEntity> page = this.page(
                new Query<AdvertisingUserEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    public boolean accelerate(Integer id, Integer type) {
        AdvertisingUserEntity adver = this.getById(id);
        if (adver.getStatus().equals("underway")) {
            adver.setDateUnderway(adver.getDateUnderway().minusMinutes(60 * 24 * 5));
            this.updateById(adver);
        }
        return true;
    }

    @Override
    public boolean audit(Integer id, Integer type) {
        AdvertisingUserEntity adver = this.getById(id);
        if (adver.getStatus().equals("yiWatch")) {
            adver.setDateUnderway(LocalDateTime.now());
            adver.setStatus("underway");
            this.updateById(adver);
        }
        return true;
    }
}
