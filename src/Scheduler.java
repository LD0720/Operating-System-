public class Scheduler{
static int arrivalTime1;
static int arrivalTime2;
static int arrivalTime3;
static int maxQunatum;
static Parser parser = new Parser();
public void schedule(int time1 , int time2, int time3,int Quantum){
    maxQunatum = Quantum; //2
    arrivalTime1 = time1; //0
    arrivalTime2 = time2; //1
    arrivalTime3 = time3; //4
    if(time1<time2 && time1<time3){
        //process 1
        /*System.out.println("time1<time2 && time1<time3");*/
        main.process1 = new PCB(1,"Ready",0,12);
            parser.loadPCB(main.process1);
            parser.loadProcess("Program_1.txt",12);
            main.ReadyQueue.add(1);
            main.p1Loaded=true;
    } else if (time2<time1 && time2<time3) {
        /*System.out.println("time2<time1 && time2<time3");*/
        //process 2
        main.process2 = new PCB(2,"Ready",0,12);
        parser.loadPCB(main.process2);
        parser.loadProcess("Program_2.txt",12);
        main.ReadyQueue.add(2);
        main.p2Loaded=true;
    } else if (time3<time2 && time3<time1) {
        /*System.out.println("time3");*/
        //process 3
        main.process3 = new PCB(3,"Ready",0,12);
        parser.loadPCB(main.process3);
        parser.loadProcess("Program_3.txt",12);
        main.ReadyQueue.add(3);
        main.p3Loaded=true;
    } else if (time1==time2) {
        /*System.out.println("1=2");*/
        //process 1 and 2
        main.process1 = new PCB(1,"Ready",0,12);
        parser.loadPCB(main.process1);
        parser.loadProcess("Program_1.txt",12);
        main.ReadyQueue.add(1);
        main.p1Loaded=true;
        main.process2 = new PCB(2,"Ready",0,26);
        parser.loadPCB(main.process2);
        parser.loadProcess("Program_2.txt",26);
        main.ReadyQueue.add(2);
        main.p2Loaded=true;
    } else if (time1==time3) {
        /*System.out.println("1=3");*/
        //process 1 and 3
        main.process1 = new PCB(1,"Ready",0,12);
        parser.loadPCB(main.process1);
        parser.loadProcess("Program_1.txt",12);
        main.ReadyQueue.add(1);
        main.p1Loaded=true;

        main.process3 = new PCB(3,"Ready",0,26);
        parser.loadPCB(main.process3);
        parser.loadProcess("Program_3.txt",12);
        main.ReadyQueue.add(3);
        main.p3Loaded=true;
    } else if (time2==time3) {
        /*System.out.println("2=3");*/
        //process 2 and 3
        main.process2 = new PCB(2,"Ready",0,12);
        parser.loadPCB(main.process2);
        parser.loadProcess("Program_2.txt",26);
        main.ReadyQueue.add(2);
        main.p2Loaded=true;

        main.process3 = new PCB(3,"Ready",0,26);
        parser.loadPCB(main.process3);
        parser.loadProcess("Program_3.txt",12);
        main.ReadyQueue.add(3);
        main.p3Loaded=true;

    }else {
        //process 1 and 2 and 3
        main.process1 = new PCB(1,"Ready",0,12);
        parser.loadPCB(main.process1);
        parser.loadProcess("Program_1.txt",12);
        main.ReadyQueue.add(1);
        main.p1Loaded=true;

        main.process2 = new PCB(2,"Ready",0,26);
        parser.loadPCB(main.process2);
        parser.loadProcess("Program_2.txt",26);
        main.ReadyQueue.add(2);
        main.p2Loaded=true;

        main.process3 = new PCB(3,"Ready",0,0);
        parser.loadPCB(main.process3);
        main.ReadyQueue.add(3);
    }
}

}