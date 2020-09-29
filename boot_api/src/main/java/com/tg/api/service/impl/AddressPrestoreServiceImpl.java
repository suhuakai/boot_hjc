package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.AddressPrestoreDao;
import com.tg.api.entity.AddressPrestoreEntity;
import com.tg.api.service.AddressPrestoreService;


@Service("addressPrestoreService")
public class AddressPrestoreServiceImpl extends ServiceImpl<AddressPrestoreDao, AddressPrestoreEntity> implements AddressPrestoreService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AddressPrestoreEntity> page = this.page(
                new Query<AddressPrestoreEntity>().getPage(params),
                new QueryWrapper<AddressPrestoreEntity>()
        );

        return new PageUtils(page);
    }



    @Override
    public AddressPrestoreEntity getAddressPrestore(Integer coinId) {
        return baseMapper.getAddressPrestore(coinId);
    }
}
