package com.dd.electronicbusiness.dao;

import com.dd.electronicbusiness.model.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminMapper {
    Admin findByUsername(@Param("username") String username);
}