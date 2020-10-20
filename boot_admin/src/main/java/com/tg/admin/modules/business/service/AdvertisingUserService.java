package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.AdvertisingUserEntity;

import java.util.Map;

/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-11 14:45:40
 */
public interface AdvertisingUserService extends IService<AdvertisingUserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    boolean audit(Integer id, Integer type);

    boolean accelerate(Integer id, Integer type);
}

