import java.util.Scanner;

public class Bankers {
    static final int NUMBER_OF_PROCESSES = 5;
    static final int NUMBER_OF_RESOURCES = 3;

    static void calculateAvailable(int[] total, int[][] allocation, int[] available) {
        for (int i = 0; i < NUMBER_OF_RESOURCES; i++) {
            available[i] = total[i];
            for (int j = 0; j < NUMBER_OF_PROCESSES; j++) {
                available[i] -= allocation[j][i];
            }
        }
    }

    static void calculateNeed(int[][] maximum, int[][] allocation, int[][] need) {
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
            for (int j = 0; j < NUMBER_OF_RESOURCES; j++) {
                need[i][j] = maximum[i][j] - allocation[i][j];
            }
        }
    }

    static boolean canAllocate(int process, int[][] need, int[] available) {
        for (int i = 0; i < NUMBER_OF_RESOURCES; i++) {
            if (need[process][i] > available[i]) {
                return false;
            }
        }
        return true;
    }

    static void printRemainingNeed(int[][] need) {
        System.out.println("\nRemaining need for each process:");
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
            System.out.printf("Process P%d: %d %d %d\n", i, need[i][0], need[i][1], need[i][2]);
        }
    }

    static boolean bankersAlgorithm(int[][] allocation, int[][] maximum, int[] available, int[] safeSequence) {
        int[][] need = new int[NUMBER_OF_PROCESSES][NUMBER_OF_RESOURCES];
        boolean[] finished = new boolean[NUMBER_OF_PROCESSES];
        calculateNeed(maximum, allocation, need);

        printRemainingNeed(need);

        int count = 0;
        int currentIndex = 0;

        System.out.println("\nStep-by-step allocation process:");
        while (count < NUMBER_OF_PROCESSES) {
            boolean found = false;

            for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
                int processIndex = (currentIndex + i) % NUMBER_OF_PROCESSES;

                if (!finished[processIndex] && canAllocate(processIndex, need, available)) {
                    System.out.printf("Allocating resources to Process P%d (Need: %d %d %d, Available: %d %d %d)\n",
                            processIndex, need[processIndex][0], need[processIndex][1], need[processIndex][2],
                            available[0], available[1], available[2]);

                    for (int j = 0; j < NUMBER_OF_RESOURCES; j++) {
                        available[j] += allocation[processIndex][j];
                    }

                    System.out.printf("Process P%d has completed. Updated available resources: %d %d %d\n",
                            processIndex, available[0], available[1], available[2]);
                    System.out.println();
                    safeSequence[count++] = processIndex;
                    finished[processIndex] = true;
                    found = true;

                    currentIndex = (processIndex + 1) % NUMBER_OF_PROCESSES;
                    break;
                }
            }

            if (!found) {
                return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] total = new int[NUMBER_OF_RESOURCES];
        int[][] allocation = new int[NUMBER_OF_PROCESSES][NUMBER_OF_RESOURCES];
        int[][] maximum = new int[NUMBER_OF_PROCESSES][NUMBER_OF_RESOURCES];
        int[] available = new int[NUMBER_OF_RESOURCES];
        int[] safeSequence = new int[NUMBER_OF_PROCESSES];

        System.out.print("Enter total instances of resources A, B, C (separated by spaces): ");
        for (int i = 0; i < NUMBER_OF_RESOURCES; i++) {
            total[i] = scanner.nextInt();
        }

        System.out.println("Enter the allocated resources for each process (5 processes, 3 resources A, B, C each):");
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
            System.out.print("Process P" + i + ": ");
            for (int j = 0; j < NUMBER_OF_RESOURCES; j++) {
                allocation[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter the maximum resources required for each process (5 processes, 3 resources A, B, C each):");
        for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
            System.out.print("Process P" + i + ": ");
            for (int j = 0; j < NUMBER_OF_RESOURCES; j++) {
                maximum[i][j] = scanner.nextInt();
            }
        }

        calculateAvailable(total, allocation, available);

        System.out.printf("Available resources: A = %d, B = %d, C = %d\n", available[0], available[1], available[2]);

        if (bankersAlgorithm(allocation, maximum, available, safeSequence)) {
            System.out.println("\nThe system is in a safe state.\nSafe sequence: ");
            for (int i = 0; i < NUMBER_OF_PROCESSES; i++) {
                System.out.print("P" + safeSequence[i]);
                if (i < NUMBER_OF_PROCESSES - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        } else {
            System.out.println("The system is not in a safe state.");
        }

        scanner.close();
    }
}
