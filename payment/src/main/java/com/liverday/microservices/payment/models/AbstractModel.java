package com.liverday.microservices.payment.models;

import com.liverday.microservices.payment.models.dto.AbstractDTO;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.RepresentationModel;

public abstract class AbstractModel extends RepresentationModel<AbstractModel> {

    public static <T extends AbstractDTO, M extends AbstractModel> T create(M model, Class<T> clazz) {
        return new ModelMapper().map(model, clazz);
    };
}
