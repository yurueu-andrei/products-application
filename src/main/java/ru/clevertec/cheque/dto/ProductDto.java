package ru.clevertec.cheque.dto;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class ProductDto {
    private final Long id;
    private final String name;
    private final Float price;
    private final boolean onSale;
    @Pattern(regexp = "^([0-9]{3})[A-Z]{3}[0-9]{2}[A-Z]{2}")
    private final String barcode;
}
