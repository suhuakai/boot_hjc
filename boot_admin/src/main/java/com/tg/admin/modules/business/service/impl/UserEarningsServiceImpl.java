package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.UserEarningsDao;
import com.tg.admin.modules.business.entity.UserEarningsEntity;
import com.tg.admin.modules.business.service.UserEarningsService;


@Service("userEarningsService")
public class UserEarningsServiceImpl extends ServiceImpl<UserEarningsDao, UserEarningsEntity> implements UserEarningsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserEarningsEntity> qw = new QueryWrapper();
        if (params.containsKey("walletTypeId") && !"-1".equals(params.get("walletTypeId")) && !"".equals(params.get("walletTypeId"))) {
            qw.eq("wallet_type_id", params.get("walletTypeId"));
        }
        if (params.containsKey("type") && !"-1".equals(params.get("type")) && !"".equals(params.get("type"))) {
            qw.eq("type", params.get("type"));
        }
        if (params.containsKey("userId") &&  !"".equals(params.get("userId"))) {
            qw.eq("user_id", params.get("userId"));
        }
        if (params.containsKey("settleStatus") && !"-1".equals(params.get("settleStatus")) && !"".equals(params.get("settleStatus"))) {
            qw.eq("settle_status", params.get("settleStatus"));
        }
        IPage<UserEarningsEntity> page = this.page(
                new Query<UserEarningsEntity>().getPage(params),
                qw
        );

        return new PageUtils(page);
    }

}
