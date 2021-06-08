package com.liverday.microservices.payment.services.implementations;

import com.liverday.microservices.payment.exceptions.ResourceNotFoundException;
import com.liverday.microservices.payment.models.Sell;
import com.liverday.microservices.payment.models.SellProduct;
import com.liverday.microservices.payment.models.dto.SellDTO;
import com.liverday.microservices.payment.models.dto.SellProductDTO;
import com.liverday.microservices.payment.repositories.SellProductRepository;
import com.liverday.microservices.payment.repositories.SellRepository;
import com.liverday.microservices.payment.services.ICrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@SuppressWarnings("unused")
public class SellService implements ICrudService<SellDTO, Sell> {

    private final SellRepository sellRepository;
    private final SellProductRepository sellProductRepository;

    @Autowired
    public SellService(SellRepository sellRepository, SellProductRepository sellProductRepository) {
        this.sellRepository = sellRepository;
        this.sellProductRepository = sellProductRepository;
    }

    @Override
    public Sell findById(Object id) throws ResourceNotFoundException {
        Sell sell = sellRepository
                .findById((UUID) id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));

        sell.getProducts().forEach(sellProduct -> sellProduct.setSell(null));
        return sell;
    }

    @Override
    public Page<Sell> findAll(Pageable pageable) {
        return sellRepository
                .findAll(pageable)
                .map(sell -> {
                    sell.getProducts().forEach(sellProduct -> sellProduct.setSell(null));
                    return sell;
                });
    }

    @Override
    public Sell create(SellDTO data) {
        Sell sell = sellRepository.save(SellDTO.create(data, Sell.class));

        List<SellProduct> sellProducts = new ArrayList<>();

        data.getProducts().forEach(sellProductDTO -> {
            SellProduct sellProduct = SellProductDTO.create(sellProductDTO, SellProduct.class);
            sellProduct.setSell((Sell) sell.clone());
            SellProduct savedSellProduct = sellProductRepository.save(sellProduct);
            sellProducts.add(savedSellProduct);
        });

        sell.setProducts(sellProducts.stream().peek(
                savedProduct -> savedProduct.setSell(null)
        ).collect(Collectors.toList()));

        return sell;
    }

    @Override
    public Sell update(SellDTO dto) {
        Optional<Sell> sellToUpdate = sellRepository.findById(dto.getId());

        if (sellToUpdate.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }

        sellProductRepository.deleteAll(sellToUpdate.get().getProducts());

        Sell updatedSell = sellRepository.save(SellDTO.create(dto, Sell.class));

        List<SellProduct> updatedSellProducts = new ArrayList<>();

        dto.getProducts().forEach(sellProductDTO -> {
            SellProduct sellProduct = SellProductDTO.create(sellProductDTO, SellProduct.class);
            sellProduct.setSell((Sell) updatedSell.clone());
            SellProduct savedSellProduct = sellProductRepository.save(sellProduct);
            updatedSellProducts.add(savedSellProduct);
        });

        updatedSell.setProducts(updatedSellProducts.stream().peek(
                updatedProduct -> updatedProduct.setSell(null)
        ).collect(Collectors.toList()));
        return updatedSell;
    }

    @Override
    public void delete(Object id) {
        UUID uuid = (UUID) id;

        Optional<Sell> sellToDelete = sellRepository.findById(uuid);

        if (sellToDelete.isEmpty()) {
            throw new ResourceNotFoundException("Resource not found");
        }

        sellProductRepository.deleteAll(sellToDelete.get().getProducts());
        sellRepository.deleteById(uuid);
    }
}
