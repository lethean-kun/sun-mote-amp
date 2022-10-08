package com.sunmote.service;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.domain.User;

public interface UserService {

    User getUserByUsername(String username);

    User getUserById(Long id);

    void create(User user);

    void update(Long userId, User user);

    PageResult<User> readUserList(QueryPageBean queryPageBean);

}
