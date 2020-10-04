package com.tg.admin.modules.business.controller;

import java.util.Arrays;
import java.util.Map;

import com.tg.admin.common.validator.ValidatorUtils;
import com.tg.admin.common.constant.ConstantCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RestController;

import com.tg.admin.modules.business.entity.WalletEntity;
import com.tg.admin.modules.business.service.WalletService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:01
 */
@RestController
@RequestMapping("business/wallet")
public class WalletController {
    @Autowired
    private WalletService walletService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:wallet:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = walletService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        WalletEntity wallet = walletService.getById(id);
        return R.ok().put("wallet", wallet);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:wallet:save")
    public R save(@RequestBody WalletEntity wallet){
        walletService.save(wallet);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:wallet:update")
    public R update(@RequestBody WalletEntity wallet){
        ValidatorUtils.validateEntity(wallet);
        walletService.updateById(wallet);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:wallet:delete")
    public R delete(@RequestBody Integer[] ids){
        walletService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
