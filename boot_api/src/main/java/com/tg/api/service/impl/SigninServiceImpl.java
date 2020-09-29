package com.tg.api.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.SigninDao;
import com.tg.api.entity.SigninEntity;
import com.tg.api.service.SigninService;


@Service("signinService")
public class SigninServiceImpl extends ServiceImpl<SigninDao, SigninEntity> implements SigninService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SigninEntity> page = this.page(
                new Query<SigninEntity>().getPage(params),
                new QueryWrapper<SigninEntity>()
        );

        return new PageUtils(page);
    }

}
