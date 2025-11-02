#File Handling in Java

#File Operations
code:
import java.io.*;
import java.util.*;

public class FileOperationsSimple {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int ch;
        System.out.println("URK24CS6006 - PUJITHA");

        do {
            System.out.println("\n=== FILE MENU ===");
            System.out.println("1.Create File  2.Rename File  3.Delete File");
            System.out.println("4.Create Dir   5.Absolute Path  6.List Files  7.Exit");
            System.out.print("Enter choice: ");
            ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> {
                    System.out.print("File name: ");
                    File f = new File(sc.nextLine());
                    System.out.println(f.createNewFile() ? "File created." : "Already exists!");
                }
                case 2 -> {
                    System.out.print("Old name: ");
                    File oldf = new File(sc.nextLine());
                    System.out.print("New name: ");
                    File newf = new File(sc.nextLine());
                    System.out.println(oldf.renameTo(newf) ? "Renamed." : "Failed.");
                }
                case 3 -> {
                    System.out.print("File to delete: ");
                    File del = new File(sc.nextLine());
                    System.out.println(del.delete() ? "Deleted." : "Not found!");
                }
                case 4 -> {
                    System.out.print("Dir name: ");
                    File d = new File(sc.nextLine());
                    System.out.println(d.mkdir() ? "Directory created." : "Exists or failed.");
                }
                case 5 -> {
                    System.out.print("File name: ");
                    System.out.println("Path: " + new File(sc.nextLine()).getAbsolutePath());
                }
                case 6 -> {
                    System.out.print("Dir path: ");
                    File dir = new File(sc.nextLine());
                    if (dir.isDirectory()) {
                        for (String name : Objects.requireNonNull(dir.list()))
                            System.out.println(name);
                    } else System.out.println("Invalid directory!");
                }
                case 7 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }
        } while (ch != 7);
        sc.close();
    }
}

#character stream 
code:
import java.io.*;
public class TransactionSummaryCharStream {
    public static void main(String[] args) {
        int successCount = 0;
        int failedCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 4) {
                    String status = parts[3].trim();
                    if (status.equalsIgnoreCase("SUCCESS"))
                        successCount++;
                    else if (status.equalsIgnoreCase("FAILED"))
                        failedCount++;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("summary_charstream.txt"))) {
                writer.write("TRANSACTION SUMMARY (CHARACTER STREAM)\n");
                writer.write("---------------------------------------\n");
                writer.write("Total Successful Transactions: " + successCount + "\n");
                writer.write("Total Failed Transactions: " + failedCount + "\n");
                writer.write("Total Transactions: " + (successCount + failedCount));
            }

            System.out.println("✅ Summary written to summary_charstream.txt successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

#and byte stream
code:
import java.io.*;
public class TransactionByteSimple {
    public static void main(String[] args) {
        int success = 0, failed = 0;
        try {
            FileInputStream fin = new FileInputStream("transactions.txt");
            byte[] data = fin.readAllBytes();
            String content = new String(data);
            fin.close();
            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.contains("SUCCESS"))
                    success++;
                else if (line.contains("FAILED"))
                    failed++;
            }
            String summary = "TRANSACTION SUMMARY (BYTE STREAM)\n"
                    + "----------------------------------\n"
                    + "Total Successful Transactions: " + success + "\n"
                    + "Total Failed Transactions: " + failed + "\n"
                    + "Total Transactions: " + (success + failed);
            FileOutputStream fout = new FileOutputStream("summary_bytestream.txt");
            fout.write(summary.getBytes());
            fout.close();
            System.out.println("✅ Summary written to summary_bytestream.txt successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
