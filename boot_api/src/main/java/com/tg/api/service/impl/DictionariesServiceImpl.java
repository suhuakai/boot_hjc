package com.tg.api.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;
import com.tg.api.dao.DictionariesDao;
import com.tg.api.entity.DictionariesEntity;
import com.tg.api.service.DictionariesService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("dictionariesService")
public class DictionariesServiceImpl extends ServiceImpl<DictionariesDao, DictionariesEntity> implements DictionariesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<DictionariesEntity> page = this.page(
                new Query<DictionariesEntity>().getPage(params),
                new QueryWrapper<DictionariesEntity>()
        );

        return new PageUtils(page);
    }

}
