package com.liverday.microservices.payment.queue;

import com.liverday.microservices.payment.models.Product;
import com.liverday.microservices.payment.models.dto.AbstractMessagePayloadDTO;
import com.liverday.microservices.payment.repositories.ProductRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class ProductMessageListener {
    private final ProductRepository productRepository;

    @Autowired
    public ProductMessageListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @RabbitListener(queues = {"${payment.rabbitmq.queue}"})
    public void onMessage(@Payload AbstractMessagePayloadDTO<Product> payload) {
        Product product = payload.getData();

        switch (payload.getAction()) {
            case "CREATE":
            case "UPDATE":
                productRepository.save(product);
                break;
            case "DELETE":
                productRepository.deleteById(product.getId());
                break;
        }
    }
}
