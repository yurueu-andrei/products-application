package by.clevertec.cheque.mapper;

import by.clevertec.cheque.dto.ProductDto;
import by.clevertec.cheque.dto.ProductSaveDto;
import by.clevertec.cheque.model.entity.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product fromSaveDto(ProductSaveDto productSaveDto);
    List<ProductDto> toDto(List<Product> products);
}
