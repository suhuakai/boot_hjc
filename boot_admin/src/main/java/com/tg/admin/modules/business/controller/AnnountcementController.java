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

import com.tg.admin.modules.business.entity.AnnountcementEntity;
import com.tg.admin.modules.business.service.AnnountcementService;
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
@RequestMapping("business/annountcement")
public class AnnountcementController {
    @Autowired
    private AnnountcementService annountcementService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:annountcement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = annountcementService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        AnnountcementEntity annountcement = annountcementService.getById(id);
        return R.ok().put("annountcement", annountcement);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:annountcement:save")
    public R save(@RequestBody AnnountcementEntity annountcement){
        annountcementService.save(annountcement);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:annountcement:update")
    public R update(@RequestBody AnnountcementEntity annountcement){
        ValidatorUtils.validateEntity(annountcement);
        annountcementService.updateById(annountcement);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:annountcement:delete")
    public R delete(@RequestBody Integer[] ids){
        annountcementService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
