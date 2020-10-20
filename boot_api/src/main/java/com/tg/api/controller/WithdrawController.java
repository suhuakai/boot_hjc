package com.tg.api.controller;

import com.tg.api.common.exception.RRException;
import com.tg.api.common.utils.LocalAssert;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.WithdrawEntity;
import com.tg.api.service.UserService;
import com.tg.api.service.WithdrawService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;


/**
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
@RestController
@RequestMapping("api/withdraw")
public class WithdrawController extends BaseController {
    @Autowired
    private WithdrawService withdrawService;


    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        params.put("userId",getUserId());
        PageUtils page = withdrawService.queryPage(params);
        return R.ok(page);
    }


    /**
     * 提交审核
     *
     * @return
     */
    @RequestMapping("/insert")
    public R insert(@RequestBody WithdrawEntity withdrawEntity) {
        LocalAssert.notNull(withdrawEntity.getAccount(), "卡号不能为空");
        LocalAssert.notNull(withdrawEntity.getType(), "操作行为不能为空");
        LocalAssert.notNull(withdrawEntity.getUrl(), "图片路径不能为空");
        LocalAssert.notNull(withdrawEntity.getRealityNumber(), "充值金额不能为空");
        LocalAssert.notNull(withdrawEntity.getUseType(), "使用类型不能为空");
        withdrawEntity.setNumber(withdrawEntity.getRealityNumber());
        withdrawEntity.setUserId(getUserId());
        withdrawService.insert(withdrawEntity);
        return R.ok();
    }

    /**
     * 提现
     */
    @RequestMapping(value = "/saveWithdraw", method = RequestMethod.POST)
    public synchronized R saveWithdraw(@RequestBody WithdrawEntity withdraw) {
        Integer userId = getUserId();
        withdraw.setUserId(userId);


        if (withdraw.getNumber() == null || withdraw.getNumber().compareTo(new BigDecimal("50")) < 0) {
            throw new RRException("提现最小金额50！");
        }

        if (withdraw.getNumber().divideAndRemainder(new BigDecimal("50"))[1].compareTo(new BigDecimal("0")) != 0) {
            throw new RRException("提现需要是50的倍数！");
        }

        if (!StringUtils.isNotBlank(withdraw.getDealPassword())) {
            throw new RRException("交易密码不能为空！");
        }
        if (!StringUtils.isNotBlank(withdraw.getAccount())) {
            throw new RRException("提现卡号不能为空！");
        }
        if (!StringUtils.isNotBlank(withdraw.getAccountName())) {
            throw new RRException("提现姓名不能为空！");
        }
        if (!StringUtils.isNotBlank(withdraw.getBanksSonType())) {
            throw new RRException("分行不能为空！");
        }
        if (!StringUtils.isNotBlank(withdraw.getBanksType())) {
            throw new RRException("银行类型不能为空！");
        }
        if (!userService.getById(userId).getDealPassword().equals(withdraw.getDealPassword())) {
            throw new RRException("交易密码错误");
        }
        withdraw.setRealityNumber(withdraw.getNumber());
        withdrawService.saveWithdraw(withdraw);
        return R.ok();
    }

}
