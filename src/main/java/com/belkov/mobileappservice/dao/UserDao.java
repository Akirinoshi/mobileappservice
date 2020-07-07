package com.belkov.mobileappservice.dao;

import com.belkov.mobileappservice.model.User;
import org.springframework.stereotype.Component;

import java.util.UUID;

public interface UserDao {

    User loginUser(User user);

    default User registerUser(User user) {
        UUID id = UUID.randomUUID();
        return user;
    }
}
