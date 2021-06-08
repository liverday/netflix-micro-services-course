package com.liverday.microservices.payment.repositories;

import com.liverday.microservices.payment.models.SellProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@SuppressWarnings("unused")
public interface SellProductRepository extends JpaRepository<SellProduct, UUID> {
}
