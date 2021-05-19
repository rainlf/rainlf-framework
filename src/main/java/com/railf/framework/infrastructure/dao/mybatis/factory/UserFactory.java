package com.railf.framework.infrastructure.dao.mybatis.factory;

import com.railf.framework.domain.User;
import com.railf.framework.infrastructure.mapstrcut.BaseFactory;
import com.railf.framework.infrastructure.dao.mybatis.entity.UserDO;
import org.mapstruct.Mapper;

/**
 * @author : rain
 * @date : 2021/4/30 14:36
 */
@Mapper(componentModel = "spring")
public interface UserFactory extends BaseFactory<User, UserDO> {

}
