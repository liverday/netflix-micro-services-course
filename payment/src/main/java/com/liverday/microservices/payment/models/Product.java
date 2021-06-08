package com.liverday.microservices.payment.models;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends AbstractModel implements Serializable  {
    private static final long serialVersionUID = -6200017633230658281L;

    @Id
    private UUID id;

    @Column(name = "quantity", nullable = false)
    private Long quantity;
}

