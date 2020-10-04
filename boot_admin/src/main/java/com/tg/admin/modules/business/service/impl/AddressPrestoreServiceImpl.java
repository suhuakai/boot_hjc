package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.AddressPrestoreDao;
import com.tg.admin.modules.business.entity.AddressPrestoreEntity;
import com.tg.admin.modules.business.service.AddressPrestoreService;


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

}
