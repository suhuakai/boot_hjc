package com.tg.admin.modules.business.controller;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import com.tg.admin.common.qiniuyun.Qiniuyun;
import com.tg.admin.common.validator.ValidatorUtils;
import com.tg.admin.common.constant.ConstantCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.tg.admin.modules.business.entity.BanksEntity;
import com.tg.admin.modules.business.service.BanksService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;
import org.springframework.web.multipart.MultipartFile;


/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:01
 */
@RestController
@RequestMapping("business/banks")
public class BanksController {
    @Autowired
    private BanksService banksService;


    @RequestMapping(value = "/imgUploading", method = RequestMethod.POST)
    public R imgUploading(@RequestParam("file") MultipartFile file) {
        String fileNameNow = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String key = UUID.randomUUID().toString() + fileNameNow;
        try {
            InputStream inputStream = file.getInputStream();
            Qiniuyun.upInput(key, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("失败");
        }
        return R.ok((Object) key);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:banks:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = banksService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        BanksEntity banks = banksService.getById(id);
        return R.ok().put("banks", banks);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:banks:save")
    public R save(@RequestBody BanksEntity banks){
        banksService.save(banks);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:banks:update")
    public R update(@RequestBody BanksEntity banks){
        ValidatorUtils.validateEntity(banks);
        banksService.updateById(banks);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:banks:delete")
    public R delete(@RequestBody Integer[] ids){
        banksService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
