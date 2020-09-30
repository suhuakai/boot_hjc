package com.tg.api.controller;

import com.tg.api.common.redis.RedisConfigService;
import com.tg.api.common.utils.LocalAssert;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.service.UserService;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * LID
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-27 20:25:05
 */
@RestController
@RequestMapping("api/user")
@Log4j2
public class UserController {


    @Autowired
    UserService userService;

    @Autowired
    RedisConfigService redisConfigService;

    /**
     * 安全设置
     *
     * @return
     */
    @RequestMapping("/safeSet")
    public R safeSet(@RequestBody Params params) {
        LocalAssert.notNull(params.getId(), "当前用户ID不能为空");
        userService.safeSet(params);
        return R.ok();
    }

    @Data
    public static class Params {
        private String id;
        private String oldPwd;
        private String newPwd;
        private String confirmPwd;
        private String oldDealPwd;
        private String newDealPwd;
        private String confirmDealPwd;
    }

    /**
     * 我的  当前信息
     *
     * @param userId
     * @return
     */
    @RequestMapping("/current")
    public R current(Integer userId) {
        LocalAssert.notNull(userId, "当前用户ID不能为空");
        Map<String, Object> map = userService.current(userId);
        return R.ok(map);
    }


    /**
     * 我的团队
     * @return
     */
    @RequestMapping("/myTeam")
    public R myTeam(@RequestParam Map<String,Object>map) {
        LocalAssert.notNull(map.get("userId"), "当前用户ID不能为空");
        LocalAssert.notNull(map.get("grade"), "推荐代不能为空");
        PageUtils page = userService.myTeam(map);
        return R.ok(page);
    }




}
