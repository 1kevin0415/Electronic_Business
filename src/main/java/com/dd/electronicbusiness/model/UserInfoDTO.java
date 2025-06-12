package com.dd.electronicbusiness.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO {
    private String username;
    private Long id;
    private String avatar;
    // 您可以添加其他字段，如角色、头像等。
}