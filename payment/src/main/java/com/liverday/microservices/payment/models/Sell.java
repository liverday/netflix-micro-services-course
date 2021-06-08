package com.liverday.microservices.payment.models;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "sell")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Sell extends AbstractModel implements Serializable {
    private static final long serialVersionUID = 6283768583996475164L;

    @Id
    @GeneratedValue
    private UUID id;

    @DateTimeFormat(pattern = "MM/dd/yyyy")
    private Date date;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sell", cascade = {CascadeType.REFRESH})
    private List<SellProduct> products;

    private Double total;

    @Override
    public Object clone() {
        try {
            return (Sell) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Sell(this.id, this.date, this.products, this.total);
        }
    }
}
