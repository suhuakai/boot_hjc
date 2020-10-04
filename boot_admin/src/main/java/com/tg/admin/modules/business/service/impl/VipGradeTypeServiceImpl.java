package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.VipGradeTypeDao;
import com.tg.admin.modules.business.entity.VipGradeTypeEntity;
import com.tg.admin.modules.business.service.VipGradeTypeService;


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
