package com.railf.framework.infrastructure.dao.jpa.repository;

import com.railf.framework.infrastructure.dao.jpa.entity.TestDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : rain
 * @date : 2021/5/6 13:41
 */
@Repository
public interface TestDORepository extends JpaRepository<TestDO, Long> {
}
