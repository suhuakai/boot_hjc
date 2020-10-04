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

import com.tg.admin.modules.business.entity.BalanceEntity;
import com.tg.admin.modules.business.service.BalanceService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:02
 */
@RestController
@RequestMapping("business/balance")
public class BalanceController {
    @Autowired
    private BalanceService balanceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:balance:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = balanceService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{balanceId}")
    public R info(@PathVariable("balanceId") Integer balanceId){
        BalanceEntity balance = balanceService.getById(balanceId);
        return R.ok().put("balance", balance);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:balance:save")
    public R save(@RequestBody BalanceEntity balance){
        balanceService.save(balance);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:balance:update")
    public R update(@RequestBody BalanceEntity balance){
        ValidatorUtils.validateEntity(balance);
        balanceService.updateById(balance);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:balance:delete")
    public R delete(@RequestBody Integer[] balanceIds){
        balanceService.removeByIds(Arrays.asList(balanceIds));
        return R.ok();
    }

}
