package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.VipGradeTypeDao;
import com.tg.api.entity.VipGradeTypeEntity;
import com.tg.api.service.VipGradeTypeService;


@Service("vipGradeTypeService")
public class VipGradeTypeServiceImpl extends ServiceImpl<VipGradeTypeDao, VipGradeTypeEntity> implements VipGradeTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VipGradeTypeEntity> page = this.page(
                new Query<VipGradeTypeEntity>().getPage(params),
                new QueryWrapper<VipGradeTypeEntity>()
        );

        return new PageUtils(page);
    }

}
