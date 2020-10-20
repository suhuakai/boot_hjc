package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.AdvertisingEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-11 14:45:40
 */
public interface AdvertisingService extends IService<AdvertisingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

