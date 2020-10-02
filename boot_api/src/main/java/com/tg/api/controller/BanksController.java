package com.tg.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.BanksEntity;
import com.tg.api.service.BanksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
@RestController
@RequestMapping("api/banks")
public class BanksController {
    @Autowired
    private BanksService banksService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = banksService.queryPage(params);
        return R.ok(page);
    }

    @RequestMapping("/info")
    public R info(){
        BanksEntity banksEntity = banksService.getOne(new QueryWrapper<BanksEntity>().eq("status","yes"));
        return R.ok(banksEntity);
    }




}
