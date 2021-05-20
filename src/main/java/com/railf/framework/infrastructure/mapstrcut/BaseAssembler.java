package com.railf.framework.infrastructure.mapstrcut;

import org.mapstruct.IterableMapping;
import org.mapstruct.Named;

import java.util.List;

/**
 * @author : rain
 * @date : 2021/5/12 15:35
 */
public interface BaseAssembler<E, DTO> {

    /**
     * dto to entity
     */
    @Named(value = "toEntity")
    E toEntity(DTO dto);

    /**
     * entity to dto
     */
    @Named(value = "toDTO")
    DTO toDTO(E entity);

    /**
     * dto list to entity list
     */
    @IterableMapping(qualifiedByName = "toEntity")
    List<E> toEntity(List<DTO> dtoList);

    /**
     * entity list to dto list
     */
    @IterableMapping(qualifiedByName = "toDTO")
    List<DTO> toDTO(List<E> entityList);
}
