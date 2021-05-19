package com.railf.framework.infrastructure.dao.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.railf.framework.domain.User;
import com.railf.framework.infrastructure.dao.mybatis.entity.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/30 14:38
 */
@Mapper
public interface UserDOMapper extends BaseMapper<UserDO> {

    List<UserDO> selectAll();

    void insertAll(@Param("userList") List<User> userList);
}
