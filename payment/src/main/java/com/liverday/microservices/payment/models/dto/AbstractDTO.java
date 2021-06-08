package com.liverday.microservices.payment.models.dto;

import com.liverday.microservices.payment.models.AbstractModel;
import org.modelmapper.ModelMapper;

public abstract class AbstractDTO {

    public static <M extends AbstractModel, T extends AbstractDTO> M create(T dto, Class<M> clazz) {
        return new ModelMapper().map(dto, clazz);
    };
}
