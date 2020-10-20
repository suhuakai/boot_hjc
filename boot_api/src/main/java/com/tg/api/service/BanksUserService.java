package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.entity.BanksUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-08-24 20:50:22
 */
public interface BanksUserService extends IService<BanksUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

