package ru.clevertec.cheque.service;

import ru.clevertec.cheque.dto.CardDto;
import ru.clevertec.cheque.dto.ProductDto;
import ru.clevertec.cheque.model.Cheque;
import ru.clevertec.cheque.model.ChequeItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Service class for cheques. Uses <b>card</b> and <b>product</b> services
 * to create cheque instance according to given parameters.
 *
 * @author Yuryeu Andrei
 */
@Component
@RequiredArgsConstructor
public class ChequeBuilder {
    /**
     * Product service field
     *
     * @see ProductService
     */
    private final ProductService productService;
    /**
     * Card service field
     *
     * @see CardService
     */
    private final CardService cardService;

    /**
     * Method for creating Cheque out of given requestParams
     *
     * @param requestParams parameters with ID and quantity of products and card ID
     * @return Cheque with products, total price, discount and total price with discount
     * @see Cheque
     */
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

    /**
     * Private method for setting ChequeItems(products) into Cheque
     *
     * @param cheque        cheque to have products set
     * @param requestParams parameters with ID and quantity of products and card ID
     * @see ChequeBuilder#buildCheque(Map requestParams)
     */
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

    /**
     * Private method for finding discount of card(if present)
     *
     * @param requestParams parameters with ID and quantity of products and card ID
     * @return <b>0</b> - in case of absence of discount card, <b>other float</b> - in case of found card
     * @see ChequeBuilder#buildCheque(Map requestParams)
     */
    private float findCardDiscount(Map<String, String> requestParams) {
        for (String key : requestParams.keySet()) {
            if (key.equals("card")) {
                CardDto card = cardService.findById(Long.valueOf(requestParams.get(key)));
                return card.getDiscount();
            }
        }
        return 0;
    }

    /**
     * Private method for finding total for the whole cheque
     *
     * @param items products with their quantity, price, total price and name
     * @return the sum of all product's total sum(quantity * price)
     * @see ChequeBuilder#buildCheque(Map requestParams)
     */
    private float findTotalForCheque(List<ChequeItem> items) {
        float total = 0;
        for (ChequeItem item : items) {
            total += item.getTotal();
        }
        return total;
    }
}
