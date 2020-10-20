package com.tg.api.controller;

import com.tg.api.common.utils.LocalAssert;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.service.SigninService;
import com.tg.api.vo.SignInVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
@RestController
@RequestMapping("api/signin")
public class SigninController extends BaseController {
    @Autowired
    private SigninService signinService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("signType",params.get("signType"));
        params.put("userId",getUserId());
        PageUtils page = signinService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 签到
     * @return
     */
    @RequestMapping("/clickSign")
    public synchronized R clickSign(Integer userId,Integer signType){
        LocalAssert.notNull(userId,"用户ID不能为空");
        signinService.clickSign(userId,signType);
        return  R.ok();
    }


    /**
     * 签到详情
     * @param userId
     * @param signType
     * @return
     */
    @RequestMapping("/getByUserId")
    public R getByUserId(Integer userId,Integer signType){
        LocalAssert.notNull(userId,"用户ID不能为空");
       Map<String,Object> map =signinService.getByUserId(userId,signType);
        return  R.ok(map);
    }
}
