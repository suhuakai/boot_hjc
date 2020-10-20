package com.tg.api.service.impl;

import com.tg.api.common.exception.RRException;
import com.tg.api.common.utils.LocalAssert;
import com.tg.api.controller.UserController;
import com.tg.api.dao.UserVipDetailDao;
import com.tg.api.dao.VipGradeTypeDao;
import com.tg.api.dao.WalletDao;
import com.tg.api.entity.*;
import com.tg.api.service.*;
import com.tg.api.vo.UserVo;
import com.tg.api.vo.WalletEntityVo;
import org.apache.commons.lang3.StringUtils;
import org.bitcoinj.wallet.Wallet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tg.api.common.utils.PageUtils;
import com.tg.api.common.utils.Query;

import com.tg.api.dao.UserDao;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    WalletService walletService;

    @Resource
    WalletDao walletDao;

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

    @Resource
    VipGradeTypeDao vipGradeTypeDao;

    @Autowired
    DictionariesService dictionariesService;


    @Override
    @Transactional
    public void activation(Integer id,Integer userId) {
        DictionariesEntity dic = dictionariesService.getById(1);
        UserEntity userUp = this.getById(id);

        WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>().eq("user_id", userId).eq("wallet_type_id", 2));
        WalletEntity walletLock = walletService.getLock(walletEntity.getId());

        if (walletLock.getBalance().compareTo(dic.getAgencyNumberFee()) < 0) {
            throw new RRException("金卷余额不足");
        }
        walletLock.setBalance(dic.getAgencyNumberFee());
        walletService.reduceWalletBalance(walletLock);

        userUp.setIsActivate("yes");

        UserEarningsEntity userEarningsEntity = new UserEarningsEntity();
        userEarningsEntity.setNumber(new BigDecimal("3"));
        userEarningsEntity.setUserId(userId);
        userEarningsEntity.setWalletTypeId(2);
        userEarningsEntity.setStatus("operation");
        userEarningsEntity.setType("yes");
        userEarningsEntity.setSettleStatus("yes");
        userEarningsEntity.setNumberZifu("-" + userEarningsEntity.getNumber());
        userEarningsEntity.setContent("激活下级uid:"+userUp.getId()+"消耗金卷" + userEarningsEntity.getNumberZifu());
        userEarningsEntity.setDate(LocalDateTime.now());
        userEarningsService.save(userEarningsEntity);

        this.updateById(userUp);

        // 注册收益
        UserEarningsEntity ear = new UserEarningsEntity();
        ear.setDate(LocalDateTime.now());
        ear.setUserId(id);
        ear.setNumber(new BigDecimal("50"));
        ear.setSettleStatus("no");
        ear.setType("register");
        ear.setNumberZifu("+" + ear.getNumber());
        ear.setContent("注册获得奖励银矿池：" + ear.getNumberZifu());
        ear.setWalletTypeId(3);
        userEarningsService.save(ear);

        // 推荐奖励
        UserEarningsEntity earTj = new UserEarningsEntity();
        earTj.setDate(LocalDateTime.now());
        earTj.setUserId(userId);
        earTj.setNumber(new BigDecimal("25"));
        earTj.setSettleStatus("no");
        earTj.setType("recommend");
        earTj.setUpUserId(userId);  //下级
        earTj.setNumberZifu("+" + earTj.getNumber());
        earTj.setContent("邀请好友注册获得奖励银矿池：" + earTj.getNumberZifu());
        earTj.setWalletTypeId(3);

        userEarningsService.save(earTj);

    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<>()
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
        user.setIsActivate("no");
        user.setGrade(userUp.getGrade() + 1);
        user.setGradeUrl(userUp.getGradeUrl() + "-" + user.getId());

        // 初始发钱包
        List<WalletTypeEntity> walletTypeEntityList = walletTypeService.list();
        walletTypeEntityList.forEach(f -> {
            if (f.getId() == 1 || f.getId() == 2) {  //钱包或者余额
                List<CoinEntity> coinEntityList = coinService.list(new QueryWrapper<CoinEntity>().eq("wallet_type_id", f.getId()));
                coinEntityList.forEach(c -> {
//                    AddressPrestoreEntity add = addressPrestoreService.getAddressPrestore(c.getId());
//                    if (add == null) {
//                        throw new RRException("钱包初始失败！");
//                    }
                    WalletEntity walletEntity = new WalletEntity();
                    walletEntity.setAddress("abc");
                    walletEntity.setUserId(user.getId());
                    walletEntity.setBalance(BigDecimal.ZERO);
                    walletEntity.setCoinName(c.getName());
                    walletEntity.setCoinId(c.getId());
                    walletEntity.setWalletTypeId(f.getId());
                    walletService.save(walletEntity);

//                    add.setUserId(user.getId());
//                    add.setStatus("no");
//                    addressPrestoreService.updateById(add);
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

    /**
     * 我的个人信息
     *
     * @param userId
     * @return
     */
    @Override
    public Map<String, Object> current(Integer userId) {

        Map<String, Object> rtMap = new HashMap<>();
        UserEntity userEntity = baseMapper.selectById(userId);
        UserVo user = new UserVo();
        BeanUtils.copyProperties(userEntity, user);

        List<WalletEntity> walletEntityVoList = walletDao.selectList(new QueryWrapper<WalletEntity>().eq("user_id", userId));
        walletEntityVoList.forEach(f -> {
            f.setWalletTypeName(walletTypeService.getById(f.getWalletTypeId()).getName());
        });
        rtMap.put("walletList", walletEntityVoList);  //资产明细

        WalletEntity walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>()
                .eq("user_id", userId).eq("wallet_type_id", 2));
        user.setAddress(walletEntity == null ? "" : walletEntity.getAddress());  //地址


        VipGradeTypeEntity vipGradeTypeEntity = vipGradeTypeDao.selectById(user.getUserVipId());
        if (vipGradeTypeEntity == null) {
            user.setVipLevel("一星矿工");
        } else {
            user.setVipLevel(vipGradeTypeEntity.getName());
        }

        user.setRecommendCount(this.count(new QueryWrapper<UserEntity>().gt("grade", user.getGrade()).like("grade_url", user.getGradeUrl())));  //推荐人数

        QueryWrapper<UserEarningsEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("type", "recommend", "upRecommend");
        queryWrapper.select("COALESCE(sum(number),0.00) as recharge");
        queryWrapper.eq("user_id", userId);
        Map map2 = userEarningsService.getMap(queryWrapper);
        user.setEarnings(new BigDecimal(map2.get("recharge") + ""));  //收益
        walletEntity = walletService.getOne(new QueryWrapper<WalletEntity>()
                .eq("user_id", userId).eq("wallet_type_id", 1));
        user.setEarnMoney(walletEntity == null ? new BigDecimal(0) : walletEntity.getBalance());    //余额
        rtMap.put("userVo", user);  //用户信息

        return rtMap;
    }

    /**
     * 我的团队
     *
     * @param map
     * @return
     */
    @Override
    public PageUtils myTeam(Map<String, Object> map) {
        UserEntity user = this.getById(map.get("userId") + "");
        IPage<UserEntity> page;
        if ("1".equals(map.get("grade").toString())) {
            page = this.page(new Query<UserEntity>().getPage(map),
                    new QueryWrapper<UserEntity>().eq("up_user_id", user.getId())
            );
            page.getRecords().forEach(f -> {
                QueryWrapper<UserEarningsEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", user.getId());
                queryWrapper.eq("up_user_id", f.getId());
                queryWrapper.select("COALESCE(sum(number),0.00) as recharge");
                Map map2 = userEarningsService.getMap(queryWrapper);
                f.setEarningsOne(new BigDecimal(map2.get("recharge") + ""));  //收益
            });
        } else {
            page = this.page(
                    new Query<UserEntity>().getPage(map),
                    new QueryWrapper<UserEntity>().eq("grade", user.getGrade() + 2).like("grade_url", user.getGradeUrl())
            );
            page.getRecords().forEach(f -> {
                QueryWrapper<UserEarningsEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("user_id", user.getId());
                queryWrapper.eq("up_user_id", f.getId());
                queryWrapper.select("COALESCE(sum(number),0.00) as recharge");
                Map map2 = userEarningsService.getMap(queryWrapper);
                f.setEarningsOne(new BigDecimal(map2.get("recharge") + ""));  //收益
            });
        }
        return new PageUtils(page);
    }


}
