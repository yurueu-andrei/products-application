package by.clevertec.cheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChequeItem {
    private float quantity;
    private String itemName;
    private float price;
    private float total;
}
