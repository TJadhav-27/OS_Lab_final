import java.util.Arrays;
import java.util.Scanner;

public class SCAN_DiskScheduling {
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

        System.out.print("Enter the disk size: ");
        int diskSize = scanner.nextInt();

        Arrays.sort(requests);

        int currentPosition = initialHead;
        int totalSeekTime = 0;
        boolean directionUp = true;

        System.out.println("\nDisk Scheduling (SCAN)");
        System.out.println("---------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s%n", "Current Position", "Next Request", "Seek Time");
        System.out.println("---------------------------------------------------");

        for (int i = 0; i < n; i++) {
            if (requests[i] >= currentPosition) {
                int seekTime = Math.abs(currentPosition - requests[i]);
                totalSeekTime += seekTime;
                System.out.printf("%-15d %-15d %-15d%n", currentPosition, requests[i], seekTime);
                currentPosition = requests[i];
            }
        }

        if (currentPosition != diskSize - 1) {
            int seekTime = Math.abs(currentPosition - (diskSize - 1));
            totalSeekTime += seekTime;
            currentPosition = diskSize - 1;
        }

        for (int i = n - 1; i >= 0; i--) {
            if (requests[i] < initialHead) {
                int seekTime = Math.abs(currentPosition - requests[i]);
                totalSeekTime += seekTime;
                System.out.printf("%-15d %-15d %-15d%n", currentPosition, requests[i], seekTime);
                currentPosition = requests[i];
            }
        }

        System.out.println("---------------------------------------------------");
        System.out.println("Total Seek Time: " + totalSeekTime);
        scanner.close();
    }
}
