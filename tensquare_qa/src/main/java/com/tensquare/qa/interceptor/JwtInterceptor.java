package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 无论如何都要放行，具体能不能操作还是在具体的操作中去判断。
        // 拦截器只是负责把请求头中包含token的令牌进行解析验证。
        String header = request.getHeader("Authorization");
        if (!StringUtils.isEmpty(header)) {
            // 如果包含Authorization头信息，就进行解析
            // 得到token
            String token = header.substring(7);
            // 对令牌进行解析
            try {
                Claims claims = jwtUtil.parseJWT(token);
                String roles = (String) claims.get("roles");
                if (!StringUtils.isEmpty(roles) || "admin".equals(roles)) {
                    request.setAttribute("claims_admin", token);
                }
                if (!StringUtils.isEmpty(roles) || "user".equals(roles)) {
                    request.setAttribute("claims_user", token);
                }
            } catch (Exception e) {
                throw new RuntimeException("令牌不正确！");
            }
        }
        return true;
    }
}
