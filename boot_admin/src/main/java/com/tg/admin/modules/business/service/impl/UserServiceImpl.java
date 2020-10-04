package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.UserDao;
import com.tg.admin.modules.business.entity.UserEntity;
import com.tg.admin.modules.business.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("id") && !"".equals(params.get("id"))) {
            queryWrapper.eq("id", params.get("id"));
        }
        if (params.containsKey("name") && !"".equals(params.get("name"))) {
            queryWrapper.like("name", params.get("name"));
        }
        if (params.containsKey("userVipId") && !"-1".equals(params.get("userVipId")) && !"".equals(params.get("userVipId"))) {
            queryWrapper.eq("user_vip_id", params.get("userVipId"));
        }
        if (params.containsKey("mnemonic") && !"-1".equals(params.get("mnemonic")) && !"".equals(params.get("mnemonic"))) {
            queryWrapper.eq("mnemonic", params.get("mnemonic"));
        }
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}
