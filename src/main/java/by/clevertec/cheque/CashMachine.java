package by.clevertec.cheque;

public class CashMachine {
    private final DataStorage dataStorage;
    private final Printer printer;
    private float total;
    private float discount;

    public CashMachine(DataStorage dataStorage, Printer printer) {
        this.dataStorage = dataStorage;
        this.printer = printer;
    }

    public void printCheque(String[] args) {
        try {
            boolean hasCard = checkArguments(args);
            printer.printHeader();

            for (int i = 0; i < args.length; i++) {
                if (hasCard && i == args.length - 1) {
                    break;
                }
                String[] idWithQuantity = args[i].split("-");
                Long id = Long.valueOf(idWithQuantity[0]);
                Float price = dataStorage.getPriceList().get(id);
                String itemName = dataStorage.getItems().get(id);
                int quantity = Integer.parseInt(idWithQuantity[1]);
                total += quantity * price;
                if (itemName.contains("!") && quantity > 5) {
                    discount += quantity * price * 0.1;
                }

                printer.printItem(quantity, itemName, price);
            }

            if (hasCard) {
                Long cardId = Long.valueOf(args[args.length - 1].split("-")[1]);
                if (dataStorage.getCardNumbers().contains(cardId)) {
                    discount += total * 0.05;
                } else {
                    printer.printFooter(total, discount);
                    throw new IllegalArgumentException("The card does not exist. Use actual card");
                }
            }
            printer.printFooter(total, discount);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean checkArguments(String[] args) {
        for (String arg : args) {
            if (arg.contains("card")) {
                return true;
            }

            Long id = Long.valueOf(arg.split("-")[0]);
            if (!dataStorage.getItems().containsKey(id)) {
                throw new IllegalArgumentException("One of the following items don't exist. Check the list of items");
            }
        }
        return false;
    }
}
