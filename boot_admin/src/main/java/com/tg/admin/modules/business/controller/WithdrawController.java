package com.tg.admin.modules.business.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.tg.admin.common.validator.ValidatorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

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
     * 通过
     */
    @RequestMapping(value = "/audit",method = RequestMethod.POST)
    @RequiresPermissions("business:withdraw:audit")
    public synchronized R audit(@RequestBody Integer[] ids) {
        Map map = new HashMap();
        String a = "";
        for (int i = 0; i < ids.length; i++) {
            if (withdrawService.audit(ids[i], 1)) {
                a = a + ids[i];
            }
        }
        map.put("id",a);
        return R.ok(map);
    }

    /**
     * 失败
     */
    @RequestMapping(value = "/failing",method = RequestMethod.POST)
    @RequiresPermissions("business:withdraw:failing")
    public synchronized R failing(@RequestBody Integer[] ids) {
        Map map = new HashMap();
        String a = "";
        for (int i = 0; i < ids.length; i++) {
            if (withdrawService.audit(ids[i], 2)) {
                a = a + ids[i];
            }
        }
        map.put("id",a);
        return R.ok(map);
    }

}
