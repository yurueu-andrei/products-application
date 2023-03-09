package ru.clevertec.cheque.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Cheque class
 *
 * @author Yuryeu Andrei
 */
@Data
public class Cheque {
    /**
     * Products field
     *
     * @see ChequeItem
     */
    private List<ChequeItem> products = new ArrayList<>();
    /**
     * Total field
     */
    private float total;
    /**
     * Discount field
     */
    private float discount;
    /**
     * Total with discount field
     */
    private float totalWithDiscount;
}
