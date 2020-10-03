package com.tg.api.controller;

import com.tg.api.common.bitcoin.BitWallet;
import com.tg.api.common.bitcoin.BitcoinWalletUtils;
import com.tg.api.common.constant.ConstantCache;
import com.tg.api.common.constant.ConstantConfig;
import com.tg.api.common.exception.RRException;
import com.tg.api.common.redis.RedisConfigService;
import com.tg.api.common.utils.LocalAssert;
import com.tg.api.common.utils.Md5;
import com.tg.api.common.utils.R;
import com.tg.api.entity.UserEntity;
import com.tg.api.service.SequenceService;
import com.tg.api.service.UserService;
import com.tg.api.vo.UserVo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/login")
@Log4j2
public class LoginController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    RedisConfigService redisConfigService;

    @Autowired
    SequenceService sequenceService;

    /**
     * 登录接口
     *
     * @return UserInfoVo
     */
    @RequestMapping(path = "ajaxLogin", produces = "application/json;charset=utf-8")
    public R ajaxLogin(String id, String password, HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (StringUtils.isBlank(id)) {
            throw new RRException("矿工助记词不能为空");
        }
        if (StringUtils.isBlank(password)) {
            throw new RRException("密码不能为空");
        }
        if (!session.isNew()) {
            response.setHeader(ConstantConfig.HTTP_SESSION_ID, session.getId());
            log.debug("➧➧➧ 重复：JSESSIONID={}", session.getId());
        }
        log.debug("当前用户会话：JSESSIONID={}", session.getId());

        //检查助记词和密码是否合法
        UserEntity baseUserInfo = userService.findUserInfoByParameter(id, password);
        if (null == baseUserInfo) {
            throw new RRException("助记词或密码错误");
        }
        if (null != baseUserInfo && "no".equals(baseUserInfo.getStatus())) {
            log.error(baseUserInfo.getStatus() + ":账号已经停用");
            throw new RRException("账号已经停用");
        }
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(baseUserInfo, userVo);
        String token = session.getId();
        userVo.setToken(token);
        userVo.setPassword(Md5.MD5(userVo.getPassword()));
        redisConfigService.set(token, id, Long.valueOf(ConstantCache.TOKENEXPIRATION.toString()));
        session.setAttribute(token, baseUserInfo);
        return R.ok(userVo);
    }

    /**
     * 获得组记词
     *
     * @param
     * @return
     */
    @PostMapping(value = "/createWallet")
    public R createWallet() {
        BitWallet bitWallet = BitcoinWalletUtils.createWallet();
        return R.ok((Object) bitWallet.getMnemonic());
    }

    /**
     * 查记助词
     *
     * @return
     */
    @RequestMapping("/getWallet")
    public R getWallet() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(sequenceService.nextval("user_id"));
        BitWallet bitWallet = BitcoinWalletUtils.createWallet();
        userEntity.setMnemonic(bitWallet.getMnemonic());
        userEntity.setDate(LocalDateTime.now());
        //userService.save(userEntity);
        return R.ok(userEntity);
    }

    /**
     * 注册
     * 1,注册，保存用户 ---2 保存钱包信息
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/register")
    public synchronized R register(@RequestBody UserVo userVo) {
        valiData(userVo);
        LocalAssert.notNull(userVo.getId(), "矿工ID不能为空");
        LocalAssert.notNull(userVo.getIdentityCard(), "请输入邀请码");
        //邀请码  用户id
        userService.Register(userVo);
        return R.ok();
    }

    /**
     * 忘记密码
     *
     * @param userVo
     * @return
     */
    @RequestMapping("/rememberMe")
    public R rememberMe(@RequestBody UserVo userVo) {
        valiData(userVo);
        //邀请码  用户id
        UserVo vo = userService.rememberMe(userVo);
        return R.ok();
    }

    /**
     * 校验
     *
     * @param userVo
     */
    public void valiData(UserVo userVo) {

        LocalAssert.notNull(userVo.getName(), "请输入昵称");
        LocalAssert.notNull(userVo.getMnemonic(), "助记词不能为空");
        LocalAssert.notNull(userVo.getPassword(), "设置登录密码不能为空");
        LocalAssert.notNull(userVo.getConfirmPwd(), "确认登录密码不能为空");
        LocalAssert.notEqualsValue(userVo.getPassword(), userVo.getConfirmPwd(), "登录密码与确认登录密码必须一致");
        LocalAssert.notNull(userVo.getDealPassword(), "设置支付密码不能为空");
        LocalAssert.notNull(userVo.getConfirmDealPwd(), "确认支付密码不能为空");
        LocalAssert.notEqualsValue(userVo.getDealPassword(), userVo.getConfirmDealPwd(), "支付密码与确认支付密码必须一致");
        // LocalAssert.notNull(userVo.getIdentityCard(), "邀请码不能为空");
    }

}
