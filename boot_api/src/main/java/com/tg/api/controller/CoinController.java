package com.tg.api.controller;

import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.CoinEntity;
import com.tg.api.service.CoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@RestController
@RequestMapping("api/coin")
public class CoinController {
    @Autowired
    private CoinService coinService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = coinService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        CoinEntity coin = coinService.getById(id);
        return R.ok( coin);
    }
}
