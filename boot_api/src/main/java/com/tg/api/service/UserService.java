package com.tg.api.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.controller.UserController;
import com.tg.api.entity.UserEntity;
import com.tg.api.vo.UserVo;

import java.util.Map;

/**
 * LID 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    UserEntity findUserInfoByParameter(String id, String password);

    UserVo Register(UserVo userVo);

    UserVo rememberMe(UserVo userVo);

    void safeSet(UserController.Params params);
}

