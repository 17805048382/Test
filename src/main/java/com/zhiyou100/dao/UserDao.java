package com.zhiyou100.dao;

import java.util.List;

import com.zhiyou100.model.User;

public interface UserDao {
	
	User selectByName(String name);
	int selectByRole(String name);
	List<String> selectByRoleId (Integer role_id);

}
