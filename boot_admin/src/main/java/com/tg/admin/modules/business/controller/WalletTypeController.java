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

import com.tg.admin.modules.business.entity.WalletTypeEntity;
import com.tg.admin.modules.business.service.WalletTypeService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
@RestController
@RequestMapping("business/wallettype")
public class WalletTypeController {
    @Autowired
    private WalletTypeService walletTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:wallettype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = walletTypeService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        WalletTypeEntity walletType = walletTypeService.getById(id);
        return R.ok().put("walletType", walletType);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:wallettype:save")
    public R save(@RequestBody WalletTypeEntity walletType){
        walletTypeService.save(walletType);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:wallettype:update")
    public R update(@RequestBody WalletTypeEntity walletType){
        ValidatorUtils.validateEntity(walletType);
        walletTypeService.updateById(walletType);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:wallettype:delete")
    public R delete(@RequestBody Integer[] ids){
        walletTypeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
