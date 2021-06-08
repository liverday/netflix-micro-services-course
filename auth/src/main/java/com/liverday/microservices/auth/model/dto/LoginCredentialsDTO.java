package com.liverday.microservices.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentialsDTO extends AbstractDTO implements Serializable {

    private static final long serialVersionUID = -2790176850008667178L;

    @NotNull(message = "Usuário é obrigatório")
    @NotBlank(message = "Usuário é obrigatório")
    private String userName;

    @NotNull(message = "Usuário é obrigatório")
    @NotBlank(message = "Usuário é obrigatório")
    private String password;
}
