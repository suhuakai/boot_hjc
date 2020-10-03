package com.tg.api.service.impl;

import com.tg.api.entity.*;
import com.tg.api.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserVipDetailDao;
import org.springframework.transaction.annotation.Transactional;


@Service("userVipDetailService")
public class UserVipDetailServiceImpl extends ServiceImpl<UserVipDetailDao, UserVipDetailEntity> implements UserVipDetailService {

    @Autowired
    VipGradeTypeService vipGradeTypeService;
    @Autowired
    WalletService walletService;
    @Autowired
    UserService userService;
    @Autowired
    BalanceService balanceService;
    @Autowired
    UserEarningsService userEarningsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserVipDetailEntity> page = this.page(
                new Query<UserVipDetailEntity>().getPage(params),
                new QueryWrapper<UserVipDetailEntity>().eq("user_id", params.get("userId")).orderByDesc("date")
        );
        for (UserVipDetailEntity userVipDetailEntity : page.getRecords()) {
            VipGradeTypeEntity vipGradeTypeEntity = vipGradeTypeService.getById(userVipDetailEntity.getVipId());
            userVipDetailEntity.setStatusName((StringUtils.isNotBlank(userVipDetailEntity.getSettleStatus()) && "no".equals(userVipDetailEntity.getSettleStatus())) ? "未结算" : "已结算");
            userVipDetailEntity.setVipGradeName(vipGradeTypeEntity.getName());
            userVipDetailEntity.setBalance(vipGradeTypeEntity.getWorth());
        }

        return new PageUtils(page);
    }

    /**
     * 购买升级
     *
     * @param userId
     * @param vipId
     */
    @Override
    @Transactional
    public void buyVip(Integer userId, Integer vipId) {
        Integer originVipId;
        List<UserVipDetailEntity> userVipDetailEntityList = baseMapper.selectList(new QueryWrapper<UserVipDetailEntity>()
                .eq("user_id", userId).orderByDesc("date"));
        if (CollectionUtils.isNotEmpty(userVipDetailEntityList) && userVipDetailEntityList.size() > 0) {
            UserVipDetailEntity userVipDetailEntity = userVipDetailEntityList.get(0);
            originVipId = userVipDetailEntity.getVipId();
        } else {
            originVipId = vipId;
        }

        VipGradeTypeEntity vipGradeTypeEntity = vipGradeTypeService.getById(vipId);
        UserEntity user = userService.getById(userId);

        //vip用户明细
        UserVipDetailEntity userVipDetailEntity = new UserVipDetailEntity();
        userVipDetailEntity.setUserId(userId);
        userVipDetailEntity.setNumber(vipGradeTypeEntity.getWorth());
        userVipDetailEntity.setDate(LocalDateTime.now());
        userVipDetailEntity.setVipId(vipId);
        userVipDetailEntity.setType("buy");
        userVipDetailEntity.setSettleStatus("no");
        userVipDetailEntity.setOriginalVpiId(originVipId);
        baseMapper.insert(userVipDetailEntity);

        //余额减少
        WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", userId).eq("wallet_type_id", 1));
        if (walletEntity != null) {
            walletEntity.setBalance(walletEntity.getBalance().subtract(vipGradeTypeEntity.getWorth()));
            walletService.updateById(walletEntity);
        }

        //上级用户+15% 定时任务做
        UserEntity userEntity = userService.getById(user.getUpUserId());
       /*  walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", userEntity.getId()).eq("wallet_type_id", 1));
        walletEntity.setBalance(walletEntity.getBalance().add(vipGradeTypeEntity.getWorth().multiply(new BigDecimal("0.15"))));
        walletService.updateById(walletEntity);*/

        // 推荐奖励
        UserEarningsEntity earTj = new UserEarningsEntity();
        earTj.setDate(LocalDateTime.now());
        earTj.setUserId(userEntity.getId());
        earTj.setNumber(vipGradeTypeEntity.getWorth(). multiply(new BigDecimal("0.15")));
        earTj.setSettleStatus("no");
        earTj.setType("upRecommend");
        earTj.setUpUserId(userId);  //下级
        earTj.setWalletTypeId(1);  //余额
        userEarningsService.save(earTj);

        //
    }

}
