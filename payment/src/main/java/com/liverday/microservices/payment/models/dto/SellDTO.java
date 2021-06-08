package com.liverday.microservices.payment.models.dto;

import com.liverday.microservices.payment.models.SellProduct;
import lombok.*;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SellDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = 4045496087462619260L;

    private UUID id;

    @NotNull(message = "A Date is required")
    private Date date;

    @NotEmpty(message = "Products is required for sell")
    private List<SellProductDTO> products;

    @NotNull(message = "Total is required")
    @Positive
    private Double total;
}
