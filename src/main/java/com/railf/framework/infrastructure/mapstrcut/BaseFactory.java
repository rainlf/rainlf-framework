package com.railf.framework.infrastructure.mapstrcut;

import java.util.List;

/**
 * @author : rain
 * @date : 2021/5/12 15:35
 */
public interface BaseFactory<E, DO> {

    /**
     * do to entity
     */
    E toEntity(DO DO);

    /**
     * entity to do
     */
    DO toDO(E entity);

    /**
     * do list to entity list
     */
    List<E> toEntity(List<DO> DOList);

    /**
     * entity list to do list
     */
    List<DO> toDO(List<E> entityList);
}
