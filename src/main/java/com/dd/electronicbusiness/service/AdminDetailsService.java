package com.dd.electronicbusiness.service;

import com.dd.electronicbusiness.dao.AdminMapper;
import com.dd.electronicbusiness.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("未找到用户名为 " + username + " 的管理员");
        }
        System.out.println("从数据库查询到的密码是: " + admin.getPassword());
        System.out.println("==============================");
        return new User(admin.getUsername(), admin.getPassword(), Collections.emptyList());
    }
}