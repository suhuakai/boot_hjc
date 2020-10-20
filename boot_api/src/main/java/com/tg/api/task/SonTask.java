package com.tg.api.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.api.common.constant.ConstantConfig;
import com.tg.api.entity.*;
import com.tg.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class SonTask {

    @Autowired
    AdvertisingUserService advertisingUserService;

    @Autowired
    UserEarningsService userEarningsService;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    @Autowired
    WalletTypeService walletTypeService;

    @Transactional
    public void earnings(UserEarningsEntity ue) {
        WalletEntity walletBalance = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", ue.getUserId()).eq("wallet_type_id", ue.getWalletTypeId()));
        WalletEntity walletBalanceLock = walletService.getLock(walletBalance.getId());
        walletBalanceLock.setBalance(ue.getNumber());
        walletService.increaseWalletBalance(walletBalanceLock);

        ue.setSettleStatus("yes");
        userEarningsService.updateById(ue);
    }


    @Transactional
    public void taskAdvertising(AdvertisingUserEntity adv, DictionariesEntity d) {
        adv.setDatePrincipalrefunded(LocalDateTime.now());
        adv.setStatus("principalRefunded");

        WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", adv.getUserId()).eq("wallet_type_id", adv.getWalletTypeId()));
        WalletEntity walletLock = walletService.getLock(walletEntity.getId());
        walletLock.setBalance(adv.getNumber());
        walletService.increaseWalletBalance(walletLock);

        UserEarningsEntity userEarningsEntity = new UserEarningsEntity();
        userEarningsEntity.setNumber(adv.getNumber().setScale(2, BigDecimal.ROUND_DOWN));
        userEarningsEntity.setUserId(adv.getUserId());
        userEarningsEntity.setWalletTypeId(2);
        userEarningsEntity.setStatus("operation");
        userEarningsEntity.setType("yes");
        userEarningsEntity.setSettleStatus("yes");
        userEarningsEntity.setNumberZifu("+" + userEarningsEntity.getNumber());
        userEarningsEntity.setContent("观看视频结束释放本金金卷：" + userEarningsEntity.getNumberZifu());
        userEarningsEntity.setDate(LocalDateTime.now());
        userEarningsService.save(userEarningsEntity);

        advertisingUserService.updateById(adv);
    }


    public void silverRelease(WalletEntity w) {
        WalletEntity walletLock = walletService.getLock(w.getId());
        BigDecimal number = walletLock.getBalance().multiply(new BigDecimal("0.001"));
        walletLock.setBalance(number);
        walletService.reduceWalletBalance(walletLock);

        UserEarningsEntity userEarningsEntity = new UserEarningsEntity();
        userEarningsEntity.setNumber(number);
        userEarningsEntity.setUserId(walletLock.getUserId());
        userEarningsEntity.setWalletTypeId(3);
        userEarningsEntity.setStatus("operation");
        userEarningsEntity.setType("yes");
        userEarningsEntity.setSettleStatus("yes");
        userEarningsEntity.setNumberZifu("-" + userEarningsEntity.getNumber());
        userEarningsEntity.setContent("银矿池释放：" + userEarningsEntity.getNumberZifu());
        userEarningsEntity.setDate(LocalDateTime.now());
        userEarningsService.save(userEarningsEntity);


        UserEarningsEntity userEarningsEntityBalnce = new UserEarningsEntity();
        userEarningsEntityBalnce.setNumber(number);
        userEarningsEntityBalnce.setUserId(walletLock.getUserId());
        userEarningsEntityBalnce.setWalletTypeId(1);
        userEarningsEntityBalnce.setStatus("operation");
        userEarningsEntityBalnce.setType("yes");
        userEarningsEntityBalnce.setSettleStatus("no");
        userEarningsEntityBalnce.setNumberZifu("-" + userEarningsEntity.getNumber());
        userEarningsEntityBalnce.setContent("银矿池释放余额：" + userEarningsEntity.getNumberZifu());
        userEarningsEntityBalnce.setDate(LocalDateTime.now());
        userEarningsService.save(userEarningsEntityBalnce);
    }

    @Transactional
    public void abc(WalletEntity f){
        BigDecimal b = f.getBalance();
        f.setBalance(BigDecimal.ZERO);

        WalletEntity walletU = walletService.getOne(new QueryWrapper<WalletEntity>().eq("wallet_type_id", 3).eq("user_id", f.getUserId()));
        walletU.setBalance(walletU.getBalance().add(b));

        walletService.updateById(walletU);
        walletService.updateById(f);
    }
}

