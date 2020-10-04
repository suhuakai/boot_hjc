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

import com.tg.admin.modules.business.entity.SequenceEntity;
import com.tg.admin.modules.business.service.SequenceService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:02
 */
@RestController
@RequestMapping("business/sequence")
public class SequenceController {
    @Autowired
    private SequenceService sequenceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:sequence:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sequenceService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{seqName}")
    public R info(@PathVariable("seqName") String seqName){
        SequenceEntity sequence = sequenceService.getById(seqName);
        return R.ok().put("sequence", sequence);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:sequence:save")
    public R save(@RequestBody SequenceEntity sequence){
        sequenceService.save(sequence);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:sequence:update")
    public R update(@RequestBody SequenceEntity sequence){
        ValidatorUtils.validateEntity(sequence);
        sequenceService.updateById(sequence);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:sequence:delete")
    public R delete(@RequestBody String[] seqNames){
        sequenceService.removeByIds(Arrays.asList(seqNames));
        return R.ok();
    }

}
