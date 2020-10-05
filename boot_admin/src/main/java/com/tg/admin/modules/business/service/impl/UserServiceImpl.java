package com.tg.admin.modules.business.service.impl;

import com.tg.admin.modules.business.service.WalletService;
import com.tg.admin.modules.business.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.Query;

import com.tg.admin.modules.business.dao.UserDao;
import com.tg.admin.modules.business.entity.UserEntity;
import com.tg.admin.modules.business.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Autowired
    WithdrawService userWithdrawService;

    @Autowired
    WalletService walletService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        if (params.containsKey("id") && !"".equals(params.get("id"))) {
            queryWrapper.eq("id", params.get("id"));
        }
        if (params.containsKey("name") && !"".equals(params.get("name"))) {
            queryWrapper.like("name", params.get("name"));
        }
        if (params.containsKey("userVipId") && !"-1".equals(params.get("userVipId")) && !"".equals(params.get("userVipId"))) {
            queryWrapper.eq("user_vip_id", params.get("userVipId"));
        }
        if (params.containsKey("mnemonic") && !"-1".equals(params.get("mnemonic")) && !"".equals(params.get("mnemonic"))) {
            queryWrapper.eq("mnemonic", params.get("mnemonic"));
        }
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public Map statistics() {
        Map map = new HashMap();

        map.put("userCountAll", this.count());
        map.put("userCount", this.count(new QueryWrapper<UserEntity>().between("date", LocalDate.now(), LocalDateTime.now())));

        // 今日充值
        QueryWrapper qwJr = new QueryWrapper<>();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()).toString();
        qwJr.like("date", date);
        qwJr.like("status", "succeed");
        qwJr.like("type", "recharge");
        qwJr.select("COALESCE(sum(number),0.00) as recharge");
        Map map2 = userWithdrawService.getMap(qwJr);
        map.put("recharge", map2.get("recharge"));

        // 充值总额
        QueryWrapper qw = new QueryWrapper<>();
        qw.eq("status", "succeed");
        qw.eq("type", "recharge");
        qw.select("COALESCE(sum(number),0.00) as recharge");
        Map map3 = userWithdrawService.getMap(qw);
        map.put("rechargeAll", map3.get("recharge"));

        // 体现总额
        QueryWrapper tx = new QueryWrapper<>();
        tx.eq("status", "succeed");
        tx.eq("type", "withdraw");
        tx.select("COALESCE(sum(number),0.00) as recharge");
        Map map4 = userWithdrawService.getMap(tx);
        map.put("tixianAll", map4.get("recharge"));

        // 余额池
        QueryWrapper txBalance = new QueryWrapper<>();
        txBalance.eq("wallet_type_id",1);
        txBalance.select("COALESCE(sum(balance),0.00) as recharge");
        Map map5 = walletService.getMap(txBalance);
        map.put("balance", map5.get("recharge"));

        // 嗨豆池
        QueryWrapper txJs = new QueryWrapper<>();
        txJs.eq("wallet_type_id",2);
        txJs.select("COALESCE(sum(balance),0.00) as recharge");
        Map map6 = walletService.getMap(txJs);
        map.put("integral", map6.get("recharge"));
        return map;
    }

}
