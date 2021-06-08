package com.liverday.microservices.payment.exceptions;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse  implements Serializable {
    private static final long serialVersionUID = -4394739992655277839L;

    private Date timestamp;
    private String message;
    private String detail;
}
