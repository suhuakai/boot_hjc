package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.BalanceEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:02
 */
public interface BalanceService extends IService<BalanceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

