package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.SigninDao;
import com.tg.admin.modules.business.entity.SigninEntity;
import com.tg.admin.modules.business.service.SigninService;


@Service("signinService")
public class SigninServiceImpl extends ServiceImpl<SigninDao, SigninEntity> implements SigninService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<SigninEntity>qw = new QueryWrapper<>();
        if (params.containsKey("walletTypeId") && !"-1".equals(params.get("walletTypeId")) && !"".equals(params.get("walletTypeId"))) {
            qw.eq("wallet_type_id", params.get("walletTypeId"));
        }
        if (params.containsKey("signType") && !"-1".equals(params.get("signType")) && !"".equals(params.get("signType"))) {
            qw.eq("sign_type", params.get("signType"));
        }
        if (params.containsKey("userId")  && !"".equals(params.get("userId"))) {
            qw.eq("user_id", params.get("userId"));
        }
        IPage<SigninEntity> page = this.page(
                new Query<SigninEntity>().getPage(params),
                qw
        );

        return new PageUtils(page);
    }

}
