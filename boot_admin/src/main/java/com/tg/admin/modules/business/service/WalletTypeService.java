package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.WalletTypeEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
public interface WalletTypeService extends IService<WalletTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

