package com.liverday.microservices.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO extends AbstractDTO implements Serializable {
    private static final long serialVersionUID = -4107799032985019238L;

    @NotNull(message = "Nome é obrigatório")
    private String name;

    @NotNull(message = "Usuário é obrigatório")
    private String userName;

    @NotNull(message = "Senha é obrigatório")
    private String password;
}
