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

import com.tg.admin.modules.business.entity.VipGradeTypeEntity;
import com.tg.admin.modules.business.service.VipGradeTypeService;
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
@RequestMapping("business/vipgradetype")
public class VipGradeTypeController {
    @Autowired
    private VipGradeTypeService vipGradeTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:vipgradetype:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = vipGradeTypeService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        VipGradeTypeEntity vipGradeType = vipGradeTypeService.getById(id);
        return R.ok().put("vipGradeType", vipGradeType);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:vipgradetype:save")
    public R save(@RequestBody VipGradeTypeEntity vipGradeType){
        vipGradeTypeService.save(vipGradeType);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:vipgradetype:update")
    public R update(@RequestBody VipGradeTypeEntity vipGradeType){
        ValidatorUtils.validateEntity(vipGradeType);
        vipGradeTypeService.updateById(vipGradeType);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:vipgradetype:delete")
    public R delete(@RequestBody Integer[] ids){
        vipGradeTypeService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
