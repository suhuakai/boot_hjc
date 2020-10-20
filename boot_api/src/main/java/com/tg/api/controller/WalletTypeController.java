package com.tg.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.api.common.utils.R;
import com.tg.api.entity.WalletEntity;
import com.tg.api.entity.WalletTypeEntity;
import com.tg.api.service.WalletTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@RestController
@RequestMapping("api/wallettype")
public class WalletTypeController {
    @Autowired
    private WalletTypeService walletTypeService;

    /**
     * 信息
     */
    @RequestMapping("/info")
    public R info() {
        WalletTypeEntity wallet = walletTypeService.getById(2);
        return R.ok(wallet.getPrice());
    }
}
