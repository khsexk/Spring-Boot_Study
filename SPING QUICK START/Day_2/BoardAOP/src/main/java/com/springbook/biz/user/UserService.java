package com.springbook.biz.user;

import com.springbook.biz.user.impl.UserDAO;

public interface UserService {
	public UserVO getUser(UserVO vo);
	public void setUSerDAO(UserDAO userDAO);
}
