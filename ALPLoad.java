//input: input.alp
//MOVER A,3
//        MOVER B,4
//        ADD A,B
//        ADD B,A
//        END

import java.io.*;

public class ALPLoad {

    public static void main(String[] args) {
        String fileName = "input.alp";
        int memorySize = 30;
        int memoryWidth = 10;
        String[][] memory = new String[memorySize][memoryWidth];

        for (int i = 0; i < memorySize; i++) {
            for (int j = 0; j < memoryWidth; j++) {
                memory[i][j] = "[_]";
            }
        }

        loadALPProgram(fileName, memory);

        displayMemory(memory);
    }

    private static void loadALPProgram(String fileName, String[][] memory) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            int memoryIndex = 0;

            while ((line = reader.readLine()) != null && memoryIndex < memory.length) {
                line = line.trim();
                if (!line.isEmpty()) {
                    int charIndex = 0;
                    for (char c : line.toCharArray()) {
                        if (charIndex < memory[memoryIndex].length) {
                            memory[memoryIndex][charIndex] = "[" + c + "]";
                            charIndex++;
                        }
                    }
                    while (charIndex < memory[memoryIndex].length) {
                        memory[memoryIndex][charIndex] = "[_]";
                        charIndex++;
                    }

                    memoryIndex++;
                }
            }

            reader.close();
        } catch (IOException e) {
            System.out.println("Error reading input file: " + e.getMessage());
        }
    }

    private static void displayMemory(String[][] memory) {
        System.out.println("Memory contents:");
        for (int i = 0; i < memory.length; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < memory[i].length; j++) {
                row.append(memory[i][j]).append(" ");
            }
            System.out.println(row.toString());
        }
    }
}
