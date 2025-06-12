package com.dd.electronicbusiness.config;

import com.dd.electronicbusiness.service.AdminDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AdminDetailsService adminDetailsService;

    // 添加一个日志记录器
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // --- ↓↓↓ 新增的详细调试日志 ↓↓↓ ---
        logger.info("==================== [JwtAuthenticationFilter] 开始处理请求 ====================");
        logger.info("[JwtAuthenticationFilter] 请求地址: {} {}", request.getMethod(), request.getRequestURI());
        // --- ↑↑↑ ---

        try {
            String jwt = getJwtFromRequest(request);
            logger.info("[JwtAuthenticationFilter] 从请求头中获取到的JWT: {}", jwt);

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                logger.info("[JwtAuthenticationFilter] Token 验证成功!");

                String username = tokenProvider.getUsernameFromToken(jwt);
                logger.info("[JwtAuthenticationFilter] 从 Token 中解析出的用户名: {}", username);

                UserDetails userDetails = adminDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                logger.info("[JwtAuthenticationFilter] 用户 {} 的认证信息已成功设置到 Spring Security 上下文中", username);
            } else {
                logger.warn("[JwtAuthenticationFilter] Token 不存在或验证失败");
            }
        } catch (Exception ex) {
            logger.error("[JwtAuthenticationFilter] 处理请求时发生异常", ex);
        }

        logger.info("==================== [JwtAuthenticationFilter] 请求处理结束 ====================\n");
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        logger.info("[JwtAuthenticationFilter] 获取到的 Authorization 请求头: {}", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}