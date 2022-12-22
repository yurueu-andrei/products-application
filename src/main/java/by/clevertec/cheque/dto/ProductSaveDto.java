package by.clevertec.cheque.dto;

import lombok.Data;

@Data
public class ProductSaveDto {
    private final String name;
    private final Float price;
    private final boolean onSale;
}
