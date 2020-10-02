package com.tg.api.service.impl;

import com.tg.api.common.exception.RRException;
import com.tg.api.common.utils.DateUtils;
import com.tg.api.entity.BalanceEntity;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.service.BalanceService;
import com.tg.api.service.UserEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.SigninDao;
import com.tg.api.entity.SigninEntity;
import com.tg.api.service.SigninService;
import org.springframework.transaction.annotation.Transactional;


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
                new QueryWrapper<SigninEntity>().eq("sign_type", params.get("signType")).eq("user_id", params.get("userId"))
        );
        for (SigninEntity pager : page.getRecords()) {
            if (1 == pager.getSignType()) {
                pager.setSignTypeName("签到奖励");
            } else if (2 == pager.getSignType()) {
                pager.setSignTypeName("关注奖励");
            } else {
                pager.setSignTypeName("浏览奖励");
            }
        }

        return new PageUtils(page);
    }

    /**
     * 签到
     *
     * @param userId
     * @param signType
     */
    @Override
    @Transactional
    public void clickSign(Integer userId, Integer signType) {
        //明细收益
        UserEarningsEntity earTj = new UserEarningsEntity();
        earTj.setDate(LocalDateTime.now());
        earTj.setUserId(userId);
        earTj.setNumber(new BigDecimal(1));
        earTj.setSettleStatus("no");
        BalanceEntity balanceEntity = balanceService.getById(1);

        if (balanceEntity.getBalanceMoney().compareTo(new BigDecimal(1)) < 0) {
            earTj.setWalletTypeId(1);
            balanceEntity.setBalanceMoney(balanceEntity.getBalanceMoney().subtract(new BigDecimal(1)));
            balanceService.updateById(balanceEntity);
        } else {
            earTj.setWalletTypeId(3);
        }
        if (signType == 1) {  //签到一次被置灰
            earTj.setType("sign");
        } else if (signType == 2) {   //关注
            earTj.setType("attention");
        } else {
            earTj.setType("browse");
        }

        userEarningsService.save(earTj);

        SigninEntity signinEntity = baseMapper.selectOne(new QueryWrapper<SigninEntity>().eq("user_id", userId)
                .like("sign_date", DateUtils.formatDateTime(new Date())).eq("sign_type", signType));
        if (signinEntity != null) {
            throw new RRException("今天已签到或已关注或已浏览");
        }

        //签到记录
        signinEntity = new SigninEntity();
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
