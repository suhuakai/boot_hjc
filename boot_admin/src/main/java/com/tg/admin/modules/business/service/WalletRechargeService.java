package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.WalletRechargeEntity;

import java.util.Map;

/**
 * 充值
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
public interface WalletRechargeService extends IService<WalletRechargeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

