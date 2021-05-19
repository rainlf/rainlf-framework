package com.railf.framework.infrastructure.mapstrcut;

import java.util.List;

/**
 * @author : rain
 * @date : 2021/5/12 15:35
 */
public interface BaseAssembler<E, DTO> {

    /**
     * dto to entity
     */
    E toEntity(DTO dto);

    /**
     * entity to dto
     */
    DTO toDTO(E entity);

    /**
     * dto list to entity list
     */
    List<E> toEntity(List<DTO> dtoList);

    /**
     * entity list to dto list
     */
    List<DTO> toDTO(List<E> entityList);
}
