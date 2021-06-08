package com.liverday.microservices.crud.service;

import com.liverday.microservices.crud.exceptions.ResourceNotFoundException;
import com.liverday.microservices.crud.model.AbstractModel;
import com.liverday.microservices.crud.model.dto.AbstractDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICrudService<T extends AbstractDTO, M extends AbstractModel> {
     M findById(Object id) throws ResourceNotFoundException;
     Page<M> findAll(Pageable pageable);
     M create(T data);
     M update(T dto);
     void delete(Object id);
}
