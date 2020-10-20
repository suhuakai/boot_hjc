package com.tg.admin.modules.business.service.impl;

import com.tg.admin.modules.business.dao.WalletDao;
import com.tg.admin.modules.business.entity.*;
import com.tg.admin.modules.business.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.WithdrawDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("withdrawService")
public class WithdrawServiceImpl extends ServiceImpl<WithdrawDao, WithdrawEntity> implements WithdrawService {
    @Autowired
    UserEarningsDetailService userEarningsDetailService;
    @Autowired
    WalletService walletService;
    @Resource
    WalletDao walletDao;

    @Autowired
    WalletTypeService walletTypeService;

    @Autowired
    UserEarningsService userEarningsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<WithdrawEntity> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("userId") && !"".equals(params.get("userId"))) {
            queryWrapper.eq("user_id", params.get("userId"));
        }
        if (params.containsKey("useType") && !"-1".equals(params.get("useType"))) {
            queryWrapper.eq("use_type", params.get("useType"));
        }
        if (params.containsKey("type") && !"-1".equals(params.get("type"))) {
            queryWrapper.eq("type", params.get("type"));
        }


        IPage<WithdrawEntity> page = this.page(
                new Query<WithdrawEntity>().getPage(params),
                queryWrapper
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean audit(Integer id, Integer type) {
        WithdrawEntity withdraw = this.getById(id);
        withdraw.setAccomplishDate(LocalDateTime.now());
        if (!withdraw.getStatus().equals("audit")) {
            return true;
        }
        // 成功
        if (type == 1) {
            withdraw.setStatus("succeed");
            // 提现审核成功
            if (withdraw.getType().equals("withdraw")) {

            } else {
                WalletTypeEntity walletTypeEntity = walletTypeService.getById(2);
                BigDecimal number = withdraw.getNumber().divide(walletTypeEntity.getPrice(), 4, BigDecimal.ROUND_DOWN);

                WalletEntity wallet = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", withdraw.getUserId()).eq("wallet_type_id", 2));
                WalletEntity walletLock = walletDao.getLock(wallet.getId());
                walletLock.setBalance(number);
                walletDao.increaseWalletBalance(walletLock);

                UserEarningsEntity userEarningsEntity = new UserEarningsEntity();
                userEarningsEntity.setNumber(number);
                userEarningsEntity.setUserId(wallet.getUserId());
                userEarningsEntity.setWalletTypeId(2);
                userEarningsEntity.setStatus("operation");
                userEarningsEntity.setType("yes");
                userEarningsEntity.setSettleStatus("yes");
                userEarningsEntity.setNumberZifu("+" + userEarningsEntity.getNumber());
                userEarningsEntity.setContent("充值金券：" + userEarningsEntity.getNumberZifu() + "金卷单价：" + walletTypeEntity.getPrice());
                userEarningsEntity.setDate(LocalDateTime.now());
                userEarningsService.save(userEarningsEntity);
            }
        } else {
            withdraw.setStatus("failed");
            if (withdraw.getType().equals("withdraw")) {
                WalletEntity wallet = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", withdraw.getUserId()).eq("wallet_type_id", 2));
                WalletEntity walletLock = walletDao.getLock(wallet.getId());
                walletLock.setBalance(withdraw.getNumber());
                walletDao.increaseWalletBalance(walletLock);
            } else {

            }
        }
        this.updateById(withdraw);
        return false;
    }


}
