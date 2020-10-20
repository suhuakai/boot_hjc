package com.tg.api.service.impl;

import com.tg.api.common.constant.MineParameter;
import com.tg.api.common.exception.RRException;
import com.tg.api.entity.WalletEntity;
import com.tg.api.entity.WalletTypeEntity;
import com.tg.api.service.SequenceService;
import com.tg.api.service.WalletService;
import com.tg.api.service.WalletTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.WithdrawDao;
import com.tg.api.entity.WithdrawEntity;
import com.tg.api.service.WithdrawService;
import org.springframework.transaction.annotation.Transactional;


@Service("withdrawService")
public class WithdrawServiceImpl extends ServiceImpl<WithdrawDao, WithdrawEntity> implements WithdrawService {

    @Autowired
    SequenceService sequenceService;

    @Autowired
    WalletService walletService;

    @Autowired
    WalletTypeService walletTypeService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveWithdraw(WithdrawEntity withdrawEntity) {
        WalletTypeEntity walletTypeEntity = walletTypeService.getById(1);

        int count = this.count(new QueryWrapper<WithdrawEntity>().eq(MineParameter.user_id, withdrawEntity.getUserId()).eq(MineParameter.status, MineParameter.audit).eq("type", MineParameter.withdraw));
        if (count > 0) {
            throw new RRException("请先完成上笔提现");
        }
        WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq(MineParameter.user_id, withdrawEntity.getUserId()).eq("wallet_type_id", 1));
        WalletEntity walletLock = walletService.getLock(walletEntity.getId());
        if (walletLock.getBalance().compareTo(withdrawEntity.getNumber()) < 0) {
            throw new RRException("余额不足！");
        }

        walletLock.setBalance(withdrawEntity.getNumber());
        walletService.reduceWalletBalance(walletLock);


        withdrawEntity.setType("withdraw");
        withdrawEntity.setFee(withdrawEntity.getNumber().multiply(walletTypeEntity.getFee()));
        withdrawEntity.setStatus(MineParameter.audit);
        withdrawEntity.setDate(LocalDateTime.now());

        this.save(withdrawEntity);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WithdrawEntity> page = this.page(
                new Query<WithdrawEntity>().getPage(params),
                new QueryWrapper<WithdrawEntity>().eq("type",params.get("type")).eq("user_id", params.get("userId")).orderByDesc("date")
        );
        for (WithdrawEntity withdrawEntity : page.getRecords()) {
            withdrawEntity.setTypeName("withdraw".equals(withdrawEntity.getType()) ? "提现" : "充值");
            withdrawEntity.setUserTypeName("bankCard".equals(withdrawEntity.getUseType()) ? "银行卡" : "支付宝");
            withdrawEntity.setBalance(withdrawEntity.getRealityNumber());
            String statusName;
            if ("creation".equals(withdrawEntity.getStatus())) {
                statusName = "创建";
            } else if ("audit".equals(withdrawEntity.getStatus())) {
                statusName = "审核中";
            } else if ("succeed".equals(withdrawEntity.getStatus())) {
                statusName = "成功";
            } else {
                statusName = "失败";
            }
            withdrawEntity.setStatusName(statusName);
        }

        return new PageUtils(page);
    }

    /**
     * 银行卡充值
     *
     * @param withdrawEntity
     * @return
     */
    @Override
    public WithdrawEntity insert(WithdrawEntity withdrawEntity) {
        int count = this.count(new QueryWrapper<WithdrawEntity>().eq(MineParameter.user_id, withdrawEntity.getUserId()).eq(MineParameter.status, MineParameter.audit).eq("type", MineParameter.recharge));
        if (count > 0) {
            throw new RRException("请先完成上笔充值");
        }

        WithdrawEntity with = new WithdrawEntity();
        BeanUtils.copyProperties(withdrawEntity, with);
        with.setId(sequenceService.nextval("withdraw_id"));
        with.setStatus("audit");
        with.setDate(LocalDateTime.now());
        with.setType("recharge");
        //with.setRandomCode(Calendar.getInstance().getTimeInMillis());
        baseMapper.insert(with);
        return with;
    }

}
