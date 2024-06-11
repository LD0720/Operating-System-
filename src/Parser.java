import java.io.*;

public class Parser {
    public void loadPCB(PCB pcb) {
        /*System.out.println("Loading pcb");
        System.out.println("PCB pID : " + pcb.getProcessId());*/
        Boolean flag = checkPCBloaded(pcb);
        /*System.out.println("flag result " + flag);*/
        if (!flag) {
            if (Memory.memory[0] == null) {
                Memory.memory[0] = pcb.getProcessId();
                /*System.out.println("Memory of 0 : " + Memory.memory[0]);*/
                Memory.memory[1] = pcb.getState();
                Memory.memory[2] = pcb.getPc();
                Memory.memory[3] = pcb.getLowerBound();
            } else if (Memory.memory[4] == null) {
                Memory.memory[4] = pcb.getProcessId();
                /*System.out.println("Memory of 4 : " + Memory.memory[4]);*/
                Memory.memory[5] = pcb.getState();
                Memory.memory[6] = pcb.getPc();
                Memory.memory[7] = pcb.getLowerBound();
            } else {
                Memory.memory[8] = pcb.getProcessId();
                /*System.out.println("Memory of 8 : " + Memory.memory[8]);*/
                Memory.memory[9] = pcb.getState();
                Memory.memory[10] = pcb.getPc();
                Memory.memory[11] = pcb.getLowerBound();
            }
        }
    }

    private Boolean checkPCBloaded(PCB pcb) {
        Boolean flag = false;
        if ((Memory.memory[0] != null && ((int) Memory.memory[0]) != pcb.getProcessId()) && (Memory.memory[4] != null && ((int) Memory.memory[0]) != pcb.getProcessId()) && (Memory.memory[8] != null && ((int) Memory.memory[0]) != pcb.getProcessId())) {
            return true;
        } /*else if (Memory.memory[4]!=null && ((int)Memory.memory[0] )!=pcb.getProcessId()) {
            return true;
        } else if (Memory.memory[8]!=null && ((int)Memory.memory[0] )!=pcb .getProcessId()) {
            return true;
        }*/
        return false;
    }

