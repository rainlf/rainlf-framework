package com.railf.framework.infrastructure.repositoryimpl;


import com.railf.framework.domain.User;
import com.railf.framework.domain.UserRepository;
import com.railf.framework.infrastructure.dao.entity.UserDO;
import com.railf.framework.infrastructure.dao.converter.UserConverter;
import com.railf.framework.infrastructure.dao.mapper.UserDOMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/30 14:37
 */
@Slf4j
@Service
public class UserRepositoryImpl implements UserRepository {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserConverter userConverter;

    @Override
    public User insertUser(User user) {
        UserDO userDO = userConverter.toDO(user);
        userDOMapper.insert(userDO);
        return userConverter.toEntity(userDO);
    }

    @Override
    public List<User> selectAllUser() {
        log.info("hello 4");
        return userConverter.toEntity(userDOMapper.selectAll());
    }

    @Override
    public List<User> insertUserList(List<User> userList) {
        List<UserDO> userDOList = userConverter.toDO(userList);
        userDOMapper.insertAll(userList);
        return userConverter.toEntity(userDOList);
    }
}
