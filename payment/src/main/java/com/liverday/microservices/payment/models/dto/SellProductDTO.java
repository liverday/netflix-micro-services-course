package com.liverday.microservices.payment.models.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SellProductDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 1126706773151422872L;

    private UUID id;

    @NotBlank(message = "ProductID is required")
    private UUID productId;

    @NotNull(message = "Quantity is required")
    @Positive
    private Long quantity;
}
