package com.railf.framework.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/30 14:33
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User addUser3(User user) {
        return userRepository.insertUser(user);
    }

    @Override
    public List<User> retrieveAllUser() {
        log.info("hello 3");
        return userRepository.selectAllUser();
    }

    @Override
    public List<User> addUserList(List<User> userList) {
        return userRepository.insertUserList(userList);
    }
}
