package by.clevertec.cheque;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataStorage {
    private final HashMap<Long, String> items;
    private final HashMap<Long, Float> priceList;
    private final List<Long> cardNumbers;

    public DataStorage() {
        this.items = fillItems();
        this.priceList = fillItemsPrice();
        this.cardNumbers = fillCards();
    }

    private HashMap<Long, String> fillItems() {
        HashMap<Long, String> items = new HashMap<>();
        String[] itemNames = {"Eggs !", "Milk !", "Butter", "Cheese", "Bread !", "Onion", "Tomato", "Potato", "Salt", "Sugar"};
        for (int i = 0; i < itemNames.length; i++) {
            items.put((long) i + 1, itemNames[i]);
        }
        return items;
    }

    private HashMap<Long, Float> fillItemsPrice() {
        HashMap<Long, Float> prices = new HashMap<>();
        Float[] itemsPrice = {2.32f, 1.73f, 3.17f, 2.95f, 1.46f, 4.61f, 3.52f, 0.94f, 1.09f, 2.09f};
        for (int i = 0; i < itemsPrice.length; i++) {
            prices.put((long) i + 1, itemsPrice[i]);
        }
        return prices;
    }

    private List<Long> fillCards() {
        List<Long> cards = new ArrayList<>();
        for (long i = 1000; i < 1051; i++) {
            cards.add(i);
        }
        return cards;
    }

    public HashMap<Long, String> getItems() {
        return items;
    }

    public HashMap<Long, Float> getPriceList() {
        return priceList;
    }

    public List<Long> getCardNumbers() {
        return cardNumbers;
    }
}
