package com.liverday.microservices.crud.service.implementations;

import com.liverday.microservices.crud.exceptions.ResourceNotFoundException;
import com.liverday.microservices.crud.messaging.implementations.ProductMessageProducer;
import com.liverday.microservices.crud.model.Product;
import com.liverday.microservices.crud.model.dto.AbstractMessagePayloadDTO;
import com.liverday.microservices.crud.model.dto.ProductDTO;
import com.liverday.microservices.crud.repositories.ProductRepository;
import com.liverday.microservices.crud.service.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService implements ICrudService<ProductDTO, Product> {
    private final ProductRepository productRepository;
    private final ProductMessageProducer productMessageProducer;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductMessageProducer productMessageProducer) {
        this.productRepository = productRepository;
        this.productMessageProducer = productMessageProducer;
    }

    @Override
    public Product findById(Object id) {
        return productRepository
                .findById((UUID) id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
    }

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public Product create(ProductDTO data) {
        Product product = productRepository.save(ProductDTO.create(data, Product.class));

        AbstractMessagePayloadDTO<Product> message = AbstractMessagePayloadDTO
                .<Product>builder()
                .action("CREATE")
                .data(product)
                .build();

        productMessageProducer.sendMessage(message);

        return product;
    }

    @Override
    public Product update(ProductDTO dto) {
        Optional<Product> productToUpdate = productRepository.findById(dto.getId());

        if (productToUpdate.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }

        Product updatedProduct = productRepository.save(ProductDTO.create(dto, Product.class));

        AbstractMessagePayloadDTO<Product> message = AbstractMessagePayloadDTO
                .<Product>builder()
                .action("UPDATE")
                .data(updatedProduct)
                .build();

        productMessageProducer.sendMessage(message);

        return updatedProduct;
    }

    @Override
    public void delete(Object id) {
        UUID uuid = (UUID) id;

        Optional<Product> productToDelete = productRepository.findById(uuid);

        if (productToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }

        productRepository.deleteById(uuid);

        AbstractMessagePayloadDTO<Product> message = AbstractMessagePayloadDTO
                .<Product>builder()
                .action("DELETE")
                .data(productToDelete.get())
                .build();

        productMessageProducer.sendMessage(message);
    }
}
