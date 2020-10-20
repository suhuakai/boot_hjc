package com.tg.api.controller;

import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.common.validator.Assert;
import com.tg.api.entity.BanksUserEntity;
import com.tg.api.service.BanksUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;


/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-08-24 20:50:22
 */
@RestController
@RequestMapping("api/banksuser")
public class BanksUserController extends BaseController {
    @Autowired
    private BanksUserService banksUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("userId", getUserId());
        PageUtils page = banksUserService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        BanksUserEntity banksUser = banksUserService.getById(id);
        return R.ok().put("banksUser", banksUser);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody BanksUserEntity banksUser){
        Assert.isBlank(banksUser.getBanksType(), "请输入银行名称");
        Assert.isBlank(banksUser.getBanksSonType(), "请输入子行");
        Assert.isBlank(banksUser.getCardNumber(), "请输入卡号");
        Assert.isBlank(banksUser.getName(), "请输入持卡人姓名");
        banksUser.setUserId(getUserId());
        banksUser.setDate(LocalDateTime.now());
        banksUserService.save(banksUser);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody BanksUserEntity banksUser){
        banksUserService.updateById(banksUser);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete/{id}")
    public R delete(@PathVariable("id") Integer id){
        BanksUserEntity banksUser = banksUserService.getById(id);
        if(banksUser.getUserId().equals(getUserId())){
            banksUserService.removeById(id);
        }
        return R.ok();
    }

}
