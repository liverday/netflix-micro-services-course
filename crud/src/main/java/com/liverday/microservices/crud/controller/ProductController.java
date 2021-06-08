package com.liverday.microservices.crud.controller;

import com.liverday.microservices.crud.model.Product;
import com.liverday.microservices.crud.model.dto.ProductDTO;
import com.liverday.microservices.crud.service.implementations.ProductService;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/product")
@SuppressWarnings("unused")
public class ProductController {

    private final ProductService productService;
    private final PagedResourcesAssembler<Product> assembler;

    @Autowired
    public ProductController(ProductService productService, PagedResourcesAssembler<Product> assembler) {
        this.productService = productService;
        this.assembler = assembler;
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable("id") UUID id) {
        Product product = productService.findById(id);
        product.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return product;
    }

    @GetMapping
    public ResponseEntity<?> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "sort", defaultValue = "asc") String sort
    ) {
        var direction = "desc".equalsIgnoreCase(sort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "name"));
        Page<Product> products = productService.findAll(pageable);
        products
            .stream()
            .forEach(product -> {
                product.add(linkTo(methodOn(ProductController.class).findById(product.getId())).withSelfRel());
            });

        PagedModel<EntityModel<Product>> pagedModel = assembler.toModel(products);
        return ResponseEntity.ok(pagedModel);
    }

    @PostMapping
    public Product create(@RequestBody ProductDTO productDTO) {
        Product product = productService.create(productDTO);
        product.add(linkTo(methodOn(ProductController.class).findById(product.getId())).withSelfRel());
        return product;
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable("id") UUID id, @RequestBody ProductDTO productDTO) {
        productDTO.setId(id);
        Product product = productService.update(productDTO);
        product.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
        return product;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") UUID id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
