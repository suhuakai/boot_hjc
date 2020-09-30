package com.tg.api.service.impl;

import com.tg.api.entity.UserEntity;
import com.tg.api.entity.VipGradeTypeEntity;
import com.tg.api.entity.WalletEntity;
import com.tg.api.service.UserService;
import com.tg.api.service.VipGradeTypeService;
import com.tg.api.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserVipDetailDao;
import com.tg.api.entity.UserVipDetailEntity;
import com.tg.api.service.UserVipDetailService;


@Service("userVipDetailService")
public class UserVipDetailServiceImpl extends ServiceImpl<UserVipDetailDao, UserVipDetailEntity> implements UserVipDetailService {

    @Autowired
    VipGradeTypeService vipGradeTypeService;
    @Autowired
    WalletService walletService;
    @Autowired
    UserService userService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserVipDetailEntity> page = this.page(
                new Query<UserVipDetailEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 购买升级
     *
     * @param userId
     * @param vipId
     */
    @Override
    public void buyVip(Integer userId, Integer vipId) {
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

        baseMapper.insert(userVipDetailEntity);

        //余额减少
        WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", userId).eq("wallet_type_id", 1));
        walletEntity.setBalance(walletEntity.getBalance().subtract(vipGradeTypeEntity.getWorth()));
        walletService.updateById(walletEntity);

        //上级用户+15%
        UserEntity userEntity = userService.getById(user.getUpUserId());
        walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", userEntity.getId()).eq("wallet_type_id", 1));
        walletEntity.setBalance(walletEntity.getBalance().add(vipGradeTypeEntity.getWorth().multiply(new BigDecimal("0.15"))));
        walletService.updateById(walletEntity);

    }

}
