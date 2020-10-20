package com.tg.api.controller;

import com.tg.api.common.utils.R;
import com.tg.api.entity.DictionariesEntity;
import com.tg.api.service.DictionariesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



/**
 * 
 *
 * @author Amy
 * @email 411382846@qq.com
 * @date 2020-10-12 16:32:34
 */
@RestController
@RequestMapping("api/dictionaries")
public class DictionariesController {
    @Autowired
    private DictionariesService dictionariesService;
    /**
     * 信息
     */
    @RequestMapping("/info")
    public R info(){
        DictionariesEntity dictionaries = dictionariesService.getById(1);
        return R.ok(dictionaries);
    }

}
