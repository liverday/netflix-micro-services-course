package com.liverday.microservices.payment.repositories;

import com.liverday.microservices.payment.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@SuppressWarnings("unused")
public interface ProductRepository extends JpaRepository<Product, UUID> {
}
