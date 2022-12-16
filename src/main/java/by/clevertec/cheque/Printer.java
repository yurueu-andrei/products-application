package by.clevertec.cheque;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class Printer {
    public void printHeader() {
        System.out.println("\t\t\t\t\tCASH RECEIPT");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\tSUPERMARKET 123\t\t\t\t\t\t\t\t\t\t\t");
        System.out.println("\ti2, MILKYWAY Galaxy/Earth\t\t\t\t\t\t\t\t");
        System.out.println("\tTEL: +375-25-999-99-99\t\t\t\t\t\t\t\t\t");
        System.out.println("\tCASHIER: Andrei Yurueu\t\t\t\tDATE: " + LocalDate.now() + "\t");
        System.out.println("\t\t\t\t\t\t\tTIME: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS) + "\t\t");
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\tQTY\t\tDESCRIPTION\t\t\t\tPRICE\t\tTOTAL\t\t");
    }

    public void printItem(int quantity, String itemName, float price) {
        System.out.print("\t" + quantity + "\t\t");
        System.out.print(itemName + "\t\t\t\t\t");
        System.out.print(price + "$\t\t");
        System.out.printf("%.2f$", quantity * price);
        System.out.println();
    }

    public void printFooter(float total, float discount) {
        System.out.println("----------------------------------------------------------------------------------------");
        System.out.println("\tTOTAL:\t\t\t\t\t\t" + String.format("%.2f$", total));
        System.out.println("\tDISCOUNT:\t\t\t\t\t" + String.format("%.2f$", discount));
        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t");
        System.out.println("\tTOTAL WITH DISCOUNT:\t\t\t\t" + String.format("%.2f$", total - discount));
        System.out.println("----------------------------------------------------------------------------------------");
    }
}
