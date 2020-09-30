package com.tg.api.controller;

import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.R;
import com.tg.api.entity.VipGradeTypeEntity;
import com.tg.api.service.VipGradeTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-09-29 13:21:15
 */
@RestController
@RequestMapping("api/vipgradetype")
public class VipGradeTypeController {
    @Autowired
    private VipGradeTypeService vipGradeTypeService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = vipGradeTypeService.queryPage(params);
        return R.ok(page);
    }

    /**
     * 列表不分页
     */
    @RequestMapping("/getList")
    public R getList(){
        List<VipGradeTypeEntity> list= vipGradeTypeService.list();
        return R.ok(list);
    }



}
