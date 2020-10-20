package com.tg.api.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.AdvertisingUserEntity;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.service.AdvertisingUserService;
import com.tg.api.service.UserEarningsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-08 17:46:13
 */
@RestController
@RequestMapping("api/advertisinguser")
public class AdvertisingUserController extends BaseController {
    @Autowired
    private AdvertisingUserService advertisingUserService;

    @Autowired
    private UserEarningsService userEarningsService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = advertisingUserService.queryPage(params);
        return R.ok().put("page", page);
    }

    /**
     * 预约广告
     */
    @RequestMapping(value = "/saveSubscribe", method = RequestMethod.POST)
    public synchronized R saveSubscribe(@RequestBody AdvertisingUserEntity advertisingUser) {
        if (advertisingUser.getAdvertistingId() == null) {
            return R.error("缺少参数");
        }
        advertisingUser.setUserId(getUserId());
        advertisingUserService.saveSubscribe(advertisingUser);
        return R.ok();
    }


    /**
     * 观看视频
     */
    @RequestMapping(value = "/saveYiWatch", method = RequestMethod.POST)
    public synchronized R saveYiWatch(@RequestBody AdvertisingUserEntity advertisingUser) {
        if (advertisingUser.getId() == null) {
            return R.error("缺少参数");
        }
        advertisingUser.setUserId(getUserId());
        advertisingUserService.saveYiWatch(advertisingUser);
        return R.ok();
    }

    @RequestMapping(value = "/getInfo")
    public R getInfo() {
        Map map = new HashMap();
        QueryWrapper<AdvertisingUserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.notIn("status","subscribe","principalRefunded");
        queryWrapper.select("COALESCE(sum(number),0.00) as recharge");
        queryWrapper.eq("user_id",getUserId());
        map.put("tour",advertisingUserService.getMap(queryWrapper).get("recharge"));
        QueryWrapper<UserEarningsEntity> userEarnings = new QueryWrapper<>();
        userEarnings.in("type","watch","watchRecommend");
        userEarnings.eq("wallet_type_id",1);
        userEarnings.select("COALESCE(sum(number),0.00) as recharge");
        userEarnings.eq("user_id",getUserId());
        map.put("Earnings",userEarningsService.getMap(userEarnings).get("recharge"));
        return R.ok(map);
    }
}
