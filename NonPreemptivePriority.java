import java.util.Scanner;

class NonPreemptivePriority {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of processes: ");
        int n = scanner.nextInt();
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];
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
            System.out.print("Enter priority for process " + (i + 1) + ": ");
            priority[i] = scanner.nextInt();
        }

        int currentTime = 0, completed = 0;
        boolean[] isComplete = new boolean[n];
        StringBuilder processSequence = new StringBuilder();

        while (completed < n) {
            int minPriority = Integer.MAX_VALUE;
            int minIndex = -1;

            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && !isComplete[i] && priority[i] < minPriority) {
                    minPriority = priority[i];
                    minIndex = i;
                }
            }

            if (minIndex == -1) {
                currentTime++;
            } else {

                processSequence.append("P").append(minIndex + 1).append(" ");
                currentTime += burstTime[minIndex];
                finishTime[minIndex] = currentTime;
                turnaroundTime[minIndex] = finishTime[minIndex] - arrivalTime[minIndex];
                waitingTime[minIndex] = turnaroundTime[minIndex] - burstTime[minIndex];

                totalTurnaroundTime += turnaroundTime[minIndex];
                totalWaitingTime += waitingTime[minIndex];
                isComplete[minIndex] = true;
                completed++;
            }
        }

        double avgWaitingTime = totalWaitingTime / n;
        double avgTurnaroundTime = totalTurnaroundTime / n;

        System.out.println("\nProcess | Arrival Time | Burst Time | Priority | Finish Time | Turnaround Time | Waiting Time");
        for (int i = 0; i < n; i++) {
            System.out.printf("%7d | %12d | %10d | %8d | %11d | %14d | %12d\n",
                    (i + 1), arrivalTime[i], burstTime[i], priority[i], finishTime[i], turnaroundTime[i], waitingTime[i]);
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", avgWaitingTime);
        System.out.printf("Average Turnaround Time: %.2f\n", avgTurnaroundTime);

        System.out.println("\nProcess Execution Sequence: " + processSequence.toString().trim());
    }
}
