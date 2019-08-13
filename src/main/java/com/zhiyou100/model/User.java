package com.zhiyou100.model;

import lombok.Data;

@Data
public class User {
	
	private Integer id;
	private String username;
	private String password;
	private Integer role_id;

}
