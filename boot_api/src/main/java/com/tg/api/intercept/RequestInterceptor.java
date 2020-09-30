package com.tg.api.intercept;

import com.tg.api.common.constant.ConstantCache;
import com.tg.api.common.exception.RRException;
import com.tg.api.common.redis.RedisConfigService;
import com.tg.api.common.utils.IPUtils;
import com.tg.api.entity.UserEntity;
import com.tg.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class RequestInterceptor implements HandlerInterceptor {

    @Autowired
    RedisConfigService redisConfigService;

    @Autowired
    UserService userService;

    /**
     * sign 签名规则  md5（私钥 + 加密后的字符串）
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

         String ip =   IPUtils.getIpAddr(request);
        String token = request.getHeader(ConstantCache.TOKEN);
        String servletPath = request.getServletPath();
        System.out.println(ip+"=================" +servletPath +"=================");
        if (token == null) {
            throw new RRException("token不能为空");
        }
        boolean b = redisConfigService.exists(token);
        if (!b) {
            throw new RRException("登入过期",ConstantCache.TOKENEXPIRATION);
        }
        Object userId = redisConfigService.get(token);
        UserEntity user = userService.getById(userId.toString());
        if(user == null){
            throw new RRException("账号不存在",ConstantCache.TOKENEXPIRATION);
        }
        if (user.getStatus().equals("no")) {
            throw new RRException("你账号禁止登入,请联系管理员！");
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
