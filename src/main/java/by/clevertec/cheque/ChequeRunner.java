package by.clevertec.cheque;

public class ChequeRunner {
    public static void main(String[] args) {
        DataStorage dataStorage = new DataStorage();
        FileManager fileManager = new FileManager();
        Printer printer = new Printer(fileManager);
        CashMachine cashMachine = new CashMachine(dataStorage, printer);
        cashMachine.printCheque(fileManager.readArgumentsFromFile(args[0]));
    }
}
