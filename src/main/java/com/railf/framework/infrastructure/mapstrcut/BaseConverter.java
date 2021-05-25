package com.railf.framework.infrastructure.mapstrcut;

import org.mapstruct.IterableMapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author : rain
 * @date : 2021/5/12 15:35
 */
public interface BaseConverter<E, DO> {

    /**
     * do to entity
     */
    @Named("toEntity")
    E toEntity(DO DO);

    /**
     * entity to do
     */
    @Named("toDO")
    DO toDO(E entity);

    /**
     * do list to entity list
     */
    @IterableMapping(qualifiedByName = "toEntity")
    List<E> toEntity(List<DO> DOList);

    /**
     * entity list to do list
     */
    @IterableMapping(qualifiedByName = "toDO")
    List<DO> toDO(List<E> entityList);
}
