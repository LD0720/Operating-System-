public class Mutex {
    private int flag;
    private int owner;

    public Mutex(int flag, int owner) {
        this.flag = flag;
        this.owner = owner;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
    public void semWait(PCB pcb , String resource){
        if(this.flag==1){
            this.flag=0;
            this.owner = pcb.getProcessId();
        }
        else{
            if(resource.equals("userInput")){
                main.userInputBlocked.add(pcb.getProcessId());
                pcb.setState("Blocked");
                main.updatePCBinMemory(pcb);
            } else if (resource.equals("userOutput")) {
                main.userOutputBlocked.add(pcb.getProcessId());
                pcb.setState("Blocked");
                main.updatePCBinMemory(pcb);
            } else if (resource.equals("file")) {
                main.fileBlocked.add(pcb.getProcessId());
                pcb.setState("Blocked");
                main.updatePCBinMemory(pcb);
            }
            main.BlockedQueue.add(pcb.getProcessId());
            System.out.println("process " + pcb.getProcessId() +" is Blocked");
            System.out.println("Ready Queue : "+main.ReadyQueue);
            System.out.println("Blocked Queue: "+ main.BlockedQueue);
            System.out.println("userInputBlocked Queue : " + main.userInputBlocked);
            System.out.println("userOutputBlocked Queue : " + main.userOutputBlocked);
            System.out.println("fileBlocked Queue : " + main.fileBlocked);
        }
    }
    public void semSignal(PCB pcb ,String resource){
        /*System.out.println("process " + pcb.getProcessId() + " is using semSignal " + "on resource " + resource);*/
        if(this.owner==pcb.getProcessId()) {
            if (resource.equals("userInput")) {
                /*System.out.println("userInput resource ");*/
                if (!main.userInputBlocked.isEmpty()) {
                    /*System.out.println("user input blocked queue is not empty");*/
                    int pID = main.userInputBlocked.remove();
                    /*System.out.println("new owner is " + pID);*/
                    this.owner=pID;
                    main.ReadyQueue.add(pID);
                    main.BlockedQueue.remove(pID);
                    if(pID==1){
                        main.process1.setState("Ready");
                        main.updatePCBinMemory(main.process1);
                    } else if (pID==2) {
                        main.process2.setState("Ready");
                        main.updatePCBinMemory(main.process2);
                    } else if (pID==3) {
                        main.process3.setState("Ready");
                        main.updatePCBinMemory(main.process3);
                    }
                }else{
                    this.flag=1;
                }
            } else if (resource.equals("userOutput")) {
                if (!main.userOutputBlocked.isEmpty()) {
                    int pID = main.userOutputBlocked.remove();
                    this.owner=pID;
                    main.ReadyQueue.add(pID);
                    main.BlockedQueue.remove(pID);

                    if(pID==1){
                        main.process1.setState("Ready");
                        main.updatePCBinMemory(main.process1);
                    } else if (pID==2) {
                        main.process2.setState("Ready");
                        main.updatePCBinMemory(main.process2);
                    } else if (pID==3) {
                        main.process3.setState("Ready");
                        main.updatePCBinMemory(main.process3);
                    }
                }else{
                    this.flag=1;
                }
            } else if (resource.equals("file")) {
                if (!main.fileBlocked.isEmpty()) {
                    int pID = main.fileBlocked.remove();
                    this.owner=pID;
                    /*System.out.println("new owner of file is : " + pID);*/
                    main.ReadyQueue.add(pID);
                    main.BlockedQueue.remove(pID);
                    if(pID==1){
                        main.process1.setState("Ready");
                        main.updatePCBinMemory(main.process1);
                    } else if (pID==2) {
                        main.process2.setState("Ready");
                        main.updatePCBinMemory(main.process2);
                    } else if (pID==3) {
                        main.process3.setState("Ready");
                        main.updatePCBinMemory(main.process3);
                    }
                }else{
                    this.flag=1;
                }
            }
        }
    }
}
