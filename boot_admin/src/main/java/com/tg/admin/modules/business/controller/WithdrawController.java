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

import com.tg.admin.modules.business.entity.WithdrawEntity;
import com.tg.admin.modules.business.service.WithdrawService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:11:59
 */
@RestController
@RequestMapping("business/withdraw")
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:withdraw:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = withdrawService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        WithdrawEntity withdraw = withdrawService.getById(id);
        return R.ok().put("withdraw", withdraw);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:withdraw:save")
    public R save(@RequestBody WithdrawEntity withdraw){
        withdrawService.save(withdraw);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:withdraw:update")
    public R update(@RequestBody WithdrawEntity withdraw){
        ValidatorUtils.validateEntity(withdraw);
        withdrawService.updateById(withdraw);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:withdraw:delete")
    public R delete(@RequestBody Integer[] ids){
        withdrawService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
