package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.AdvertisingDao;
import com.tg.admin.modules.business.entity.AdvertisingEntity;
import com.tg.admin.modules.business.service.AdvertisingService;


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
