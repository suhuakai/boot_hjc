package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.entity.DictionariesEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-08-06 20:02:59
 */
public interface DictionariesService extends IService<DictionariesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

