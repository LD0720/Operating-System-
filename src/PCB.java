public class PCB {
    private int processId;
    private String state;
    private int pc;
    private int lowerBound;
    private int higherBound;
    public PCB(int processId, String state, int pc, int lowerBound) {
        this.processId = processId;
        this.state = state;
        this.pc = pc;
        this.lowerBound = lowerBound;
        this.higherBound = lowerBound+13;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public int getHigherBound() {
        return higherBound;
    }

    public void setHigherBound(int higherBound) {
        this.higherBound = higherBound;
    }
}
