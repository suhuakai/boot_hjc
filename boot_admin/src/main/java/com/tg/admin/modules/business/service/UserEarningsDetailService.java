package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.UserEarningsDetailEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-30 17:55:44
 */
public interface UserEarningsDetailService extends IService<UserEarningsDetailEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

