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

import com.tg.admin.modules.business.entity.SigninEntity;
import com.tg.admin.modules.business.service.SigninService;
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
@RequestMapping("business/signin")
public class SigninController {
    @Autowired
    private SigninService signinService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:signin:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = signinService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{signId}")
    public R info(@PathVariable("signId") Integer signId){
        SigninEntity signin = signinService.getById(signId);
        return R.ok().put("signin", signin);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:signin:save")
    public R save(@RequestBody SigninEntity signin){
        signinService.save(signin);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:signin:update")
    public R update(@RequestBody SigninEntity signin){
        ValidatorUtils.validateEntity(signin);
        signinService.updateById(signin);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:signin:delete")
    public R delete(@RequestBody Integer[] signIds){
        signinService.removeByIds(Arrays.asList(signIds));
        return R.ok();
    }

}
