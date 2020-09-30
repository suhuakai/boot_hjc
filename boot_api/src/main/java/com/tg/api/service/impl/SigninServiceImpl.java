package com.tg.api.service.impl;

import com.tg.api.common.utils.DateUtils;
import com.tg.api.entity.BalanceEntity;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.service.BalanceService;
import com.tg.api.service.UserEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.SigninDao;
import com.tg.api.entity.SigninEntity;
import com.tg.api.service.SigninService;


@Service("signinService")
public class SigninServiceImpl extends ServiceImpl<SigninDao, SigninEntity> implements SigninService {
    @Autowired
    BalanceService balanceService;
    @Autowired
    UserEarningsService userEarningsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SigninEntity> page = this.page(
                new Query<SigninEntity>().getPage(params),
                new QueryWrapper<>()
        );

        return new PageUtils(page);
    }

    /**
     * 签到
     *
     * @param userId
     * @param signType
     */
    @Override
    public void clickSign(Integer userId, Integer signType) {
        //明细收益
        UserEarningsEntity earTj = new UserEarningsEntity();
        earTj.setDate(LocalDateTime.now());
        earTj.setUserId(userId);
        earTj.setNumber(new BigDecimal(1));
        earTj.setSettleStatus("no");
        if (signType == 1) {
            earTj.setType("sign");
        } else if (signType == 2) {
            earTj.setType("attention");
        } else {
            earTj.setType("browse");
        }

        BalanceEntity balanceEntity = balanceService.getById(1);

        if (balanceEntity.getBalanceMoney().compareTo(new BigDecimal(1)) < 0) {
            earTj.setWalletTypeId(1);
            balanceEntity.setBalanceMoney(balanceEntity.getBalanceMoney().subtract(new BigDecimal(1)));
            balanceService.updateById(balanceEntity);
        } else {
            earTj.setWalletTypeId(3);
        }
        userEarningsService.save(earTj);

        //签到记录
        SigninEntity signinEntity = new SigninEntity();
        signinEntity.setSignBalance(new BigDecimal(1));
        signinEntity.setSignDate(LocalDateTime.now());
        signinEntity.setSignType(signType);
        signinEntity.setWalletTypeId(1);
        signinEntity.setUserId(userId);
        baseMapper.insert(signinEntity);
    }

    /**
     * 签到详情
     *
     * @param userId
     * @param signType
     * @return
     */
    @Override
    public Map<String, Object> getByUserId(Integer userId, Integer signType) {
        List<SigninEntity> signinEntityList = baseMapper.selectList(new QueryWrapper<SigninEntity>()
                .eq("user_id", userId)
                .eq("sign_type", signType));
        List<String> date = new ArrayList<>();
        BigDecimal platformCount = new BigDecimal(0);
        Integer signDay = 0;
        for (SigninEntity signinEntity : signinEntityList) {
            date.add(DateUtils.localDateTime2DateStr(signinEntity.getSignDate()));
            platformCount = platformCount.add(signinEntity.getSignBalance());
            signDay++;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("signDay", signDay);
        map.put("platformCount", platformCount);
        map.put("date", date);
        return map;
    }

}
