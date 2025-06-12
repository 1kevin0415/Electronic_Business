package com.dd.electronicbusiness.controller;

import com.dd.electronicbusiness.dao.AdminMapper;
import com.dd.electronicbusiness.config.JwtTokenProvider;
import com.dd.electronicbusiness.model.Admin;
import com.dd.electronicbusiness.model.ApiResponse;
import com.dd.electronicbusiness.model.LoginRequest;
import com.dd.electronicbusiness.model.UserInfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AdminMapper adminMapper;

    // 添加一个日志记录器，这比 System.out.println 更标准、更强大
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @PostMapping("/login")
    public ApiResponse<?> authenticateAdmin(@RequestBody LoginRequest loginRequest) {
        System.out.println("=========== DEBUG INFO ===========");
        System.out.println("前端传来的用户名是: " + loginRequest.getUsername());
        System.out.println("前端传来的密码是: " + loginRequest.getPassword());

        try {
            // 第一步：进行密码验证
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            System.out.println("密码验证成功！准备生成JWT Token...");

            // 第二步：如果验证成功，将认证信息存入安全上下文
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 第三步：生成JWT Token
            String jwt = tokenProvider.generateToken(authentication);
            System.out.println("JWT Token 生成成功！准备返回给前端。");

            // 第四步：返回包含Token的成功响应
            return ApiResponse.success(Map.of("token", jwt));

        } catch (Exception e) {
            // 如果在以上任何一步（特别是生成Token时）发生异常，这里的代码会执行
            logger.error("!!! 登录过程中发生未知异常 !!!", e); // 关键：打印出完整的错误堆栈信息
            // 并返回一个包含错误信息的响应给前端
            return ApiResponse.error(500, "登录时发生内部错误: " + e.getMessage());
        }
    }

    /**
     * 获取当前登录的管理员信息。
     * 此方法现在会从数据库查询完整的用户信息，并返回包含id、username和avatar的DTO。
     */
    @GetMapping("/info")
    public ApiResponse<UserInfoDTO> getAdminInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // 从数据库获取更完整的 Admin 信息
        Admin admin = adminMapper.findByUsername(username);
        if (admin == null) {
            return ApiResponse.error(404, "用户不存在");
        }

        // 填充 DTO
        UserInfoDTO userInfo = new UserInfoDTO();
        userInfo.setId(admin.getId());
        userInfo.setUsername(admin.getUsername());
        // 暂时返回一个默认头像，您可以根据需要修改
        userInfo.setAvatar("https://example.com/avatar.png");

        return ApiResponse.success(userInfo);
    }

    @PostMapping("/logout")
    public ApiResponse<?> logoutAdmin() {
        // 对于无状态的 JWT，登出主要由客户端通过清除 token 来处理。
        // 如果需要，此端点可用于日志记录或其他服务器端清理工作。
        SecurityContextHolder.clearContext();
        return ApiResponse.success(Map.of("message", "登出成功"));
    }
}