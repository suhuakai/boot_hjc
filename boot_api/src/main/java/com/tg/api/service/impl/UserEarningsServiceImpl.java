package com.tg.api.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserEarningsDao;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.service.UserEarningsService;


@Service("userEarningsService")
public class UserEarningsServiceImpl extends ServiceImpl<UserEarningsDao, UserEarningsEntity> implements UserEarningsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEarningsEntity> page = this.page(
                new Query<UserEarningsEntity>().getPage(params),
                new QueryWrapper<UserEarningsEntity>().eq("user_id", params.get("userId")).orderByDesc("date")
        );
        for (UserEarningsEntity userEarningsEntity : page.getRecords()) {
            String typeName;
            if ("register".equals(userEarningsEntity.getType())) {
                typeName = "注册";
            } else if ("recommend".equals(userEarningsEntity.getType())) {
                typeName = "注册推荐";
            } else if ("attention".equals(userEarningsEntity.getType())) {
                typeName = "关注";
            } else if ("browse".equals(userEarningsEntity.getType())) {
                typeName = "浏览";
            } else if ("sign".equals(userEarningsEntity.getType())) {
                typeName = "签到";
            } else {
                typeName = "升级推荐";
            }
            userEarningsEntity.setTypeName(typeName);
            userEarningsEntity.setBalance(userEarningsEntity.getNumber());
            userEarningsEntity.setStatusName("no".equals(userEarningsEntity.getSettleStatus()) ? "未结算" : "已结算");
        }

        return new PageUtils(page);
    }

}
