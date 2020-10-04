package com.tg.admin.modules.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.modules.business.entity.WalletMentionEntity;

import java.util.Map;

/**
 * 提币
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:01
 */
public interface WalletMentionService extends IService<WalletMentionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

