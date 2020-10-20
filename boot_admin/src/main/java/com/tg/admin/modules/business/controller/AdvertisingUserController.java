package com.tg.admin.modules.business.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tg.admin.common.validator.ValidatorUtils;
import com.tg.admin.common.constant.ConstantCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.tg.admin.modules.business.entity.AdvertisingUserEntity;
import com.tg.admin.modules.business.service.AdvertisingUserService;
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
@RequestMapping("business/advertisinguser")
public class AdvertisingUserController {
    @Autowired
    private AdvertisingUserService advertisingUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:advertisinguser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = advertisingUserService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        AdvertisingUserEntity advertisingUser = advertisingUserService.getById(id);
        return R.ok().put("advertisingUser", advertisingUser);

    }

    /**
     * 加速
     */
    @RequestMapping(value = "/accelerate",method = RequestMethod.POST)
    @RequiresPermissions("business:advertisingUser:accelerate")
    public synchronized R accelerate(@RequestBody Integer[] ids) {
        Map map = new HashMap();
        String a = "";
        for (int i = 0; i < ids.length; i++) {
            if (advertisingUserService.accelerate(ids[i], 1)) {
                a = a + ids[i];
            }
        }
        map.put("id",a);
        return R.ok(map);
    }

    /**
     * 通过
     */
    @RequestMapping(value = "/audit",method = RequestMethod.POST)
    @RequiresPermissions("business:advertisingUser:audit")
    public synchronized R audit(@RequestBody Integer[] ids) {
        Map map = new HashMap();
        String a = "";
        for (int i = 0; i < ids.length; i++) {
            if (advertisingUserService.audit(ids[i], 1)) {
                a = a + ids[i];
            }
        }
        map.put("id",a);
        return R.ok(map);
    }

}
