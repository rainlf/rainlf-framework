package com.railf.framework.adapter.assembler;

import com.railf.framework.adapter.dto.UserDTO;
import com.railf.framework.domain.User;
import com.railf.framework.infrastructure.mapstrcut.BaseAssembler;
import org.mapstruct.Mapper;

/**
 * @author : rain
 * @date : 2021/4/30 14:29
 */
@Mapper(componentModel = "spring")
public interface UserAssembler extends BaseAssembler<User, UserDTO> {

}
