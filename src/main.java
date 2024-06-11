import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class main {
    static Parser parser = new Parser();
    static Scheduler scheduler = new Scheduler();
    static Queue<Integer> ReadyQueue = new LinkedList<Integer>();
    static Queue<Integer> BlockedQueue = new LinkedList<Integer>();
    static Queue<Integer> userInputBlocked = new LinkedList<Integer>();
    static Queue<Integer> userOutputBlocked = new LinkedList<Integer>();
    static Queue<Integer> fileBlocked = new LinkedList<Integer>();
    static Mutex userInput = new Mutex(1, 0);
    static Mutex userOutput = new Mutex(1, 0);
    static Mutex file = new Mutex(1, 0);
    static PCB process1 = null;
    static PCB process2 = null;
    static PCB process3 = null;
    static Boolean p1Loaded = false;
    static Boolean p2Loaded = false;
    static Boolean p3Loaded = false;
    static Boolean p1State = false;
    static Boolean p2State = false;
    static Boolean p3State = false;
    static int clk = 0;

    public Queue getReadyQueue() {
        return ReadyQueue;
    }

    public void addToReadyQueue(int process) {
        ReadyQueue.add(process);
    }

    public void removeFromReadyQueue(int process) {
        ReadyQueue.remove(process);
    }

    public void addToBlockedQueue(int process) {
        BlockedQueue.add(process);
    }

    public void addToUserInputBlocked(int process) {
        userInputBlocked.add(process);
    }

    public void addToUserOutputBlocked(int process) {
        userOutputBlocked.add(process);
    }

    public void addToFileBlocked(int process) {
        fileBlocked.add(process);
    }

    public void removeFromBlockedQueue(int process) {
        BlockedQueue.remove(process);
    }

    public void removeFromUserInputBlocked(int process) {
        userInputBlocked.remove(process);
    }

    public void removeFromUserOutputBlocked(int process) {
        userOutputBlocked.remove(process);
    }

    public void removeFromFileBlocked(int process) {
        fileBlocked.remove(process);
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter arrival time of process 1 ");
        int time1 = scanner.nextInt();
        System.out.println("Enter arrival time of process 2 ");
        int time2 = scanner.nextInt();
        System.out.println("Enter arrival time of process 3 ");
        int time3 = scanner.nextInt();
        System.out.println("Enter the Quantum ");
        int quantum = scanner.nextInt();
        scheduler.schedule(time1, time2, time3, quantum);
        int Q = 0;
        int runningPid = 0;
        while (!p1State || !p2State || !p3State) { //while all processes are not done
            checkArrival();
           /* System.out.println("State of p1" + p1State);
            System.out.println("State of p2" + p2State);
            System.out.println("State of p3" + p3State);
*/
//            System.out.println("p1Loaded " + p1Loaded);
//            System.out.println("p2loaded " + p2Loaded );
//            System.out.println("p3Loaded " + p3Loaded);

            if (Q == 0 ) {
//                System.out.println("Q==0");
                runningPid = ReadyQueue.remove();
                System.out.println("process " + runningPid +" is Running");
                System.out.println("Ready Queue : "+main.ReadyQueue);
                System.out.println("Blocked Queue: "+ main.BlockedQueue);
                System.out.println("userInputBlocked Queue : " + main.userInputBlocked);
                System.out.println("userOutputBlocked Queue : " + main.userOutputBlocked);
                System.out.println("fileBlocked Queue : " + main.fileBlocked);

            }
            if (Q < Scheduler.maxQunatum ) {
//                System.out.println("Q < Scheduler.maxQunatum");
                System.out.println("Running process has id : " + runningPid);
                System.out.println("Memory while running : "+ Memory.tostring());
                if (runningPid == 1) {
                    System.out.println("runningPid == 1");
                    if(!process1.getState().equals("Blocked") && !process1.getState().equals("Finished")) {
                        if (!p1Loaded) {
                            int lowerBoundry = 12;
                            if (!process2.getState().equals("Running") && p2Loaded) {
                                lowerBoundry = process2.getLowerBound();
                                p2Loaded = false;
                                System.out.println("process 2 is swapped to disk");
                                Parser.SwapMemToDisk(process2);
                            } else if (!process3.getState().equals("Running") && p3Loaded) {
                                lowerBoundry = process3.getLowerBound();
                                p3Loaded = false;
                                System.out.println("process 3 is swapped to disk");
                                Parser.SwapMemToDisk(process3);
                            }
                            System.out.println("process 1 is swapped to Memory");
                            Parser.SwapDiskToMem(lowerBoundry , process1);
                            p1Loaded = true;
                            process1.setLowerBound(lowerBoundry);
                        }
                        //parser.loadPCB(process1);
                        process1.setState("Running");
                        parser.interpeter(process1);
                        updatePCBinMemory(process1);
                    }
                } else if (runningPid == 2) {
//                    System.out.println("runningPid == 2");
                    if(!process2.getState().equals("Blocked") && !process2.getState().equals("Finished")) {
                        if (!p2Loaded) {
                            int lowerBoundry = 0;
                            if (!process1.getState().equals("Running") && p1Loaded) {
                                lowerBoundry = process1.getLowerBound();
                                p1Loaded = false;
                                System.out.println("process 1 is swapped to disk");
                                Parser.SwapMemToDisk(process1);
                            } else if (!process3.getState().equals("Running") && p3Loaded) {
                                lowerBoundry = process3.getLowerBound();
                                p3Loaded = false;
                                System.out.println("process 3 is swapped to disk");
                                Parser.SwapMemToDisk(process3);
                            }
                            System.out.println("process 2 is swapped to Memory");
                            Parser.SwapDiskToMem(lowerBoundry,process2);
                            p2Loaded = true;
                            process2.setLowerBound(lowerBoundry);
                        }
                       // parser.loadPCB(process2);
                        process2.setState("Running");
                        /*System.out.println("process 2 is getting loaded with a lower boundry of : " + process2.getLowerBound());*/
                        parser.interpeter(process2);
                        updatePCBinMemory(process2);
                    }
                } else if (runningPid == 3) {
                    /*System.out.println("runningPid == 3");*/
                    if (!process3.getState().equals("Blocked") && !process3.getState().equals("Finished")) {
                        if (!p3Loaded) {
                            int lowerBoundry = 0;
                            if (!process2.getState().equals("Running") && p2Loaded) {
                                lowerBoundry = process2.getLowerBound();
                                p2Loaded = false;
                                System.out.println("process 2 is swapped to disk");
                                Parser.SwapMemToDisk(process2);
                            } else if (!process1.getState().equals("Running") && p1Loaded) {
                                lowerBoundry = process1.getLowerBound();
                                p1Loaded = false;
                                System.out.println("process 1 is swapped to disk");
                                Parser.SwapMemToDisk(process1);
                            }
                            //parser.loadPCB(process3);
                            Parser.SwapDiskToMem(lowerBoundry , process3);
                            System.out.println("process 3 is swapped to Memory");
                            p3Loaded = true;
                            process3.setLowerBound(lowerBoundry);
                        }
                        process3.setState("Running");
                        parser.interpeter(process3);
                        updatePCBinMemory(process3);
                    }
                }
                Q++;
            }  if (Q == Scheduler.maxQunatum) {
                /*System.out.println("Q == Scheduler.maxQunatum");*/
                if (runningPid == 1) {
                    /*System.out.println("runningPid == 1");
                    System.out.println("I finished my quantum and i'm getting added to the ready list");*/
                    if (!process1.getState().equals("Blocked") &&!process1.getState().equals("Finished") ) {
                        process1.setState("Ready");
                        ReadyQueue.add(runningPid);
                        /*System.out.println("Ready queue after adding 1" + ReadyQueue);*/
                    }
                    updatePCBinMemory(process1);
                } else if (runningPid == 2) {
                   /* System.out.println("runningPid == 2");*/
                    if (!process2.getState().equals("Blocked") &&!process2.getState().equals("Finished")) {
                        process2.setState("Ready");
                        ReadyQueue.add(runningPid);
                    }
                    updatePCBinMemory(process2);
                } else if (runningPid == 3) {
                    /*System.out.println("runningPid == 3");*/
                    if (!process3.getState().equals("Blocked") &&!process3.getState().equals("Finished")) {
                        process3.setState("Ready");
                        ReadyQueue.add(runningPid);
                    }
                    updatePCBinMemory(process3);
                }
                Q = 0;
            }
            System.out.println("Printing Memory data : ");
            System.out.println(Memory.tostring());
            System.out.println("Clock = " + clk);
            System.out.println("----------------------------------------------------------------------------------");
            clk++;
        }
    }

    private static void checkArrival() throws IOException {
        if (clk != 0) {
            if (clk == Scheduler.arrivalTime1) {
                int lowerBoundry = 0;
                if (process2 != null && process3 != null) {
                    if (!process2.getState().equals("Running")) {
                        lowerBoundry = process2.getLowerBound();
                        p2Loaded = false;
                        Parser.SwapMemToDisk(process2);
                        System.out.println("process 2 is swapped to disk");
                    } else if (!process3.getState().equals("Running")) {
                        lowerBoundry = process3.getLowerBound();
                        p3Loaded = false;
                        Parser.SwapMemToDisk(process3);
                        System.out.println("process 3 is swapped to disk");
                    }
                } else {
                    if (process2 == null) {
                        if (process3.getLowerBound() == 12) {
                            lowerBoundry = 26;
                        } else {
                            lowerBoundry = 12;
                        }
                    } else {
                        if (process2.getLowerBound() == 12) {
                            lowerBoundry = 26;
                        } else {
                            lowerBoundry = 12;
                        }
                    }
                }
                parser.loadProcess("Program_1.txt", lowerBoundry);
                p1Loaded = true;
                process1 = new PCB(1, "Ready", 0, lowerBoundry);
                parser.loadPCB(process1);
                ReadyQueue.add(1);
                System.out.println("process 1 arrived ");
            } else if (clk == Scheduler.arrivalTime2) {
                int lowerBoundry = 0;
                if (process1 != null && process3 != null) {
                    if (!process1.getState().equals("Running")) {
                        lowerBoundry = process1.getLowerBound();
                        p1Loaded = false;
                        Parser.SwapMemToDisk(process1);
                        System.out.println("process 1 is swapped to disk");
                    } else if (!process3.getState().equals("Running")) {
                        lowerBoundry = process3.getLowerBound();
                        p3Loaded = false;
                        Parser.SwapMemToDisk(process3);
                        System.out.println("process 3 is swapped to disk");
                    }
                } else {
                    if (process3 == null) {
                        if (process1.getLowerBound() == 12) {
                            lowerBoundry = 26;
                        } else {
                            lowerBoundry = 12;
                        }
                    } else {
                        if (process3.getLowerBound() == 12) {
                            lowerBoundry = 26;
                        } else {
                            lowerBoundry = 12;
                        }
                    }
                }
                System.out.println("process 2 arrived");
                parser.loadProcess("Program_2.txt", lowerBoundry);
                p2Loaded = true;
                process2 = new PCB(2, "Ready", 0, lowerBoundry);
                parser.loadPCB(process2);
                ReadyQueue.add(2);
            } else if (clk == Scheduler.arrivalTime3) {
                int lowerBoundry = 0;
                if (process1 != null && process2 != null) {
                    if (!process2.getState().equals("Running")) {
                        lowerBoundry = process2.getLowerBound();
                        p2Loaded = false;
                        Parser.SwapMemToDisk(process2);
                        System.out.println("process 2 is swapped to disk");
                    } else if (!process1.getState().equals("Running")) {
                        lowerBoundry = process1.getLowerBound();
                        p1Loaded = false;
                        Parser.SwapMemToDisk(process1);
                        System.out.println("process 1 is swapped to disk");

                    }
                } else {
                    if (process2 == null) {
                        if (process1.getLowerBound() == 12) {
                            lowerBoundry = 26;
                        } else {
                            lowerBoundry = 12;
                        }
                    } else {
                        if (process2.getLowerBound() == 12) {
                            lowerBoundry = 26;
                        } else {
                            lowerBoundry = 12;
                        }
                    }
                }
                System.out.println("process 3 arrived");
                parser.loadProcess("Program_3.txt", lowerBoundry);
                p3Loaded = true;
                process3 = new PCB(3, "Ready", 0, lowerBoundry);
                parser.loadPCB(process3);
                ReadyQueue.add(3);
            }
        }

    }

    public static void updatePCBinMemory(PCB pcb) {
        //check memory in positions 0 , 4, 8 if processID matches any cell then update all 4 cells
        if ((int) Memory.memory[0] == pcb.getProcessId()) {
            /*System.out.println("I am updating Memory[0]");*/
            Memory.memory[1] = pcb.getState();
            Memory.memory[2] = pcb.getPc();
            Memory.memory[3] = pcb.getLowerBound();
        } else if ((int) Memory.memory[4] == pcb.getProcessId()) {
            /*System.out.println("I am updating Memory[4]");*/
            Memory.memory[5] = pcb.getState();
            Memory.memory[6] = pcb.getPc();
            Memory.memory[7] = pcb.getLowerBound();
        } else if ((int) Memory.memory[8] == pcb.getProcessId()) {
            /*System.out.println("I am updating Memory[8]");*/
            Memory.memory[9] = pcb.getState();
            Memory.memory[10] = pcb.getPc();
            Memory.memory[11] = pcb.getLowerBound();
        }
    }
}
