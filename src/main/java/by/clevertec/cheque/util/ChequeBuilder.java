package by.clevertec.cheque.util;

import by.clevertec.cheque.dto.CardDto;
import by.clevertec.cheque.dto.ProductDto;
import by.clevertec.cheque.model.Cheque;
import by.clevertec.cheque.model.ChequeItem;
import by.clevertec.cheque.model.entity.DiscountCard;
import by.clevertec.cheque.model.entity.Product;
import by.clevertec.cheque.service.CardService;
import by.clevertec.cheque.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChequeBuilder {
    private final ProductService productService;
    private final CardService cardService;

    public Cheque buildCheque(Map<String, String> requestParams) {
        Cheque cheque = new Cheque();
        fillItems(cheque, requestParams);

        float discount = findCardDiscount(requestParams) / 100;
        float total = findTotalForCheque(cheque.getProducts());
        cheque.setTotal(total);

        if (discount != 0) {
            cheque.setDiscount(total * discount);
        } else {
            cheque.setDiscount(0);
        }

        cheque.setTotalWithDiscount(cheque.getTotal() - cheque.getDiscount());
        return cheque;
    }

    private void fillItems(Cheque cheque, Map<String, String> requestParams) {
        for (String key : requestParams.keySet()) {
            if (!key.equals("card")) {
                ProductDto product = productService.findById(Long.valueOf(key));
                int quantity = Integer.parseInt(requestParams.get(key));
                float totalForItem = Integer.parseInt(requestParams.get(key)) * product.getPrice();

                if (quantity > 5 && product.isOnSale()) {
                    totalForItem = totalForItem * 0.9f;
                }

                ChequeItem item = new ChequeItem(
                        quantity,
                        product.getName(),
                        product.getPrice(),
                        totalForItem);

                cheque.getProducts().add(item);
            }
        }
    }

    private float findCardDiscount(Map<String, String> requestParams) {
        for (String key : requestParams.keySet()) {
            if (key.equals("card")) {
                CardDto card = cardService.findById(Long.valueOf(requestParams.get(key)));
                return card.getDiscount();
            }
        }
        return 0;
    }

    private float findTotalForCheque(List<ChequeItem> items) {
        float total = 0;
        for (ChequeItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
