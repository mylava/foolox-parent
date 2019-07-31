package com.foolox.game.web.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.foolox.base.db.domain.Player;
import com.foolox.game.web.jwt.JwtUtils;
import com.foolox.game.web.jwt.ValidateToken;
import com.foolox.game.web.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * comment: 登录拦截器
 *
 * @author: lipengfei
 * @date: 12/05/2019
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private PlayerService playerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod=(HandlerMethod)handler;
        Method method=handlerMethod.getMethod();

        if (method.isAnnotationPresent(ValidateToken.class)) {
            ValidateToken validateToken = method.getAnnotation(ValidateToken.class);
            if (validateToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }
                // 获取 token 中的 clientSessionList id
                String playerId;
                try {
                    playerId = JWT.decode(token).getClaim("id").asString();
                } catch (JWTDecodeException j) {
                    throw new RuntimeException("401");
                }
                Player player = playerService.findPlayerById(playerId);
                if (player == null) {
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                // 验证 token
                Boolean verify = JwtUtils.isVerify(token, player);
                if (!verify) {
                    throw new RuntimeException("非法访问！");
                }
                return true;
            }
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
