package com.railf.framework.adapter.facade;


import com.railf.framework.adapter.assembler.UserAssembler;
import com.railf.framework.adapter.dto.APIResponse;
import com.railf.framework.adapter.dto.UserDTO;
import com.railf.framework.application.service.UserAppService;
import com.railf.framework.domain.User;
import com.railf.framework.infrastructure.aop.annotation.TimeCost;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author : rain
 * @date : 2021/4/29 18:58
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserApi {

    @Autowired
    private UserAppService userAppService;

    @Autowired
    private UserAssembler userAssembler;

    @PostMapping("")
    public APIResponse<UserDTO> addUser1(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                log.error("fieldError {}: {}", fieldError.getField(), fieldError.getDefaultMessage());
            }
            log.info("passed here, it will validate failed in addUser2()");
        }
        return APIResponse.ok(userAssembler.toDTO(userAppService.addUser2(userAssembler.toEntity(userDTO))));
    }

    @TimeCost
    @GetMapping("")
    public APIResponse<List<UserDTO>> retrieveAllUser() {
        log.info("hello 1");
        return APIResponse.ok(userAssembler.toDTO(userAppService.retrieveAllUser()));
    }

    @PostMapping("/list")
    // valid not work for list, it's not a bean
    public APIResponse<List<UserDTO>> addUserList(@Valid @RequestBody List<UserDTO> userDTOList) {
        List<User> userList = userAssembler.toEntity(userDTOList);
        List<User> result = userAppService.addUserList(userList);
        return APIResponse.ok(userAssembler.toDTO(result));
    }
}
