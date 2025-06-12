package com.dd.electronicbusiness.model;

import lombok.Data;
import java.io.Serializable;

@Data
public class Admin implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String username;
    private String password;
}