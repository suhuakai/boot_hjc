package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.entity.AdvertisingEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-08 17:46:13
 */
public interface AdvertisingService extends IService<AdvertisingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

