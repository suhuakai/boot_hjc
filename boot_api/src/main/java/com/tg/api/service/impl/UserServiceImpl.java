package com.tg.api.service.impl;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.tg.api.common.constant.ConstantConfig;
import com.tg.api.common.exception.RRException;
import com.tg.api.common.utils.LocalAssert;
import com.tg.api.controller.UserController;
import com.tg.api.entity.*;
import com.tg.api.service.*;
import com.tg.api.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.core.Coin;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserDao;
import org.springframework.transaction.annotation.Transactional;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    WalletService walletService;

    @Autowired
    WalletTypeService walletTypeService;

    @Autowired
    CoinService coinService;

    @Autowired
    BalanceService balanceService;

    @Autowired
    UserEarningsService userEarningsService;

    @Autowired
    AddressPrestoreService addressPrestoreService;

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
    @Transactional(rollbackFor = Exception.class)
    public UserVo Register(UserVo userVo) {
        UserEntity user = new UserEntity();
        BeanUtils.copyProperties(userVo, user);
        // 查询上级是否存在
        UserEntity userUp = this.getOne(new QueryWrapper<UserEntity>().eq("Identity_card", user.getIdentityCard()));
        if (userUp == null) {
            throw new RRException("推荐码错误！");
        }
        // 直推加1
        userUp.setRecommendCount(userUp.getRecommendCount() + 1);

        user.setUpUserId(Integer.valueOf(user.getIdentityCard()));
        user.setIdentityCard(user.getId() + "");
        user.setDate(LocalDateTime.now());
      //  user.setUpUserId(userUp.getId());
        user.setGrade(userUp.getGrade() + 1);
        user.setGradeUrl(userUp.getGradeUrl() + "-" + user.getId());

        // 初始发钱包
        List<WalletTypeEntity> walletTypeEntityList = walletTypeService.list();
        walletTypeEntityList.forEach(f -> {
            if (f.getId() == 1 || f.getId() == 2) {  //钱包或者余额
                List<CoinEntity> coinEntityList = coinService.list(new QueryWrapper<CoinEntity>().eq("wallet_type_id", f.getId()));
                coinEntityList.forEach(c -> {
                    AddressPrestoreEntity add = addressPrestoreService.getAddressPrestore(c.getId());
                    if (add == null) {
                        throw new RRException("钱包初始失败！");
                    }
                    WalletEntity walletEntity = new WalletEntity();
                    walletEntity.setAddress(add.getAddress());
                    walletEntity.setUserId(user.getId());
                    walletEntity.setBalance(BigDecimal.ZERO);
                    walletEntity.setCoinName(c.getName());
                    walletEntity.setCoinId(c.getId());
                    walletEntity.setWalletTypeId(f.getId());
                    walletService.save(walletEntity);

                    add.setUserId(user.getId());
                    add.setStatus("no");
                    addressPrestoreService.updateById(add);
                });
            } else {
                WalletEntity walletEntity = new WalletEntity();
                walletEntity.setUserId(user.getId());
                walletEntity.setBalance(BigDecimal.ZERO);
                walletEntity.setWalletTypeId(f.getId());
                walletService.save(walletEntity);
            }
        });
        this.updateById(userUp);
        this.save(user);
        // 注册收益
        UserEarningsEntity ear = new UserEarningsEntity();
        ear.setDate(LocalDateTime.now());
        ear.setUserId(user.getId());
        ear.setNumber(new BigDecimal("50"));
        ear.setSettleStatus("no");
        ear.setType("register");

        BalanceEntity balanceEntity = balanceService.getById(1);
        if (balanceEntity.getBalanceMoney().compareTo(new BigDecimal("50")) < 0) {
            ear.setWalletTypeId(4);
            balanceEntity.setBalanceMoney(balanceEntity.getBalanceMoney().subtract(new BigDecimal(50)));
            balanceService.updateById(balanceEntity);
        } else {
            ear.setWalletTypeId(3);
        }
        userEarningsService.save(ear);

        // 推荐奖励
        UserEarningsEntity earTj = new UserEarningsEntity();
        earTj.setDate(LocalDateTime.now());
        earTj.setUserId(userUp.getId());
        earTj.setNumber(new BigDecimal("25"));
        earTj.setSettleStatus("no");
        earTj.setType("recommend");
        earTj.setUpUserId(user.getId());  //下级

        if (balanceEntity.getBalanceMoney().compareTo(new BigDecimal("25")) < 0) {
            earTj.setWalletTypeId(4);
            balanceEntity.setBalanceMoney(balanceEntity.getBalanceMoney().subtract(new BigDecimal(25)));
            balanceService.updateById(balanceEntity);
        } else {
            earTj.setWalletTypeId(3);
        }
        userEarningsService.save(earTj);
//        if (userVo.getUpUserId() != null) {  //通过邀请码进来
//            user.setUpUserId(userVo.getUpUserId());
//            WalletEntity walletEntity = walletService.selectByUserId(userVo.getUpUserId(), ConstantConfig.WalletType.goldPool.getType());
//            LocalAssert.notNull(walletEntity, "请确认该用户数据正确");
//            walletEntity.setBalance(walletEntity.getBalance().add(new BigDecimal(25)));
//            walletService.updateById(walletEntity);
//        }
//
//        WalletEntity walletEntity = new WalletEntity();
//        walletEntity.setId(ConstantConfig.getUUID());
//        walletEntity.setWalletTypeId(ConstantConfig.WalletType.goldPool.getType());
//        walletEntity.setUserId(userVo.getId());
//        walletEntity.setBalance(new BigDecimal(50));
//        walletService.save(walletEntity);
//
//        baseMapper.insert(user);
        return userVo;
    }

    /**
     * 记住密码
     *
     * @param userVo
     * @return
     */
    @Override
    public UserVo rememberMe(UserVo userVo) {
        UserEntity user = baseMapper.selectByMnemonic(userVo.getMnemonic());
        LocalAssert.notNull(user, "请确认助记词是否准确");
        user.setPassword(userVo.getPassword());
        user.setDealPassword(userVo.getDealPassword());
        user.setName(userVo.getName());
        baseMapper.updateById(user);
        return userVo;
    }

    /**
     * 安全设置
     *
     * @param params
     */
    @Override
    public void safeSet(UserController.Params params) {
        UserEntity userEntity = baseMapper.selectById(params.getId());
        if (StringUtils.isNotBlank(params.getOldPwd())) {  //登录密码
            LocalAssert.notEqualsValue(userEntity.getPassword(), params.getOldPwd(), "输入得旧密码不匹配");
            LocalAssert.notEqualsValue(params.getNewPwd(), params.getConfirmPwd(), "输入得新密码与确认密码不匹配");
            userEntity.setPassword(params.getNewPwd());
        }
        if (StringUtils.isNotBlank(params.getOldDealPwd())) {  //支付密码
            LocalAssert.notEqualsValue(userEntity.getDealPassword(), params.getOldDealPwd(), "输入得旧密码不匹配");
            LocalAssert.notEqualsValue(params.getNewDealPwd(), params.getConfirmDealPwd(), "输入得新密码与支付确认密码不匹配");
            userEntity.setDealPassword(params.getNewDealPwd());
        }
        baseMapper.updateById(userEntity);

    }


}
