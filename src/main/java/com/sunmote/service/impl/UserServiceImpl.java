package com.sunmote.service.impl;

import com.sunmote.common.model.PageResult;
import com.sunmote.common.model.QueryPageBean;
import com.sunmote.dao.UserDAO;
import com.sunmote.domain.User;
import com.sunmote.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(final String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    public User getUserById(final Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    public void create(final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.insert(user);
    }

    @Override
    public void update(final Long userId, final User user) {
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        userDAO.update(userId, user);
    }

    @Override
    public PageResult<User> readUserList(final QueryPageBean queryPageBean) {
        return new PageResult<>(
            userDAO.readUserCount(),
            userDAO.readUserList(queryPageBean)
        );
    }
}
