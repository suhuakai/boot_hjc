package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.AdvertisingDao;
import com.tg.api.entity.AdvertisingEntity;
import com.tg.api.service.AdvertisingService;


@Service("advertisingService")
public class AdvertisingServiceImpl extends ServiceImpl<AdvertisingDao, AdvertisingEntity> implements AdvertisingService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdvertisingEntity> page = this.page(
                new Query<AdvertisingEntity>().getPage(params),
                new QueryWrapper<AdvertisingEntity>()
        );

        return new PageUtils(page);
    }

}
