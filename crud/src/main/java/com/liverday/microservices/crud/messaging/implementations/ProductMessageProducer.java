package com.liverday.microservices.crud.messaging.implementations;

import com.liverday.microservices.crud.messaging.IMessageProducer;
import com.liverday.microservices.crud.model.Product;
import com.liverday.microservices.crud.model.dto.AbstractMessagePayloadDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("unused")
public class ProductMessageProducer implements IMessageProducer<AbstractMessagePayloadDTO<Product>> {
    @Value("${crud.rabbitmq.exchange}")
    String exchange;

    @Value("${crud.rabbitmq.routingkey}")
    String routingKey;

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProductMessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendMessage(AbstractMessagePayloadDTO<Product> model) {
        this.rabbitTemplate.convertAndSend(exchange, routingKey, model);
    }
}
