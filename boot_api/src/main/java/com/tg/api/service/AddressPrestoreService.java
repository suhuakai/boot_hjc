package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.entity.AddressPrestoreEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
public interface AddressPrestoreService extends IService<AddressPrestoreEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

