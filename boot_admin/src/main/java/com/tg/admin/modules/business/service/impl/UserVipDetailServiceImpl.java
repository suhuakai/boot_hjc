package com.tg.admin.modules.business.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.UserVipDetailDao;
import com.tg.admin.modules.business.entity.UserVipDetailEntity;
import com.tg.admin.modules.business.service.UserVipDetailService;


@Service("userVipDetailService")
public class UserVipDetailServiceImpl extends ServiceImpl<UserVipDetailDao, UserVipDetailEntity> implements UserVipDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserVipDetailEntity> qw = new QueryWrapper<>();
        if (params.containsKey("vipId") && !"-1".equals(params.get("vipId")) && !"".equals(params.get("vipId"))) {
            qw.eq("vip_id", params.get("vipId"));
        }
        if (params.containsKey("type") && !"-1".equals(params.get("type")) && !"".equals(params.get("type"))) {
            qw.eq("type", params.get("type"));
        }
        if (params.containsKey("settleStatus") && !"-1".equals(params.get("settleStatus")) && !"".equals(params.get("settleStatus"))) {
            qw.eq("settle_status", params.get("settleStatus"));
        }
        IPage<UserVipDetailEntity> page = this.page(
                new Query<UserVipDetailEntity>().getPage(params),
                qw
        );

        return new PageUtils(page);
    }

}
