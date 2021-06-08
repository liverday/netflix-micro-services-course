package com.liverday.microservices.crud.model.dto;

import com.liverday.microservices.crud.model.AbstractModel;
import org.modelmapper.ModelMapper;

public abstract class AbstractDTO {

    public static <M extends AbstractModel, T extends AbstractDTO> M create(T dto, Class<M> clazz) {
        return new ModelMapper().map(dto, clazz);
    };
}
