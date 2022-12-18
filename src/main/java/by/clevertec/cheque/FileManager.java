package by.clevertec.cheque;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileManager {

    public String[] readArgumentsFromFile(String fileName) {
        String[] result = new String[0];
        try {
            result = Files.readString(Path.of(fileName)).split(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void writeChequeToFile(List<String> strings) {
        try (OutputStream outStream = new FileOutputStream("cheque.txt", true)) {
            if (!Files.exists(Path.of("cheque.txt"))) {
                Files.createFile(Path.of("cheque.txt"));
            }
            strings.forEach(string -> {
                try {
                    outStream.write((string + "\n").getBytes());
                } catch (IOException e) {
                    System.out.println("Cannot write cheque");
                }
            });
        } catch (IOException e) {
            System.out.println("Cannot open file with name: cheque.txt");
        }
    }

    public void writeItemToFile(String string) {
        try {
            OutputStream outputStream = new FileOutputStream("cheque.txt", true);
            outputStream.write((string + "\n").getBytes());
        } catch (IOException e) {
            System.out.println();
        }
    }
}
