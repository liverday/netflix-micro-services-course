package com.liverday.microservices.auth.repositories;

import com.liverday.microservices.auth.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@SuppressWarnings("unused")
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    @Query("SELECT p From Permission p where p.description = :description")
    Permission findByDescription(@Param("description") String description);
}
