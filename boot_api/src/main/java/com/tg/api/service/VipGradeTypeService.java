package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.entity.VipGradeTypeEntity;

import java.util.Map;

/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
public interface VipGradeTypeService extends IService<VipGradeTypeEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

