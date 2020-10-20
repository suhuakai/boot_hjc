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

import com.tg.admin.modules.business.entity.AdvertisingEntity;
import com.tg.admin.modules.business.service.AdvertisingService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-11 14:45:40
 */
@RestController
@RequestMapping("business/advertising")
public class AdvertisingController {
    @Autowired
    private AdvertisingService advertisingService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:advertising:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = advertisingService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        AdvertisingEntity advertising = advertisingService.getById(id);
        return R.ok().put("advertising", advertising);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:advertising:save")
    public R save(@RequestBody AdvertisingEntity advertising){
        advertisingService.save(advertising);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:advertising:update")
    public R update(@RequestBody AdvertisingEntity advertising){
        ValidatorUtils.validateEntity(advertising);
        advertisingService.updateById(advertising);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:advertising:delete")
    public R delete(@RequestBody Integer[] ids){
        advertisingService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
