package com.tg.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.WalletEntity;
import com.tg.api.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@RestController
@RequestMapping("api/wallet")
public class WalletController extends BaseController {
    @Autowired
    private WalletService walletService;

//    /**
//     * 列表
//     */
//    @RequestMapping("/list")
//    public R list(@RequestParam Map<String, Object> params) {
//        PageUtils page = walletService.queryPage(params);
//        return R.ok(page);
//    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
            WalletEntity wallet = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", getUserId()).eq("wallet_type_id", id));
        return R.ok(wallet);
    }

}
