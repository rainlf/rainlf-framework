package com.railf.framework.infrastructure.dao.jpa.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author : rain
 * @date : 2021/5/6 13:39
 */
@Data
@Entity
public class TestDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
