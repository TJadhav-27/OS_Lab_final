import java.util.*;

public class OpcodeJob {
    public static void main(String[] args) {
        String str = "$AMJ000100030001GD10PD10HALT$DTA$END";
        System.out.println("Job string: " + str);

        String[] validOpcodes = {"GD", "PD", "LR", "SR", "CR", "BT", "HA"};

        int startIdx = 16;

        for (int i = startIdx; i < str.length()-8; i += 4) {
            String opcodeChunk = str.substring(i, Math.min(i + 4, str.length()));

            String opcode = opcodeChunk.substring(0, 2);

            System.out.println("Checking opcode: " + opcode);

            boolean isValid = false;
            for (String validOpcode : validOpcodes) {
                if(opcode.equals(validOpcode)) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) {
                System.out.println(opcode + " is a valid opcode.");
            } else {
                System.out.println(opcode + " is an invalid opcode. Raising interrupt!");
                interrupt();
                break;
            }
        }
    }

    private static void interrupt() {
        System.out.println("Interrupt raised due to invalid opcode!");
    }
}
