import java.util.Scanner;

public class FCFS_DiskScheduling {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter initial head position: ");
        int initialHead = scanner.nextInt();

        System.out.print("Enter number of disk requests: ");
        int n = scanner.nextInt();

        int[] requests = new int[n];
        System.out.print("Enter disk requests: ");
        for (int i = 0; i < n; i++) {
            requests[i] = scanner.nextInt();
        }

        int totalSeekTime = 0;
        int currentPosition = initialHead;

        System.out.println("\nDisk Scheduling (FCFS)");
        System.out.println("---------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s%n", "Current Position", "Next Request", "Seek Time");
        System.out.println("---------------------------------------------------");

        for (int i = 0; i < n; i++) {
            int seekTime = Math.abs(currentPosition - requests[i]);
            totalSeekTime += seekTime;

            System.out.printf("%-15d %-15d %-15d%n", currentPosition, requests[i], seekTime);

            currentPosition = requests[i];
        }

        System.out.println("---------------------------------------------------");
        System.out.println("Total Seek Time: " + totalSeekTime);
        scanner.close();
    }
}
