package com.tg.api.controller;

import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.AnnountcementEntity;
import com.tg.api.service.AnnountcementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-30 14:32:04
 */
@RestController
@RequestMapping("api/annountcement")
public class AnnountcementController {
    @Autowired
    private AnnountcementService annountcementService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = annountcementService.queryPage(params);
        return R.ok().put("page",page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
        AnnountcementEntity annountcement = annountcementService.getById(id);
        return R.ok().put("annountcement", annountcement);

    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AnnountcementEntity annountcement){
        annountcementService.save(annountcement);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AnnountcementEntity annountcement){
        annountcementService.updateById(annountcement);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
        annountcementService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
