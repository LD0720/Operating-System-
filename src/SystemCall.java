import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SystemCall {
    static Parser parser;

    public static void print(String x) {
        System.out.println(x);
    }


    public static void writeFile(String fileName, String variable, PCB pcb) {
        File file = new File(fileName);
        try {
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
                FileWriter myWriter = new FileWriter(fileName);
                String data = "";
                for (int i = pcb.getLowerBound() + 9; i < pcb.getLowerBound() + 12; i++) {
                    if (Memory.memory[i] != null) {
                        MemoryWord memoryWord = (MemoryWord) Memory.memory[i];
                        if (memoryWord.getKey() == variable) {
                            data = memoryWord.getValue();
                        }
                    }
                }
                myWriter.write(data);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFile(String fileName) throws IOException {
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            String x = scanner.next();
            scanner.close();
            return x;
        } catch (FileNotFoundException e) {
            System.out.println("Failed to read file");
            return null;
            //e.printStackTrace();
        }
    }

    public static String takeInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter a value: ");
        return scanner.nextLine();
    }

    public static String readMemory(PCB pcb, String key) {
        if (pcb.getProcessId() == 1 && main.p1Loaded) {
            for (int i = pcb.getLowerBound() + 9; i < pcb.getLowerBound() + 12; i++) {
                MemoryWord t = (MemoryWord) Memory.memory[i];
                if (t.getKey().equals(key)) {
                    return t.getValue();
                }
            }
        } else if (pcb.getProcessId() == 2 && main.p2Loaded) {
            for (int i = pcb.getLowerBound(); i < pcb.getHigherBound(); i++) {
                MemoryWord t = (MemoryWord) Memory.memory[i];
                if (t.getKey().equals(key)) {
                    return t.getValue();
                }
            }
        } else if (pcb.getProcessId() == 3 && main.p3Loaded) {
            for (int i = pcb.getLowerBound(); i < pcb.getHigherBound(); i++) {
                MemoryWord t = (MemoryWord) Memory.memory[i];
                if (t.getKey().equals(key)) {
                    return t.getValue();
                }
            }
        }
        return null;
    }

    public static void writeMemory(PCB pcb, String key, String value) {
        MemoryWord memoryWord = new MemoryWord(key, value);
        Boolean found = false;
        for (int i = pcb.getLowerBound() + 9; i < pcb.getLowerBound() + 12; i++) {
            if (Memory.memory[i] != null) {
                MemoryWord t = (MemoryWord) Memory.memory[i];
                if (t.getKey().equals(key)) {
                    found = true;
                    ((MemoryWord) Memory.memory[i]).setValue(value);
                }
            }
        }
        if (!found) {
            for (int i = pcb.getLowerBound() + 9; i < pcb.getLowerBound() + 12; i++) {
                if (Memory.memory[i] == null) {
                    Memory.memory[i] = memoryWord;
                }
            }
        }
    }


}