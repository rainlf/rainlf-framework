package com.railf.framework.domain;


import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/30 14:33
 */
public interface UserService {

    User addUser3(User user);

    List<User> retrieveAllUser();

    List<User> addUserList(List<User> userList);
}
