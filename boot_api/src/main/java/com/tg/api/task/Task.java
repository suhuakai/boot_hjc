package com.tg.api.task;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.api.entity.AdvertisingUserEntity;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.entity.WalletEntity;
import com.tg.api.service.AdvertisingUserService;
import com.tg.api.service.DictionariesService;
import com.tg.api.service.UserEarningsService;
import com.tg.api.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Task {

    @Autowired
    UserEarningsService userEarningsService;

    @Autowired
    AdvertisingUserService advertisingUserService;

    @Autowired
    SonTask sonTask;

    @Autowired
    DictionariesService dictionariesService;

    @Autowired
    WalletService walletService;

    /**
     * 收益和本金释放
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void taskAdvertising() {
        List<AdvertisingUserEntity> list = advertisingUserService.list(new QueryWrapper<AdvertisingUserEntity>().eq("status", "underway"));
        list.forEach(f -> {
            LocalDateTime now = LocalDateTime.now();
            Duration duration = Duration.between(f.getDateUnderway(), now);
            System.out.println(f.getId() + "============" + duration.toMinutes());

            System.out.println("系统时间================" + now);
            if (duration.toMinutes() > 2) {
                sonTask.taskAdvertising(f, dictionariesService.getById(1));
            }
        });
    }

    /**
     * 收益释放
     */
    @Scheduled(cron = "0/10 * * * * ?")
    public void earnings() {
        List<UserEarningsEntity> userEarningsEntityList = userEarningsService.list(new QueryWrapper<UserEarningsEntity>().eq("settle_status", "no"));
        userEarningsEntityList.forEach(f -> {
            sonTask.earnings(f);
        });
    }

    /**
     * 银矿释放
     */
    @Scheduled(cron = "0 0 01 * * ?")
    public void silverRelease() {
        List<WalletEntity> walletEntityList = walletService.list(new QueryWrapper<WalletEntity>().eq("wallet_type_id", 3).gt("balance", BigDecimal.ZERO).ne("user_id", 1567));
        walletEntityList.forEach(f -> {
            sonTask.silverRelease(f);
        });
    }


}
