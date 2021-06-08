package com.liverday.microservices.auth.model;

import com.liverday.microservices.auth.model.dto.AbstractDTO;
import org.modelmapper.ModelMapper;

public abstract class AbstractModel {

    public static <T extends AbstractDTO, M extends AbstractModel> T create(M model, Class<T> clazz) {
        return new ModelMapper().map(model, clazz);
    };
}
