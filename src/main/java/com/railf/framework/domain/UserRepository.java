package com.railf.framework.domain;


import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/30 14:35
 */
public interface UserRepository {
    User insertUser(User user);

    List<User> selectAllUser();

    List<User> insertUserList(List<User> userList);
}
