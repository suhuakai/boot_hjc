package com.tg.admin.modules.business.service.impl;

import com.tg.admin.modules.business.dao.WalletDao;
import com.tg.admin.modules.business.entity.UserEarningsDetailEntity;
import com.tg.admin.modules.business.entity.WalletEntity;
import com.tg.admin.modules.business.service.UserEarningsDetailService;
import com.tg.admin.modules.business.service.WalletService;
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
import com.tg.admin.modules.business.entity.WithdrawEntity;
import com.tg.admin.modules.business.service.WithdrawService;
import org.springframework.transaction.annotation.Transactional;


@Service("withdrawService")
public class WithdrawServiceImpl extends ServiceImpl<WithdrawDao, WithdrawEntity> implements WithdrawService {
    @Autowired
    UserEarningsDetailService userEarningsDetailService;
    @Autowired
    WalletService walletService;
    @Autowired
    WalletDao walletDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WithdrawEntity> page = this.page(
                new Query<WithdrawEntity>().getPage(params),
                new QueryWrapper<WithdrawEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean audit(Integer id, Integer type) {
        WithdrawEntity withdraw = this.getById(id);
//        if (withdraw.getType().equals("recharge")) {
//            return false;
//        }
        withdraw.setAccomplishDate(LocalDateTime.now());
        if (!withdraw.getStatus().equals("audit")) {
            return true;
        }
        // 成功
        if (type == 1) {
            withdraw.setStatus("succeed");
            // 提现审核成功
            if (withdraw.getType().equals("withdraw")) {
                // 提现审核成功
                UserEarningsDetailEntity earDk = new UserEarningsDetailEntity();
                earDk.setUserId(withdraw.getUserId());
                earDk.setNumber(withdraw.getFee().setScale(2, BigDecimal.ROUND_DOWN));
                earDk.setNumberZifu("+" + earDk.getNumber());
                earDk.setOrderId(withdraw.getId());
                earDk.setWalletTypeId(3);
                earDk.setContent("提现成功获得抵扣卷：" + earDk.getNumberZifu());
                earDk.setDate(LocalDateTime.now());
                earDk.setType("operation");
                //     earDk.setWeightingRate(u.getWeightingRate());
                earDk.setUpUserId(0);
                earDk.setEarningsType("not");
                earDk.setGrade(0);
                earDk.setSettleStatus("no");
                //   earDk.setShuntRate(dic.getConsumeVoucher());
                earDk.setDate(LocalDateTime.now());
                userEarningsDetailService.save(earDk);

                // 提现审核成功
                UserEarningsDetailEntity earDkTx = new UserEarningsDetailEntity();
                earDkTx.setUserId(withdraw.getUserId());
                earDkTx.setNumber(withdraw.getNumber().subtract(withdraw.getFee()).setScale(2, BigDecimal.ROUND_DOWN));
                earDkTx.setNumberZifu("-" + earDkTx.getNumber());
                earDkTx.setOrderId(withdraw.getId());
                earDkTx.setWalletTypeId(2);
                earDkTx.setContent("提现成功扣除福豆：" + earDkTx.getNumberZifu());
                earDkTx.setDate(LocalDateTime.now());
                earDkTx.setType("operation");
                //     earDk.setWeightingRate(u.getWeightingRate());
                earDkTx.setUpUserId(0);
                earDkTx.setEarningsType("not");
                earDkTx.setGrade(0);
                earDkTx.setSettleStatus("yes");
                //   earDk.setShuntRate(dic.getConsumeVoucher());
                earDkTx.setDate(LocalDateTime.now());
                userEarningsDetailService.save(earDkTx);


            } else {
                WalletEntity wallet = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", withdraw.getUserId()).eq("wallet_type_id", 1));
                WalletEntity walletLock = walletDao.getLock(wallet.getId());
                walletLock.setBalance(withdraw.getNumber());
                walletDao.increaseWalletBalance(walletLock);
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
