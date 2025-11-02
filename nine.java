#File Handling in Java

#File Operations
code:
import java.io.*;
import java.util.Scanner;
public class FileOperations {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;
        System.out.println("URK24CS6006 - PUJITHA");
        do {
            System.out.println("\n==== FILE OPERATIONS MENU ====");
            System.out.println("1. Create a New File");
            System.out.println("2. Rename a File");
            System.out.println("3. Delete a File");
            System.out.println("4. Create a Directory");
            System.out.println("5. Find Absolute Path of a File");
            System.out.println("6. Display All Files in a Directory");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Enter file name to create: ");
                    String fileName = sc.nextLine();
                    try {
                        File f = new File(fileName);
                        if (f.createNewFile())
                            System.out.println("File created: " + f.getName());
                        else
                            System.out.println("File already exists!");
                    } catch (IOException e) {
                        System.out.println("Error creating file!");
                    }
                    break;

                case 2:
                    System.out.print("Enter existing file name: ");
                    String oldName = sc.nextLine();
                    System.out.print("Enter new file name: ");
                    String newName = sc.nextLine();
                    File oldFile = new File(oldName);
                    File newFile = new File(newName);
                    if (oldFile.renameTo(newFile))
                        System.out.println("File renamed successfully.");
                    else
                        System.out.println("Rename failed.");
                    break;

                case 3:
                    System.out.print("Enter file name to delete: ");
                    String delName = sc.nextLine();
                    File delFile = new File(delName);
                    if (delFile.delete())
                        System.out.println("File deleted successfully.");
                    else
                        System.out.println("File not found or unable to delete.");
                    break;

                case 4:
                    System.out.print("Enter directory name: ");
                    String dirName = sc.nextLine();
                    File dir = new File(dirName);
                    if (dir.mkdir())
                        System.out.println("Directory created successfully.");
                    else
                        System.out.println("Directory already exists or failed to create.");
                    break;

                case 5:
                    System.out.print("Enter file name: ");
                    String absName = sc.nextLine();
                    File absFile = new File(absName);
                    System.out.println("Absolute Path: " + absFile.getAbsolutePath());
                    break;

                case 6:
                    System.out.print("Enter directory path: ");
                    String path = sc.nextLine();
                    File directory = new File(path);
                    if (directory.isDirectory()) {
                        System.out.println("Files in " + path + ":");
                        String[] files = directory.list();
                        if (files != null) {
                            for (String file : files)
                                System.out.println(file);
                        }
                    } else {
                        System.out.println("Not a valid directory!");
                    }
                    break;

                case 7:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 7);
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
