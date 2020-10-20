package com.tg.api.service.impl;

import com.tg.api.common.constant.ConstantConfig;
import com.tg.api.common.exception.RRException;
import com.tg.api.entity.*;
import com.tg.api.service.*;
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

import com.tg.api.dao.AdvertisingUserDao;
import org.springframework.transaction.annotation.Transactional;


@Service("advertisingUserService")
public class AdvertisingUserServiceImpl extends ServiceImpl<AdvertisingUserDao, AdvertisingUserEntity> implements AdvertisingUserService {

    @Autowired
    WalletService walletService;

    @Autowired
    AdvertisingService advertisingService;

    @Autowired
    UserEarningsService userEarningsService;

    @Autowired
    UserService userService;

    @Autowired
    DictionariesService dictionariesService;

    @Autowired
    WalletTypeService walletTypeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AdvertisingUserEntity> queryWrapper = new QueryWrapper<>();
        if (!params.get("status").equals("all")) {
            queryWrapper.eq("status", params.get("status"));
        }
        queryWrapper.eq("user_id", params.get("userId"));
        queryWrapper.orderByDesc("id");
        IPage<AdvertisingUserEntity> page = this.page(
                new Query<AdvertisingUserEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public void saveYiWatch(AdvertisingUserEntity advertisingUserEntity) {
        DictionariesEntity d = dictionariesService.getById(1);

        AdvertisingUserEntity adver = this.getById(advertisingUserEntity.getId());
        if (adver == null || adver.getUserId().compareTo(advertisingUserEntity.getUserId()) != 0 || !adver.getStatus().equals("subscribe")) {
            throw new RRException("无效请请求");
        }
        int count = this.count(new QueryWrapper<AdvertisingUserEntity>().in("status", "yiWatch", "underway").eq("user_id", adver.getUserId()));
        if (count != 0) {
            throw new RRException("上单还没完成");
        }

        WalletEntity wallet = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", adver.getUserId()).eq("wallet_type_id", 2));
        WalletEntity walletLock = walletService.getLock(wallet.getId());
        if (walletLock.getBalance().compareTo(adver.getNumber()) < 0) {
            throw new RRException("金卷不足！");
        }
        walletLock.setBalance(adver.getNumber());
        walletService.reduceWalletBalance(walletLock);


        adver.setWalletTypeId(2);
        adver.setDateYiWatch(LocalDateTime.now());
        adver.setStatus("yiWatch");
        this.updateById(adver);

        UserEarningsEntity userEarningsEntity = new UserEarningsEntity();
        userEarningsEntity.setNumber(adver.getNumber().setScale(2, BigDecimal.ROUND_DOWN));
        userEarningsEntity.setUserId(adver.getUserId());
        userEarningsEntity.setWalletTypeId(2);
        userEarningsEntity.setStatus("operation");
        userEarningsEntity.setType("yes");
        userEarningsEntity.setSettleStatus("yes");
        userEarningsEntity.setNumberZifu("-" + userEarningsEntity.getNumber());
        userEarningsEntity.setContent("观看视频消耗金卷：" + userEarningsEntity.getNumberZifu());
        userEarningsEntity.setDate(LocalDateTime.now());
        userEarningsService.save(userEarningsEntity);

        AdvertisingUserEntity advertis = this.getOne(new QueryWrapper<AdvertisingUserEntity>().eq("user_id", adver.getUserId()).eq("status", "principalRefunded"));
        if (advertis != null) {
            //发放上单收益
            BigDecimal number = advertis.getNumber().multiply(d.getAgencyNumberAward().multiply(advertis.getPrice()));

            advertis.setDateAccomplish(LocalDateTime.now());
            advertis.setStatus("accomplish");

            this.updateById(advertis);

            UserEarningsEntity userEarningsEntityBalance = new UserEarningsEntity();
            userEarningsEntityBalance.setNumber(number.setScale(2, BigDecimal.ROUND_DOWN));
            userEarningsEntityBalance.setUserId(adver.getUserId());
            userEarningsEntityBalance.setWalletTypeId(1);
            userEarningsEntityBalance.setStatus("earnings");
            userEarningsEntityBalance.setType("watch");
            userEarningsEntityBalance.setSettleStatus("no");
            userEarningsEntityBalance.setNumberZifu("+" + userEarningsEntityBalance.getNumber());
            userEarningsEntityBalance.setContent("观看视频结束收益释放：" + userEarningsEntityBalance.getNumberZifu());
            userEarningsEntityBalance.setDate(LocalDateTime.now());
            userEarningsService.save(userEarningsEntityBalance);

            UserEntity user = userService.getById(userEarningsEntity.getUserId());
            if (user.getUpUserId().compareTo(0) != 0) {
                // 一代收益
                UserEntity userOne = userService.getById(user.getUpUserId());

                UserEarningsEntity userEarningsEntityBalanceOne = new UserEarningsEntity();
                userEarningsEntityBalanceOne.setUpUserId(user.getId());
                userEarningsEntityBalanceOne.setNumber(number.multiply(d.getOneAgency()).setScale(2, BigDecimal.ROUND_DOWN));
                userEarningsEntityBalanceOne.setUserId(userOne.getId());
                userEarningsEntityBalanceOne.setWalletTypeId(1);
                userEarningsEntityBalanceOne.setStatus("earnings");
                userEarningsEntityBalanceOne.setType("watchRecommend");
                userEarningsEntityBalanceOne.setSettleStatus("no");
                userEarningsEntityBalanceOne.setNumberZifu("+" + userEarningsEntityBalanceOne.getNumber());
                userEarningsEntityBalanceOne.setContent("下级观看视频获得推荐收益：" + userEarningsEntityBalanceOne.getNumberZifu());
                userEarningsEntityBalanceOne.setDate(LocalDateTime.now());
                userEarningsService.save(userEarningsEntityBalanceOne);

//                WalletEntity walletJin = walletService.getOne(new QueryWrapper<WalletEntity>().eq("wallet_type_id", 4).eq("user_id", userOne.getId()));
//
//                if (walletJin.getBalance().compareTo(BigDecimal.ZERO) > 0) {
//
//                    BigDecimal jinQuan = number.multiply(d.getOneAgency());
//                    WalletEntity walletBalanceLock = walletService.getLock(walletJin.getId());
//                    walletBalanceLock.setBalance(jinQuan);
//                    walletService.reduceWalletBalance(walletBalanceLock);
//
//                    UserEarningsEntity userEarningsEntityJinKuang = new UserEarningsEntity();
//                    userEarningsEntityJinKuang.setUpUserId(user.getId());
//                    userEarningsEntityJinKuang.setNumber(jinQuan);
//                    userEarningsEntityJinKuang.setUserId(userOne.getId());
//                    userEarningsEntityJinKuang.setWalletTypeId(1);
//                    userEarningsEntityJinKuang.setStatus("earnings");
//                    userEarningsEntityJinKuang.setType("watchRecommend");
//                    userEarningsEntityJinKuang.setSettleStatus("no");
//                    userEarningsEntityJinKuang.setNumberZifu("+" + userEarningsEntityJinKuang.getNumber());
//                    userEarningsEntityJinKuang.setContent("下级观看视频获得推荐收益金矿释放余额：" + userEarningsEntityJinKuang.getNumberZifu());
//                    userEarningsEntityJinKuang.setDate(LocalDateTime.now());
//                    userEarningsService.save(userEarningsEntityJinKuang);
//                }
                //二代及以上获得收益
                if (userOne.getUpUserId().compareTo(0) != 0) {
                    upEarnings(userOne, number, user.getId(), d.getTwoAgency());
                }
            }
        }

    }

    @Transactional
    public void upEarnings(UserEntity user, BigDecimal number, Integer userId, BigDecimal twoAgency) {
        UserEntity userTwo = userService.getById(user.getUpUserId());
        if (userTwo.getUpUserId().compareTo(0) != 0) {
            UserEarningsEntity userEarningsEntityBalanceTwo = new UserEarningsEntity();
            userEarningsEntityBalanceTwo.setUpUserId(userId);
            userEarningsEntityBalanceTwo.setNumber(number.multiply(twoAgency).setScale(2, BigDecimal.ROUND_DOWN));
            userEarningsEntityBalanceTwo.setUserId(userTwo.getId());
            userEarningsEntityBalanceTwo.setWalletTypeId(1);
            userEarningsEntityBalanceTwo.setStatus("earnings");
            userEarningsEntityBalanceTwo.setType("watchRecommend");
            userEarningsEntityBalanceTwo.setSettleStatus("no");
            userEarningsEntityBalanceTwo.setNumberZifu("+" + userEarningsEntityBalanceTwo.getNumber());
            userEarningsEntityBalanceTwo.setContent("下级观看视频获得推荐收益：" + userEarningsEntityBalanceTwo.getNumberZifu());
            userEarningsEntityBalanceTwo.setDate(LocalDateTime.now());
            userEarningsService.save(userEarningsEntityBalanceTwo);

//            UserEarningsEntity userEarningsEntityJinKuang = new UserEarningsEntity();
//            userEarningsEntityJinKuang.setUpUserId(user.getId());
//            userEarningsEntityJinKuang.setNumber(number.multiply(twoAgency));
//            userEarningsEntityJinKuang.setUserId(userTwo.getId());
//            userEarningsEntityJinKuang.setWalletTypeId(5);
//            userEarningsEntityJinKuang.setStatus("earnings");
//            userEarningsEntityJinKuang.setType("watchRecommend");
//            userEarningsEntityJinKuang.setSettleStatus("no");
//            userEarningsEntityJinKuang.setNumberZifu("+" + userEarningsEntityJinKuang.getNumber());
//            userEarningsEntityJinKuang.setContent("下级观看视频获得算力池增加：" + userEarningsEntityJinKuang.getNumberZifu());
//            userEarningsEntityJinKuang.setDate(LocalDateTime.now());
//            userEarningsService.save(userEarningsEntityJinKuang);

            //          upEarnings(userTwo, number, userId, twoAgency);
        }
    }

    @Override
    @Transactional
    public void saveSubscribe(AdvertisingUserEntity adv) {
        int count = this.count(new QueryWrapper<AdvertisingUserEntity>().eq("status", "subscribe").eq("user_id", adv.getUserId()));
        if (count != 0) {
            throw new RRException("请先完成上单预约");
        }
        AdvertisingEntity adver = advertisingService.getById(adv.getAdvertistingId());
        if (adver.getStatus().equals("close")) {
            throw new RRException("当前预约暂未开放！");
        }
        WalletTypeEntity walletTypeEntity = walletTypeService.getById(2);

        WalletEntity wallet = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", adv.getUserId()).eq("wallet_type_id", 3));
        WalletEntity walletLock = walletService.getLock(wallet.getId());
        if (walletLock.getBalance().compareTo(adver.getNumber().divide(walletTypeEntity.getPrice(), 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("0.01"))) <
                0) {
            throw new RRException("银矿池不足！");
        }
        walletLock.setBalance(adver.getNumber().multiply(new BigDecimal("0.01")));
        walletService.reduceWalletBalance(walletLock);

        adv.setNumber(adver.getNumber().divide(walletTypeEntity.getPrice(), 4, BigDecimal.ROUND_DOWN));
        adv.setAdvertistingName(adver.getTitle());
        adv.setAdvertistingId(adver.getId());
        adv.setStatus("subscribe");
        adv.setWalletTypeId(2);
        adv.setDateSubscribe(LocalDateTime.now());
        adv.setPrice(walletTypeEntity.getPrice());
        this.save(adv);

        adver.setBuyCount(adver.getBuyCount() + 1);
        advertisingService.updateById(adver);

        UserEarningsEntity userEarningsEntity = new UserEarningsEntity();
        userEarningsEntity.setNumber(adver.getNumber().divide(walletTypeEntity.getPrice(), 4, BigDecimal.ROUND_DOWN).multiply(new BigDecimal("0.01")).setScale(2, BigDecimal.ROUND_DOWN));
        userEarningsEntity.setUserId(adv.getUserId());
        userEarningsEntity.setWalletTypeId(3);
        userEarningsEntity.setStatus("operation");
        userEarningsEntity.setType("yes");
        userEarningsEntity.setSettleStatus("yes");
        userEarningsEntity.setNumberZifu("-" + userEarningsEntity.getNumber());
        userEarningsEntity.setContent("预约视频消耗银矿池：" + userEarningsEntity.getNumberZifu());
        userEarningsEntity.setDate(LocalDateTime.now());
        userEarningsService.save(userEarningsEntity);

    }
}
