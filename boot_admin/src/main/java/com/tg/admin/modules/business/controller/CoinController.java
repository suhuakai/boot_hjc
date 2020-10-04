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

import com.tg.admin.modules.business.entity.CoinEntity;
import com.tg.admin.modules.business.service.CoinService;
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
@RequestMapping("business/coin")
public class CoinController {
    @Autowired
    private CoinService coinService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:coin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coinService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        CoinEntity coin = coinService.getById(id);
        return R.ok().put("coin", coin);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:coin:save")
    public R save(@RequestBody CoinEntity coin){
        coinService.save(coin);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:coin:update")
    public R update(@RequestBody CoinEntity coin){
        ValidatorUtils.validateEntity(coin);
        coinService.updateById(coin);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:coin:delete")
    public R delete(@RequestBody Integer[] ids){
        coinService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
