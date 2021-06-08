package com.liverday.microservices.crud.messaging;

public interface IMessageProducer<T> {
    void sendMessage(T model);
}
