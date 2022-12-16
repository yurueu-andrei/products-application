package by.clevertec.cheque;

public class ChequeRunner {
    public static void main(String[] args) {
        DataStorage dataStorage = new DataStorage();
        Printer printer = new Printer();
        CashMachine cashMachine = new CashMachine(dataStorage, printer);
        cashMachine.printCheque(args);
    }
}
