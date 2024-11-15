import java.util.*;

public class OperandJob {
    public static void main(String[] args) {
        String str = "$AMJ000100030001GD10PD10HALT$DTA$END";
        System.out.println("Job string: " + str);

        String[] validOpcodes = {"GD", "PD", "LR", "SR", "CR", "BT", "HA"};
        int startIdx = 16;

        for (int i = startIdx; i < str.length() - 8; i += 4) {
            String opcodeChunk = str.substring(i, Math.min(i + 4, str.length()));
            String opcode = opcodeChunk.substring(0, 2);
            String operand = opcodeChunk.substring(2, 4);

            System.out.println("Checking opcode: " + opcode + " with operand: " + operand);

            boolean isValid = false;
            for (String validOpcode : validOpcodes) {
                if (opcode.equals(validOpcode)) {
                    isValid = true;
                    break;
                }
            }

            if (isValid) {
                if (isOperandValid(operand)) {
                    System.out.println(opcode + " " + operand + " is a valid opcode and operand.");
                } else if(opcode.equals("HA") && !isOperandValid(operand)){
                    System.out.println("HALT does not have an operand");
                } else {
                    System.out.println(opcode + " " + operand + " has an invalid operand. Raising interrupt!");
                    interrupt("Invalid operand: Operand must be a 2-digit number!");
                    break;
                }
            } else {
                System.out.println(opcode + " is an invalid opcode. Raising interrupt!");
                interrupt("Invalid opcode!");
                break;
            }
        }
    }

    private static boolean isOperandValid(String operand) {
        if (operand.length() == 2 && operand.matches("\\d{2}")) {
            return true;
        }

        if (operand.matches("[a-zA-Z]+")) {
            return false;
        }

        return false;
    }

    private static void interrupt(String errorMessage) {
        System.out.println("Interrupt raised: " + errorMessage);
        System.exit(1);
    }
}
