package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.UserEarningsEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
public interface UserEarningsService extends IService<UserEarningsEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

