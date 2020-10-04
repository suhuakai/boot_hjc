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

import com.tg.admin.modules.business.entity.BanksEntity;
import com.tg.admin.modules.business.service.BanksService;
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
@RequestMapping("business/banks")
public class BanksController {
    @Autowired
    private BanksService banksService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:banks:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = banksService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        BanksEntity banks = banksService.getById(id);
        return R.ok().put("banks", banks);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:banks:save")
    public R save(@RequestBody BanksEntity banks){
        banksService.save(banks);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:banks:update")
    public R update(@RequestBody BanksEntity banks){
        ValidatorUtils.validateEntity(banks);
        banksService.updateById(banks);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:banks:delete")
    public R delete(@RequestBody Integer[] ids){
        banksService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
