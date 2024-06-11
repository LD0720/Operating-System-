public class Memory {
     static Object[] memory = new Object[40];

    public Memory() {

    }

    public Object[] getMemory() {
        return memory;
    }

    public void setMemory(Object[] memory) {
        this.memory = memory;
    }
    public static String tostring(){
        String result = "";
        for(int i=0 ; i< Memory.memory.length;i++){
           if(Memory.memory[i]!=null) {
               if (Memory.memory[i] instanceof Integer) {
                   result += ((int) Memory.memory[i] )+ "\n";
               } else if (memory[i] instanceof String) {
                   result += (Memory.memory[i] )+ "\n";
               } else if (memory[i] instanceof MemoryWord) {
                   result += ((MemoryWord) Memory.memory[i]).getKey() + " " + ((MemoryWord) Memory.memory[i]).getValue() + "\n";
               }
           }
        }
        return result;
    }
}
