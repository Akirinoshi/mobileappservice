package com.belkov.mobileappservice.service;

import com.belkov.mobileappservice.dao.UserDao;
import com.belkov.mobileappservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(@Qualifier("userDao") UserDao userDao) {
        this.userDao = userDao;
    }

    public User addUser(User user) { return userDao.registerUser(user); }

    public User loginUser(User user) { return userDao.loginUser(user); }
}
