package com.liverday.microservices.payment.controller;

import com.liverday.microservices.payment.models.Sell;
import com.liverday.microservices.payment.models.dto.SellDTO;
import com.liverday.microservices.payment.services.implementations.SellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/sell")
@SuppressWarnings("unused")
public class SellController {
    private final SellService sellService;
    private final PagedResourcesAssembler<Sell> assembler;

    @Autowired
    public SellController(SellService sellService, PagedResourcesAssembler<Sell> assembler) {
        this.sellService = sellService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public Sell findById(@PathVariable("id") UUID id) {
        Sell sell = sellService.findById(id);
        sell.add(linkTo(methodOn(SellController.class).findById(id)).withSelfRel());
        return sell;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "sort", defaultValue = "asc") String sort
    ) {
        var direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "date"));
        Page<Sell> sellPage = sellService.findAll(pageable);
        sellPage
                .stream()
                .forEach(sell -> {
                    sell.add(linkTo(methodOn(SellController.class).findById(sell.getId())).withSelfRel());
                });

        PagedModel<EntityModel<Sell>> pagedModel = assembler.toModel(sellPage);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping
    public Sell create(@RequestBody SellDTO sellDTO) {
        Sell sell = sellService.create(sellDTO);
        sell.add(linkTo(methodOn(SellController.class).findById(sell.getId())).withSelfRel());
        return sell;
    }

    @PutMapping("/{id}")
    public Sell update(@PathVariable("id") UUID id, @RequestBody SellDTO sellDTO) {
        sellDTO.setId(id);
        Sell sell = sellService.update(sellDTO);
        sell.add(linkTo(methodOn(SellController.class).findById(id)).withSelfRel());
        return sell;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        sellService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
