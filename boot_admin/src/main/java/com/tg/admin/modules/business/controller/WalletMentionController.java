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

import com.tg.admin.modules.business.entity.WalletMentionEntity;
import com.tg.admin.modules.business.service.WalletMentionService;
import com.tg.admin.common.utils.PageUtils;
import com.tg.admin.common.utils.R;



/**
 * 提币
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-03 20:12:01
 */
@RestController
@RequestMapping("business/walletmention")
public class WalletMentionController {
    @Autowired
    private WalletMentionService walletMentionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("business:walletmention:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = walletMentionService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        WalletMentionEntity walletMention = walletMentionService.getById(id);
        return R.ok().put("walletMention", walletMention);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("business:walletmention:save")
    public R save(@RequestBody WalletMentionEntity walletMention){
        walletMentionService.save(walletMention);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("business:walletmention:update")
    public R update(@RequestBody WalletMentionEntity walletMention){
        ValidatorUtils.validateEntity(walletMention);
        walletMentionService.updateById(walletMention);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("business:walletmention:delete")
    public R delete(@RequestBody Integer[] ids){
        walletMentionService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