    public void loadProcess(String fileName, int memoryBoundry) {
        /*System.out.println("loading process");*/
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                Memory.memory[memoryBoundry] = line;
                memoryBoundry++;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void interpeter(PCB pcb) throws IOException {
        /*System.out.println("Interpeting");*/
        String instruction = null;

//       if (pcb.getPc() < pcb.getLowerBound() + 9) {
//            System.out.println("Interpeting pc" + pcb.getPc());
            Object t = Memory.memory[pcb.getPc() + pcb.getLowerBound()];
/*        System.out.println("Instruction pointer : " + (pcb.getPc() + pcb.getLowerBound()));
        System.out.println("Memory[13] " + Memory.memory[13]);
        System.out.println("Instruction t : " + t);*/
            if (t instanceof MemoryWord || t == null || t.equals("null")) {
                instruction = null;
            } else {
                instruction = (String) t;
            }

        //}


        /*if (pcb.getProcessId() == 1 && pcb.getPc() == 7) {
            main.p1State = true;
            main.process1.setState("Finished");
            return;
        } else if (pcb.getProcessId() == 2 && pcb.getPc() == 7) {
            main.p2State = true;
            main.process2.setState("Finished");
            return;
        } else if (pcb.getProcessId() == 3 && pcb.getPc() == 9) {
            main.p3State = true;
            main.process3.setState("Finished");
            return;
        }
        main.updatePCBinMemory(pcb);*/




        /*System.out.println("process " + pcb.getProcessId() + " is Finished");*/
        System.out.println("Ready Queue : " + main.ReadyQueue);
        System.out.println("Blocked Queue: " + main.BlockedQueue);
        System.out.println("userInputBlocked Queue : " + main.userInputBlocked);
        System.out.println("userOutputBlocked Queue : " + main.userOutputBlocked);
        System.out.println("fileBlocked Queue : " + main.fileBlocked);
        System.out.println("Instruction currently executing is : " + instruction);
        System.out.println("Printing Memory data : ");
        System.out.println(Memory.tostring());
        if(instruction == null)
            return;
        String[] command = instruction.split(" ");
        if (command[0].equals("semWait")) {
            switch (command[1]) {
                case "userInput":
                    main.userInput.semWait(pcb, "userInput");
                    break;
                case "userOutput":
                    main.userOutput.semWait(pcb, "userOutput");
                    break;
                case "file":
                    main.file.semWait(pcb, "file");
                    break;
            }
            pcb.setPc(pcb.getPc() + 1);
        } else if (command[0].equals("assign")) {
            if (Memory.memory[pcb.getHigherBound()] == null) {
                String temp = "";
                switch (command[2]) {
                    case "input":
                        temp = SystemCall.takeInput();
                        break;
                    case "readFile":
                        temp = SystemCall.readFile(command[3]);
                        break;

                }
                Memory.memory[pcb.getHigherBound()] = temp;
            } else {
                Boolean found = false;
                for (int i = pcb.getLowerBound() + 9; i < pcb.getLowerBound() + 12; i++) {
                    if (Memory.memory[i] != null) {
                        if (((MemoryWord) Memory.memory[i]).getKey().equals(command[1])) {
                            found = true;
                            assign(command[1], ((MemoryWord) Memory.memory[i]).getValue(), pcb);

                        }
                    }
                }
                MemoryWord memoryWord = new MemoryWord(command[1], (String) Memory.memory[pcb.getHigherBound()]);
                if (!found) {
                    for (int i = pcb.getLowerBound() + 9; i < pcb.getLowerBound() + 12; i++) {
                        if (Memory.memory[i] == null) {
                            Memory.memory[i] = memoryWord;
                            break;
                        }
                    }
                }
                Memory.memory[pcb.getHigherBound()] = null;
                pcb.setPc(pcb.getPc() + 1);
            }

        } else if (command[0].equals("semSignal")) {
            switch (command[1]) {
                case "userInput":
                    main.userInput.semSignal(pcb, "userInput");
                    break;
                case "userOutput":
                    main.userOutput.semSignal(pcb, "userOutput");
                    break;
                case "file":
                    main.file.semSignal(pcb, "file");
                    break;
            }
            pcb.setPc(pcb.getPc() + 1);

        } else if (command[0].equals("print")) {
            Boolean found = false;
            for (int i = pcb.getLowerBound() + 9; i < pcb.getLowerBound() + 12; i++) {
                if (Memory.memory[i] != null) {
                    if (((MemoryWord) Memory.memory[i]).getKey().equals(command[1])) {
                        found = true;
                        SystemCall.print(((MemoryWord) Memory.memory[i]).getValue());
                    }
                }
            }
            if (!found) {
                System.out.println("This variable doesn't exist");
            }
            pcb.setPc(pcb.getPc() + 1);
        } else if (command[0].equals("printFromTo")) {
            printFromTo(command[1], command[2], pcb);
            pcb.setPc(pcb.getPc() + 1);
        } else if (command[0].equals("writeFile")) {
            SystemCall.writeFile(command[1], command[2] , pcb);
            pcb.setPc(pcb.getPc() + 1);
        } else if (command[0].equals("readFile")) {
            SystemCall.readFile(command[1]);
            pcb.setPc(pcb.getPc() + 1);
        }
        main.updatePCBinMemory(pcb);
        if (pcb.getProcessId() == 1 && pcb.getPc() == 9) {
            main.p1State = true;
            main.process1.setState("Finished");
            main.updatePCBinMemory(main.process1);
            return;
        }
        if (pcb.getProcessId() == 2 && pcb.getPc() == 7) {
            main.p2State = true;
            main.process2.setState("Finished");
            main.updatePCBinMemory(main.process2);
            return;
        }
        if (pcb.getProcessId() == 3 && pcb.getPc() == 9) {
            main.p3State = true;
            main.process3.setState("Finished");
            main.updatePCBinMemory(main.process3);
            return;
        }
    }

    public static void SwapMemToDisk(PCB pcb) throws IOException {

        FileWriter writer = new FileWriter("temp" + pcb.getProcessId() + ".txt");

        if (!(pcb.getState().equals("Running"))) {
            for (int i = pcb.getLowerBound(); i < pcb.getLowerBound() + 13; i++) {
                Object data = Memory.memory[i];
                Memory.memory[i] = null;
                /*System.out.println("Data swapped to Memory : " + data);*/
                if (data instanceof MemoryWord) {
                    writer.write(((MemoryWord) data).getKey() + " " + ((MemoryWord) data).getValue() + System.lineSeparator());
                } else {
                    writer.write((String) data + System.lineSeparator());
                }

            }

        }
        writer.close();
    }

    public static void SwapDiskToMem(int lowerboundry, PCB pcb) throws IOException {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("temp" + pcb.getProcessId() + ".txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] x = line.split(" ");
                /*System.out.println("line getting loaded in index " + lowerboundry + "Data : " + line);*/
               if(line.equals("null")){
                   Memory.memory[lowerboundry]=null;
               }
                else if (x[0].equals("a") || x[0].equals("b")||x[0].equals("c")) {
                    MemoryWord memoryWord = new MemoryWord(x[0], x[1]);
                    Memory.memory[lowerboundry] = memoryWord;
                } else {
                    Memory.memory[lowerboundry] = line;
                }
                lowerboundry++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void printFromTo(String x, String y, PCB pcb) {
        int a, b;

        a = Integer.parseInt(SystemCall.readMemory(pcb, x));
        b = Integer.parseInt(SystemCall.readMemory(pcb, y));

        for (int i = a; i <= b; i++) {
            System.out.println(i);
        }

    }

    public static void assign(String x, String y, PCB pcb) {
        String result = x + " " + y;
        int start = 0;
        int end = 0;
        if (Memory.memory[0].equals(pcb.getProcessId() + "")) {
            start = 5;
            end = 8;
        } else if (Memory.memory[20].equals(pcb.getProcessId() + "")) {
            start = 25;
            end = 28;
        }
        for (int i = start; i < end; i++) {
            if (Memory.memory[i].equals("")) {
                Memory.memory[i] = result;
                break;
            }
        }
    }

}
