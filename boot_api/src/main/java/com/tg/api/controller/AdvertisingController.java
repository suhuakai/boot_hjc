package com.tg.api.controller;

import com.tg.api.common.utils.R;
import com.tg.api.entity.AdvertisingEntity;
import com.tg.api.entity.WalletTypeEntity;
import com.tg.api.service.AdvertisingService;
import com.tg.api.service.WalletTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;


/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-08 17:46:13
 */
@RestController
@RequestMapping("api/advertising")
public class AdvertisingController {
    @Autowired
    private AdvertisingService advertisingService;

    @Autowired
    WalletTypeService walletTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list() {
        List<AdvertisingEntity> list = advertisingService.list();
        WalletTypeEntity wallet = walletTypeService.getById(2);
        list.forEach(f -> {
            f.setNumberPrice(f.getNumber());
            f.setNumber(f.getNumber().divide(wallet.getPrice(), 4, BigDecimal.ROUND_DOWN));
        });
        return R.ok(list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id) {
        AdvertisingEntity advertising = advertisingService.getById(id);
        return R.ok().put("advertising", advertising);

    }


}
