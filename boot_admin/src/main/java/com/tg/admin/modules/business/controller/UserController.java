package com.tg.admin.modules.business.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.admin.common.validator.ValidatorUtils;
import com.tg.admin.common.constant.ConstantCode;
import com.tg.admin.modules.business.entity.UserEarningsEntity;
import com.tg.admin.modules.business.entity.WalletEntity;
import com.tg.admin.modules.business.service.UserEarningsService;
import com.tg.admin.modules.business.service.WalletService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.tg.admin.modules.business.entity.UserEntity;
import com.tg.admin.modules.business.service.UserService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;


/**
 * LID
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
@RestController
@RequestMapping("business/user")
@Log4j2
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    WalletService walletService;

    @Autowired
    UserEarningsService userEarningsService;

    @RequestMapping("/statistics")
    public R statistics() {
        return R.ok(userService.statistics());
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        UserEntity user = userService.getById(id);
        return R.ok().put("user", user);

    }

//    @RequestMapping(value = "/abc", method = RequestMethod.POST)
//    @RequiresPermissions("business:user:activate")
//    public R activate(@RequestBody Integer[] ids) {
//        System.out.println("================++++++++++++++++++++++" + ids);
//        List<UserEntity> userEntityList = userService.list(new QueryWrapper<UserEntity>().in("id", Arrays.asList(ids)));
//        for (UserEntity user : userEntityList) {
//            //账号金矿池
//            WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", user.getId()).eq("wallet_type_id", 4));
//            if ("no".equals(user.getIsActivate())) {
//                walletEntity.setBalance(walletEntity.getBalance().subtract(new BigDecimal(3)));
//                walletService.updateById(walletEntity);
//                user.setIsActivate("yes");
//                userService.updateById(user);
//            }
//        }
//        return R.ok();
//    }

    /**
     * 激活
     */
    @RequestMapping(value = "/activate", method = RequestMethod.POST)
    @RequiresPermissions("business:user:activate")
    public R activate(@RequestBody Integer[] ids) {
        List<UserEntity> userEntityList = userService.list(new QueryWrapper<UserEntity>().in("id", Arrays.asList(ids)));
        for (UserEntity user : userEntityList) {

            UserEarningsEntity userEarningsEntity = new UserEarningsEntity();
            userEarningsEntity.setNumber(new BigDecimal("0"));
            userEarningsEntity.setUserId(user.getId());
            userEarningsEntity.setWalletTypeId(2);
            userEarningsEntity.setStatus("operation");
            userEarningsEntity.setType("yes");
            userEarningsEntity.setSettleStatus("yes");
            userEarningsEntity.setNumberZifu("-" + userEarningsEntity.getNumber());
            userEarningsEntity.setContent("系统激活用户消耗金卷：0");
            userEarningsEntity.setDate(LocalDateTime.now());
            userEarningsService.save(userEarningsEntity);

            user.setIsActivate("yes");
            userService.updateById(user);
        }
        return R.ok();
    }

}
