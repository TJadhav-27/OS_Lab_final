import java.util.Scanner;

class PreemptiveSJF {
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
        boolean[] isComplete = new boolean[n];

        double totalWaitingTime = 0;
        double totalTurnaroundTime = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            arrivalTime[i] = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            burstTime[i] = scanner.nextInt();
            remainingTime[i] = burstTime[i];
        }

        int currentTime = 0, completed = 0, minIndex = -1;
        while (completed < n) {
            int minBurst = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && !isComplete[i] && remainingTime[i] < minBurst) {
                    minBurst = remainingTime[i];
                    minIndex = i;
                }
            }

            if (minIndex == -1) {
                currentTime++;
            } else {
                remainingTime[minIndex]--;
                if (remainingTime[minIndex] == 0) {
                    isComplete[minIndex] = true;
                    completed++;
                    finishTime[minIndex] = currentTime + 1;
                    turnaroundTime[minIndex] = finishTime[minIndex] - arrivalTime[minIndex];
                    waitingTime[minIndex] = turnaroundTime[minIndex] - burstTime[minIndex];

                    totalTurnaroundTime += turnaroundTime[minIndex];
                    totalWaitingTime += waitingTime[minIndex];
                }
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
    }
}
