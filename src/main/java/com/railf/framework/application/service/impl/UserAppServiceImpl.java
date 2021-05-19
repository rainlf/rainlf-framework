package com.railf.framework.application.service.impl;


import com.railf.framework.application.service.UserAppService;
import com.railf.framework.domain.User;
import com.railf.framework.domain.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/30 14:28
 */
@Slf4j
@Service
@Validated
public class UserAppServiceImpl implements UserAppService {
    @Autowired
    private UserService userService;

    @Override
    public User addUser2(@Valid User user) {
        return userService.addUser3(user);
    }

    @Override
    public List<User> retrieveAllUser() {
        log.info("hello 2");
        return userService.retrieveAllUser();
    }

    @Override
    public List<User> addUserList(List<User> userList) {
        return userService.addUserList(userList);
    }
}
