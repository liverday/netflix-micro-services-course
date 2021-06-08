package com.liverday.microservices.payment.models.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 287535758752671288L;

    private UUID id;

    @NotNull(message = "Quantity is required")
    @Positive
    private Long quantity;
}
