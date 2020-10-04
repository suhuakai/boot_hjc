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

import com.tg.admin.modules.business.entity.UserEntity;
import com.tg.admin.modules.business.service.UserService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * LID 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:00
 */
@RestController
@RequestMapping("business/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        UserEntity user = userService.getById(id);
        return R.ok().put("user", user);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:user:save")
    public R save(@RequestBody UserEntity user){
        userService.save(user);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:user:update")
    public R update(@RequestBody UserEntity user){
        ValidatorUtils.validateEntity(user);
        userService.updateById(user);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:user:delete")
    public R delete(@RequestBody Integer[] ids){
        userService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
