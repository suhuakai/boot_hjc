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

import com.tg.admin.modules.business.entity.AddressPrestoreEntity;
import com.tg.admin.modules.business.service.AddressPrestoreService;
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
@RequestMapping("business/addressprestore")
public class AddressPrestoreController {
    @Autowired
    private AddressPrestoreService addressPrestoreService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:addressprestore:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = addressPrestoreService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        AddressPrestoreEntity addressPrestore = addressPrestoreService.getById(id);
        return R.ok().put("addressPrestore", addressPrestore);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:addressprestore:save")
    public R save(@RequestBody AddressPrestoreEntity addressPrestore){
        addressPrestoreService.save(addressPrestore);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:addressprestore:update")
    public R update(@RequestBody AddressPrestoreEntity addressPrestore){
        ValidatorUtils.validateEntity(addressPrestore);
        addressPrestoreService.updateById(addressPrestore);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:addressprestore:delete")
    public R delete(@RequestBody Integer[] ids){
        addressPrestoreService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
