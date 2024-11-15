import java.util.Scanner;
import java.util.ArrayList;

class RoundRobin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] remainingTime = new int[n];
        int[] finishTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnaroundTime = new int[n];

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i];
        }

        System.out.print("Enter time quantum: ");
        int quantum = scanner.nextInt();

        int currentTime = 0, completed = 0;
        boolean isComplete[] = new boolean[n];
        ArrayList<Integer> processSequence = new ArrayList<>();

        while (completed < n) {
            boolean didProcess = false;
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && remainingTime[i] > 0) {
                    if (remainingTime[i] > quantum) {
                        currentTime += quantum;
                        remainingTime[i] -= quantum;
                    } else {
                        currentTime += remainingTime[i];
                        finishTime[i] = currentTime;
                        turnaroundTime[i] = finishTime[i] - arrivalTime[i];
                        waitingTime[i] = turnaroundTime[i] - burstTime[i];
                        remainingTime[i] = 0;
                        completed++;

                        totalTurnaroundTime += turnaroundTime[i];
                        totalWaitingTime += waitingTime[i];
                    }
                    processSequence.add(i + 1);
                    didProcess = true;
                }
            }
            if (!didProcess) {
                currentTime++;
            }
        }

        double avgWaitingTime = totalWaitingTime / n;
        double avgTurnaroundTime = totalTurnaroundTime / n;

        System.out.println("\nProcess | Arrival Time | Burst Time | Finish Time | Turnaround Time | Waiting Time");
        for (int i = 0; i < n; i++) {
            System.out.printf("%7d | %12d | %10d | %11d | %14d | %12d\n",
                    (i + 1), arrivalTime[i], burstTime[i], finishTime[i], turnaroundTime[i], waitingTime[i]);
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", avgWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);

        System.out.println("\nProcess Execution Sequence: " + processSequence);
    }
}
