package com.tg.admin.modules.business.controller;

import java.util.Arrays;
import java.util.Map;

import com.tg.admin.common.validator.ValidatorUtils;
import com.tg.admin.common.constant.ConstantCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RestController;

import com.tg.admin.modules.business.entity.UserVipDetailEntity;
import com.tg.admin.modules.business.service.UserVipDetailService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:01
 */
@RestController
@RequestMapping("business/uservipdetail")
public class UserVipDetailController {
    @Autowired
    private UserVipDetailService userVipDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:uservipdetail:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userVipDetailService.queryPage(params);
        return R.ok().put("page",page);
    }

}
