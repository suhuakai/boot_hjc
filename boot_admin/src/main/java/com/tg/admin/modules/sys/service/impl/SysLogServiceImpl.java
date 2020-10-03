/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.mbrex.vip
 *
 * 版权所有，侵权必究！
 */

package com.tg.admin.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;
import com.tg.admin.modules.sys.dao.SysLogDao;
import com.tg.admin.modules.sys.entity.SysLogEntity;
import com.tg.admin.modules.sys.service.SysLogService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");
        String startTime = (String)params.get("startTime");
        String endTime = (String)params.get("endTime");
        Query<SysLogEntity> query =new Query<SysLogEntity>();
        QueryWrapper<SysLogEntity> queryWrapper = new QueryWrapper<SysLogEntity>();
        if(startTime!=null&&endTime!=null&& StringUtils.isNotBlank(startTime)&&StringUtils.isNotBlank(endTime)) {
            queryWrapper.between("create_date",startTime.replaceAll("T"," ")+":00", endTime.replaceAll("T"," ")+":00");
        }
        IPage<SysLogEntity> page = this.page(
            new Query<SysLogEntity>().getPage(params),
            queryWrapper.like(StringUtils.isNotBlank(key),"username", key).orderByDesc("create_date")
        );

        return new PageUtils(page);
    }
}
