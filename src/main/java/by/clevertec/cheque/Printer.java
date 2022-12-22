package by.clevertec.cheque;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class Printer {
    private final FileManager fileManager;

    public Printer(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public void printHeader() {
        List<String> header = List.of(
                "\t\t\t\t\t\t\t\t\tCASH RECEIPT",
                "----------------------------------------------------------------------------------------",
                "\tSUPERMARKET 123\t\t\t\t\t\t\t\t\t\t\t",
                "\ti2, MILKYWAY Galaxy/Earth\t\t\t\t\t\t\t\t",
                "\tTEL: +375-25-999-99-99\t\t\t\t\t\t\t\t\t",
                "\tCASHIER: Andrei Yurueu\t\t\t\tDATE: " + LocalDate.now() + "\t",
                "\t\t\t\t\t\t\t\t\t\tTIME: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "\t\t",
                "----------------------------------------------------------------------------------------",
                "\tQTY\t\tDESCRIPTION\t\t\t\tPRICE\t\tTOTAL\t\t"
        );
        fileManager.writeChequeToFile(header);
    }

    public void printItem(int quantity, String itemName, float price) {
        fileManager.writeItemToFile(
                "\t" + quantity + "\t\t"
                        + itemName + "\t\t\t\t\t"
                        + price + "$\t\t"
                        + String.format("%.2f$\n", quantity * price));
    }

    public void printFooter(float total, float discount) {
        List<String> footer = List.of(
                "----------------------------------------------------------------------------------------",
                "\tTOTAL:\t\t\t\t\t\t" + String.format("%.2f$", total),
                "\tDISCOUNT:\t\t\t\t\t" + String.format("%.2f$", discount),
                "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t",
                "\tTOTAL WITH DISCOUNT:\t\t" + String.format("%.2f$", total - discount),
                "----------------------------------------------------------------------------------------"
        );
        fileManager.writeChequeToFile(footer);
    }
}
