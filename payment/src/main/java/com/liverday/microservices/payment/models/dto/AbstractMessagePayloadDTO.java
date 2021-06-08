package com.liverday.microservices.payment.models.dto;

import com.liverday.microservices.payment.models.AbstractModel;
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