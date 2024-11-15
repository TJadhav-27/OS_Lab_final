import java.util.ArrayList;
import java.util.Scanner;

public class SSTF_DiskScheduling {
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

        ArrayList<Integer> requestList = new ArrayList<>();
        for (int request : requests) {
            requestList.add(request);
        }

        int currentPosition = initialHead;
        int totalSeekTime = 0;

        System.out.println("\nDisk Scheduling (SSTF)");
        System.out.println("---------------------------------------------------");
        System.out.printf("%-15s %-15s %-15s%n", "Current Position", "Next Request", "Seek Time");
        System.out.println("---------------------------------------------------");

        while (!requestList.isEmpty()) {
            int closestRequest = -1;
            int closestDistance = Integer.MAX_VALUE;

            for (int request : requestList) {
                int distance = Math.abs(currentPosition - request);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestRequest = request;
                }
            }

            int seekTime = Math.abs(currentPosition - closestRequest);
            totalSeekTime += seekTime;

            System.out.printf("%-15d %-15d %-15d%n", currentPosition, closestRequest, seekTime);

            currentPosition = closestRequest;
            requestList.remove((Integer) closestRequest);
        }

        System.out.println("---------------------------------------------------");
        System.out.println("Total Seek Time: " + totalSeekTime);
        scanner.close();
    }
}
