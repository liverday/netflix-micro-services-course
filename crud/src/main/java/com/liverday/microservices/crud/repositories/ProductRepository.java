package com.liverday.microservices.crud.repositories;

import com.liverday.microservices.crud.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@SuppressWarnings("unused")
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
