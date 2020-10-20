package com.tg.api.controller;

import com.tg.api.common.utils.LocalAssert;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.DictionariesEntity;
import com.tg.api.entity.UserEarningsEntity;
import com.tg.api.entity.UserEntity;
import com.tg.api.service.DictionariesService;
import com.tg.api.service.UserEarningsService;
import com.tg.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
@RestController
@RequestMapping("api/userearnings")
public class UserEarningsController extends BaseController {
    @Autowired
    private UserEarningsService userEarningsService;

    @Autowired
    private DictionariesService dictionariesService;

    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("userId", getUserId());
        PageUtils page = userEarningsService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 划转
     *
     * @return
     */
    @RequestMapping("/saveEarnginx")
    public synchronized R saveEarnginx(@RequestBody UserEarningsEntity userEarningsEntity) {
        Integer userId = getUserId();
        if (userId.compareTo(userEarningsEntity.getUpUserId()) == 0) {
            return R.error("不能自己转给自己");
        }
        if (userId.compareTo(1567) != 0) {
            if (userEarningsEntity.getNumber().compareTo(BigDecimal.ZERO) != 1) {
                return R.error("无效金额");
            }
        //    DictionariesEntity dic = dictionariesService.getById(1);
            UserEntity userEntity = userService.getById(userId);
            if (userEntity.getUserVipId() == 4 || userEntity.getUserVipId() == 5) {
                if (userEarningsEntity.getWalletTypeId() != 2) {
                    return R.error("目前只资产划转金卷");
                }
                userEarningsEntity.setUserId(userId);
                userEarningsService.saveEarnginx(userEarningsEntity);
                return R.ok();
            } else {
                return R.error("目前只支持四星千户和五星城主划转");
            }
        } else {
            userEarningsEntity.setUserId(userId);
            userEarningsService.saveEarnginx(userEarningsEntity);
            return R.ok();
        }
    }
}
