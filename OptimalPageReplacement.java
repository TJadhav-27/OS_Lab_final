import java.util.Scanner;

public class OptimalPageReplacement {

    static final int MAX_PAGES = 100;
    static final int MAX_FRAMES = 10;

    public static void main(String[] args) {
        int[] pages = new int[MAX_PAGES];
        int n, frames;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of pages in the reference string: ");
        n = scanner.nextInt();
        System.out.print("Enter the reference string (space-separated): ");
        for (int i = 0; i < n; i++) {
            pages[i] = scanner.nextInt();
        }
        System.out.print("Enter the number of frames: ");
        frames = scanner.nextInt();

        int optimalFaults = optimal(pages, n, frames);
        System.out.println("Number of page faults using Optimal: " + optimalFaults);

        scanner.close();
    }

    public static int optimal(int[] pages, int n, int frames) {
        int pageFaults = 0;
        int[] frame = new int[MAX_FRAMES];
        for (int j = 0; j < frames; j++) {
            frame[j] = -1;
        }

        for (int i = 0; i < n; i++) {
            boolean found = false;

            System.out.print("\nCurrent Frames: ");
            for (int j = 0; j < frames; j++) {
                if (frame[j] != -1)
                    System.out.print(frame[j] + " ");
                else
                    System.out.print("- ");
            }
            System.out.println("\nPage request: " + pages[i]);

            for (int j = 0; j < frames; j++) {
                if (frame[j] == pages[i]) {
                    found = true;
                    System.out.println("Page " + pages[i] + " found in frame " + j + " (HIT).");
                    break;
                }
            }

            if (!found) {
                int emptyFrame = -1;
                for (int j = 0; j < frames; j++) {
                    if (frame[j] == -1) {
                        emptyFrame = j;
                        break;
                    }
                }

                if (emptyFrame != -1) {
                    System.out.println("Page " + pages[i] + " caused a page fault (MISS). Placing in empty frame " + emptyFrame + ".");
                    frame[emptyFrame] = pages[i];
                } else {
                    int optimalIndex = -1, farthest = -1;

                    for (int j = 0; j < frames; j++) {
                        int k;
                        for (k = i + 1; k < n; k++) {
                            if (frame[j] == pages[k]) {
                                if (k > farthest) {
                                    farthest = k;
                                    optimalIndex = j;
                                }
                                break;
                            }
                        }
                        if (k == n) {
                            optimalIndex = j;
                            break;
                        }
                    }
                    System.out.println("Page " + pages[i] + " caused a page fault (MISS). Replacing page " + frame[optimalIndex] + " in frame " + optimalIndex + ".");
                    frame[optimalIndex] = pages[i];
                }

                pageFaults++;
            }
        }
        return pageFaults;
    }
}
