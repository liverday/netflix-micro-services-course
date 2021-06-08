package com.liverday.microservices.payment.services;

import com.liverday.microservices.payment.exceptions.ResourceNotFoundException;
import com.liverday.microservices.payment.models.AbstractModel;
import com.liverday.microservices.payment.models.dto.AbstractDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICrudService<T extends AbstractDTO, M extends AbstractModel> {
    M findById(Object id) throws ResourceNotFoundException;
    Page<M> findAll(Pageable pageable);
    M create(T data);
    M update(T dto);
    void delete(Object id);
}

