package by.clevertec.cheque.controller;

import by.clevertec.cheque.dto.ProductDto;
import by.clevertec.cheque.dto.ProductSaveDto;
import by.clevertec.cheque.model.entity.Product;
import by.clevertec.cheque.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.hibernate.service.spi.ServiceException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
            @RequestBody ProductSaveDto product
    ) throws ServiceException {
        return productService.add(product);
    }

    @PutMapping
    public boolean update(
            @RequestBody ProductDto product
    ) throws ServiceException {
        return productService.update(product);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) throws ServiceException {
        return productService.delete(id);
    }
}
