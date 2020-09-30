package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.entity.AnnountcementEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-30 14:32:04
 */
public interface AnnountcementService extends IService<AnnountcementEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

