package com.tg.api.controller;

import com.tg.api.common.utils.LocalAssert;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.service.UserVipDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
@RestController
@RequestMapping("api/uservipdetail")
public class UserVipDetailController extends BaseController {
    @Autowired
    private UserVipDetailService userVipDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("userId",getUserId());
        PageUtils page = userVipDetailService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 购买升级
     *
     * @param userId
     * @param vipId
     * @return
     */
    @RequestMapping("/buyVip")
    public synchronized R buyVip(Integer userId, Integer vipId) {
        LocalAssert.notNull(vipId, "等级ID不能为空");
        userVipDetailService.buyVip(getUserId(), vipId);
        return R.ok();
    }


}
