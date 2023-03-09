package ru.clevertec.cheque.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Cheque item(product in cheque) class
 *
 * @author Yuryeu Andrei
 * @see Cheque
 */
@Data
@AllArgsConstructor
public class ChequeItem {
    /**
     * Quantity field
     */
    private float quantity;
    /**
     * Item name field
     */
    private String itemName;
    /**
     * Price field
     */
    private float price;
    /**
     * Total field
     */
    private float total;
}
