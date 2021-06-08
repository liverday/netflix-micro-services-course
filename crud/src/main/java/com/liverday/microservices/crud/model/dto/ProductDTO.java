package com.liverday.microservices.crud.model.dto;

import com.liverday.microservices.crud.model.Product;
import lombok.*;
import org.modelmapper.ModelMapper;

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
public class ProductDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 1391232088589559325L;

    private UUID id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Quantity is required")
    @Positive
    private Long quantity;

    @NotNull(message = "Price is required")
    @Positive
    private Double price;
}
