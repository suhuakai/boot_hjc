package com.tg.api.controller;

import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.service.WalletMentionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



/**
 * 提币
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@RestController
@RequestMapping("api/walletmention")
public class WalletMentionController {
    @Autowired
    private WalletMentionService walletMentionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = walletMentionService.queryPage(params);
        return R.ok(page);
    }



}
