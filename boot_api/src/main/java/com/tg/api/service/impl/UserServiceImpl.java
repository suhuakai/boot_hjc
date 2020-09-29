package com.tg.api.service.impl;

import com.tg.api.common.constant.ConstantConfig;
import com.tg.api.common.utils.LocalAssert;
import com.tg.api.controller.UserController;
import com.tg.api.entity.WalletEntity;
import com.tg.api.service.WalletService;
import com.tg.api.vo.UserVo;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserDao;
import com.tg.api.entity.UserEntity;
import com.tg.api.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    WalletService walletService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public UserEntity findUserInfoByParameter(String id, String password) {
        return baseMapper.findUserInfoByParameter(id, password);
    }

    /**
     * 注册
     *
     * @param userVo
     * @retur
     */
    @Override
    public UserVo Register(UserVo userVo) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userVo, user);
        if (userVo.getUpUserId() != null) {  //通过邀请码进来
            user.setUpUserId(userVo.getUpUserId());
            WalletEntity walletEntity = walletService.selectByUserId(userVo.getUpUserId(), ConstantConfig.WalletType.goldPool.getType());
            LocalAssert.notNull(walletEntity, "请确认该用户数据正确");
            walletEntity.setBalance(walletEntity.getBalance().add(new BigDecimal(25)));
            walletService.updateById(walletEntity);
        }

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(ConstantConfig.getUUID());
        walletEntity.setWalletTypeId(ConstantConfig.WalletType.goldPool.getType());
        walletEntity.setUserId(userVo.getId());
        walletEntity.setBalance(new BigDecimal(50));
        walletService.save(walletEntity);

        baseMapper.insert(user);
        return userVo;
    }

    /**
     * 记住密码
     * @param userVo
     * @return
     */
    @Override
    public UserVo rememberMe(UserVo userVo) {
        UserEntity user = baseMapper.selectByMnemonic(userVo.getMnemonic());
        LocalAssert.notNull(user,"请确认助记词是否准确");
        user.setPassword(userVo.getPassword());
        user.setDealPassword(userVo.getDealPassword());
        user.setName(userVo.getName());
        baseMapper.updateById(user);
        return userVo;
    }

    /**
     * 安全设置
     * @param params
     */
    @Override
    public void safeSet(UserController.Params params) {
        UserEntity userEntity = baseMapper.selectById(params.getId());
        if(StringUtils.isNotBlank(params.getOldPwd())){  //登录密码
            LocalAssert.notEqualsValue(userEntity.getPassword(),params.getOldPwd(),"输入得旧密码不匹配");
            LocalAssert.notEqualsValue(params.getNewPwd(),params.getConfirmPwd(),"输入得新密码与确认密码不匹配");
            userEntity.setPassword(params.getNewPwd());
        }
        if(StringUtils.isNotBlank(params.getOldDealPwd())){  //支付密码
            LocalAssert.notEqualsValue(userEntity.getDealPassword(),params.getOldDealPwd(),"输入得旧密码不匹配");
            LocalAssert.notEqualsValue(params.getNewDealPwd(),params.getConfirmDealPwd(),"输入得新密码与支付确认密码不匹配");
            userEntity.setDealPassword(params.getNewDealPwd());
        }
        baseMapper.updateById(userEntity);

    }


}
