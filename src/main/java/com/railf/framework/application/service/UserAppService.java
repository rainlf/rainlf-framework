package com.railf.framework.application.service;


import com.railf.framework.domain.User;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/30 14:27
 */
public interface UserAppService {

    User addUser2(@Valid User user);

    List<User> retrieveAllUser();

    List<User> addUserList(List<User> userList);
}
