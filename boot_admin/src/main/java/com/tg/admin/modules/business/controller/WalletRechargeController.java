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

import com.tg.admin.modules.business.entity.WalletRechargeEntity;
import com.tg.admin.modules.business.service.WalletRechargeService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 充值
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
@RestController
@RequestMapping("business/walletrecharge")
public class WalletRechargeController {
    @Autowired
    private WalletRechargeService walletRechargeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:walletrecharge:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = walletRechargeService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        WalletRechargeEntity walletRecharge = walletRechargeService.getById(id);
        return R.ok().put("walletRecharge", walletRecharge);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:walletrecharge:save")
    public R save(@RequestBody WalletRechargeEntity walletRecharge){
        walletRechargeService.save(walletRecharge);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:walletrecharge:update")
    public R update(@RequestBody WalletRechargeEntity walletRecharge){
        ValidatorUtils.validateEntity(walletRecharge);
        walletRechargeService.updateById(walletRecharge);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:walletrecharge:delete")
    public R delete(@RequestBody Integer[] ids){
        walletRechargeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
