package com.liverday.microservices.auth.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "permissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Permission extends AbstractModel implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 2365164509599903679L;

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "description", nullable = false)
    private String description;

    @Override
    public String getAuthority() {
        return description;
    }
}
