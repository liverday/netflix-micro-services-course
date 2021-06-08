package com.liverday.microservices.crud.model.dto;

import com.liverday.microservices.crud.model.AbstractModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("unused")
public class AbstractMessagePayloadDTO<M extends AbstractModel> {
    private String action;
    private M data;
}
