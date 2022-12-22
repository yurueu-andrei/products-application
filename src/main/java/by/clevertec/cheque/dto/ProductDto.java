package by.clevertec.cheque.dto;

import lombok.Data;

@Data
public class ProductDto {
    private final Long id;
    private final String name;
    private final Float price;
    private final boolean onSale;
}
