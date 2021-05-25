package com.railf.framework.infrastructure.dao.converter;

import com.railf.framework.domain.User;
import com.railf.framework.infrastructure.dao.entity.UserDO;
import com.railf.framework.infrastructure.mapstrcut.BaseConverter;
import org.mapstruct.Mapper;

/**
 * @author : rain
 * @date : 2021/4/30 14:36
 */
@Mapper(componentModel = "spring")
public interface UserConverter extends BaseConverter<User, UserDO> {

}
