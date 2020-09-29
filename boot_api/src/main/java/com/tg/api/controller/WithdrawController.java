package com.tg.api.controller;

import com.tg.api.common.utils.LocalAssert;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.WithdrawEntity;
import com.tg.api.service.WithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
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
@RequestMapping("api/withdraw")
public class WithdrawController {
    @Autowired
    private WithdrawService withdrawService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = withdrawService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 提交审核
     * @return
     */
    @RequestMapping("/insert")
    public R insert(@RequestBody WithdrawEntity withdrawEntity){
        LocalAssert.notNull(withdrawEntity.getUserId(),"用户ID不能为空");
        LocalAssert.notNull(withdrawEntity.getAccount(),"卡号不能为空");
        LocalAssert.notNull(withdrawEntity.getType(),"操作行为不能为空");
        LocalAssert.notNull(withdrawEntity.getUrl(),"图片路径不能为空");
        LocalAssert.notNull(withdrawEntity.getRealityNumber(),"充值金额不能为空");
        LocalAssert.notNull(withdrawEntity.getUseType(),"使用类型不能为空");
       WithdrawEntity with =  withdrawService.insert(withdrawEntity);
       return R.ok();

    }


}
