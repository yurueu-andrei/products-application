package ru.clevertec.cheque.controller;

import ru.clevertec.cheque.dto.ProductDto;
import ru.clevertec.cheque.dto.ProductSaveDto;
import ru.clevertec.cheque.service.ProductService;
import ru.clevertec.cheque.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/{id}")
    public ProductDto findById(@PathVariable Long id) throws ServiceException {
        return productService.findById(id);
    }

    @GetMapping
    public List<ProductDto> findAll() throws ServiceException {
        return productService.findAll();
    }

    @PostMapping
    public ProductDto add(
            @Valid @RequestBody ProductSaveDto product
    ) throws ServiceException {
        return productService.add(product);
    }

    @PutMapping
    public boolean update(
            @Valid @RequestBody ProductDto product
    ) throws ServiceException {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return productService.delete(id);
    }
}
