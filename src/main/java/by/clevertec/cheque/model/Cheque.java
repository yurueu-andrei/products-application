package by.clevertec.cheque.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Cheque {
    private List<ChequeItem> products = new ArrayList<>();
    private float total;
    private float discount;
    private float totalWithDiscount;
}
