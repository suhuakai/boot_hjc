package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.entity.UserEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
public interface UserEarningsService extends IService<UserEarningsEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveEarnginx(UserEarningsEntity userEarningsEntity);
}

