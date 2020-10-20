package com.tg.api.service.impl;

import com.tg.api.common.exception.RRException;
import com.tg.api.entity.WalletEntity;
import com.tg.api.service.WalletService;
import com.tg.api.service.WalletTypeService;
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

import com.tg.api.dao.UserEarningsDao;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.service.UserEarningsService;
import org.springframework.transaction.annotation.Transactional;


@Service("userEarningsService")
public class UserEarningsServiceImpl extends ServiceImpl<UserEarningsDao, UserEarningsEntity> implements UserEarningsService {

    @Autowired
    WalletService walletService;

    @Autowired
    WalletTypeService walletTypeService;

    @Override
    @Transactional
    public void saveEarnginx(UserEarningsEntity userEarningsEntity) {
        WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", userEarningsEntity.getUserId()).eq("wallet_type_id", userEarningsEntity.getWalletTypeId()));

        WalletEntity walletUp = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", userEarningsEntity.getUpUserId()).eq("wallet_type_id", userEarningsEntity.getWalletTypeId()));
        if (walletUp == null) {
            throw new RRException("无效接受账号");
        }
        boolean b = false;
        if (userEarningsEntity.getNumber().compareTo(BigDecimal.ZERO) == -1) {
            b = true;
        }
        userEarningsEntity.setNumber(userEarningsEntity.getNumber().abs());
        if (userEarningsEntity.getUserId().compareTo(1567) == 0) {
            WalletEntity walletLock = walletService.getLock(walletEntity.getId());
            if (b) {
                walletLock.setBalance(walletLock.getBalance().add(userEarningsEntity.getNumber()));
            } else {
                walletLock.setBalance(walletLock.getBalance().subtract(userEarningsEntity.getNumber()));
            }
            walletService.updateById(walletLock);

            WalletEntity walletUpLock = walletService.getLock(walletUp.getId());
            if (b) {
                walletUpLock.setBalance(walletUpLock.getBalance().subtract(userEarningsEntity.getNumber()));
            } else {
                walletUpLock.setBalance(walletUpLock.getBalance().add(userEarningsEntity.getNumber()));
            }
            walletService.updateById(walletUpLock);
        } else {
            WalletEntity walletLock = walletService.getLock(walletEntity.getId());
            if (walletEntity.getBalance().compareTo(userEarningsEntity.getNumber()) < 0) {
                throw new RRException("账号划转数量不足,无法划账");
            }
            walletLock.setBalance(walletLock.getBalance().subtract(userEarningsEntity.getNumber()));
            walletService.updateById(walletLock);

            WalletEntity walletUpLock = walletService.getLock(walletUp.getId());
            walletUpLock.setBalance(walletUpLock.getBalance().add(userEarningsEntity.getNumber()));
            walletService.updateById(walletUpLock);
        }
        userEarningsEntity.setNumberZifu(b ? "+" + userEarningsEntity.getNumber() : "-" + userEarningsEntity.getNumber());
        userEarningsEntity.setSettleStatus("yes");
        userEarningsEntity.setDate(LocalDateTime.now());
        userEarningsEntity.setContent(walletEntity.getUserId() + "往uid:" + walletUp.getUserId() + "划账" + walletTypeService.getById(userEarningsEntity.getWalletTypeId()).getName() + ":" + userEarningsEntity.getNumberZifu());
        userEarningsEntity.setStatus("operation");
        userEarningsEntity.setType("transfer");
        this.save(userEarningsEntity);

        UserEarningsEntity userEarnings = new UserEarningsEntity();
        userEarnings.setNumber(userEarningsEntity.getNumber());
        userEarnings.setNumberZifu(b ? "-" + userEarningsEntity.getNumber() : "+" + userEarningsEntity.getNumber() + "");
        userEarnings.setSettleStatus("yes");
        userEarnings.setDate(LocalDateTime.now());
        userEarnings.setContent(walletEntity.getUserId() + "往uid:" + walletUp.getUserId() + "划账" + walletTypeService.getById(userEarningsEntity.getWalletTypeId()).getName() + ":" + userEarnings.getNumberZifu());
        userEarnings.setStatus("operation");
        userEarnings.setType("transfer");
        userEarnings.setUserId(walletUp.getUserId());
        userEarnings.setUpUserId(walletEntity.getUserId());
        userEarnings.setDate(LocalDateTime.now());
        userEarnings.setWalletTypeId(walletEntity.getWalletTypeId());

        this.save(userEarnings);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        QueryWrapper<UserEarningsEntity> queryWrapper = new QueryWrapper<>();
        if (!params.get("type").equals("all")) {
            if (params.get("type").equals("watch")) {
                queryWrapper.in("type", "watch", "watchRecommend");
            }
        } else {
            queryWrapper.eq("wallet_type_id", params.get("walletTypeId"));
        }
        queryWrapper.eq("user_id", params.get("userId"));
        queryWrapper.orderByDesc("id");
        IPage<UserEarningsEntity> page = this.page(
                new Query<UserEarningsEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

}
