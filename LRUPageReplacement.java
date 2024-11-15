import java.util.Scanner;
public class LRUPageReplacement {
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

        int lruFaults = lru(pages, n, frames);
        System.out.println("Number of page faults using LRU: " + lruFaults);

        scanner.close();
    }

    public static int lru(int[] pages, int n, int frames) {
        int pageFaults = 0;
        int[] frame = new int[MAX_FRAMES];
        int[] lastUsed = new int[MAX_FRAMES];
        int currentTime = 0;

        for (int j = 0; j < frames; j++) {
            frame[j] = -1;
            lastUsed[j] = -1;
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
                    lastUsed[j] = currentTime;
                    System.out.println("Page " + pages[i] + " found in frame " + j + " (HIT).");
                    break;
                }
            }

            if (!found) {
                int lruIndex = 0;
                for (int j = 1; j < frames; j++) {
                    if (lastUsed[j] < lastUsed[lruIndex]) {
                        lruIndex = j;
                    }
                }
                System.out.println("Page " + pages[i] + " caused a page fault (MISS). Replacing page " + frame[lruIndex] + " in frame " + lruIndex + ".");
                frame[lruIndex] = pages[i];
                lastUsed[lruIndex] = currentTime;
                pageFaults++;
            }

            currentTime++;
        }
        return pageFaults;
    }
}
