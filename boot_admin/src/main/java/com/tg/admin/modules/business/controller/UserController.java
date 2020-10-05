package com.tg.admin.modules.business.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.admin.common.validator.ValidatorUtils;
import com.tg.admin.common.constant.ConstantCode;
import com.tg.admin.modules.business.entity.WalletEntity;
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

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:user:update")
    public R update(@RequestBody UserEntity user) {
        ValidatorUtils.validateEntity(user);
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:user:delete")
    public R delete(@RequestBody Integer[] ids) {
        System.out.print(1111111111);
        userService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }


    /**
     * 激活
     */
    @RequestMapping(name = "/activate")
    @RequiresPermissions("business:user:activate")
    public R activate(@RequestBody Integer[] ids) {
        // ValidatorUtils.validateEntity(user);
        List<Integer> idList = new ArrayList<>();
        for (Integer id : ids) {
            idList.add(id);
        }
        List<UserEntity> userEntityList = userService.list(new QueryWrapper<UserEntity>().in("id", idList));
        for (UserEntity user : userEntityList) {
            //账号金矿池
            WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", user.getId()).eq("wallet_type_id", 4));
            if ("no".equals(user.getIsActivate())) {
                walletEntity.setBalance(walletEntity.getBalance().subtract(new BigDecimal(3)));
                walletService.updateById(walletEntity);
            }
            user.setIsActivate("yes");
            userService.updateById(user);
        }

        return R.ok();
    }

}
