package com.railf.framework.infrastructure.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author : rain
 * @date : 2021/4/30 14:24
 */
@Data
@TableName("user")
public class UserDO {
    @TableId(type = IdType.AUTO)
    @NotNull
    @Min(1)
    private Integer id;

    @NotNull
    @Size(min = 4)
    private String name;

    @Email
    private String email;
}
