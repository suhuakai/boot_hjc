package com.tg.api.service.impl;

import com.tg.api.service.SequenceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


@Service("withdrawService")
public class WithdrawServiceImpl extends ServiceImpl<WithdrawDao, WithdrawEntity> implements WithdrawService {

    @Autowired
    SequenceService sequenceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<WithdrawEntity> page = this.page(
                new Query<WithdrawEntity>().getPage(params),
                new QueryWrapper<WithdrawEntity>().eq("user_id", params.get("userId")).orderByDesc("date")
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
        WithdrawEntity with = new WithdrawEntity();
        BeanUtils.copyProperties(withdrawEntity, with);
        with.setId(sequenceService.nextval("withdraw_id"));
        with.setStatus("creation");
        with.setDate(LocalDateTime.now());
        //with.setRandomCode(Calendar.getInstance().getTimeInMillis());
        baseMapper.insert(with);
        return with;
    }

}
