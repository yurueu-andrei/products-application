package ru.clevertec.cheque.mapper;

import ru.clevertec.cheque.dto.ProductDto;
import ru.clevertec.cheque.dto.ProductSaveDto;
import ru.clevertec.cheque.model.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * Mapper class for products. Used to convert <b>Entities</b> into <b>DTO</b> operations.
 * @author Yuryeu Andrei
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product fromSaveDto(ProductSaveDto productSaveDto);
    List<ProductDto> toListDto(List<Product> products);
}
