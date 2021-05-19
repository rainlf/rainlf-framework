package com.railf.framework.domain;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author : rain
 * @date : 2021/4/30 14:28
 */
@Data
public class User {
    private Integer id;

    @NotNull
    @Size(min = 4)
    private String name;

    @Email
    private String email;
}
