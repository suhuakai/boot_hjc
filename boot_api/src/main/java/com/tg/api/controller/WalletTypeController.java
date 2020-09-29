package com.tg.api.controller;

import com.tg.api.service.WalletTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@RestController
@RequestMapping("api/wallettype")
public class WalletTypeController {
    @Autowired
    private WalletTypeService walletTypeService;


}
