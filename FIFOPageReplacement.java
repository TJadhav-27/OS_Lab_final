import java.util.Arrays;
import java.util.Scanner;

class FIFOPageReplacement{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[] pages;
        System.out.print("Enter length of reference string: ");
        int len = sc.nextInt();
        pages = new int[len];
        System.out.print("Enter the reference string space separated: ");
        for(int i=0;i<len;i++) {
            pages[i]=sc.nextInt();
        }
        System.out.println(Arrays.toString(pages));
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        int page_fault = check(pages, len, frames);
        System.out.println("----------------------------------------");
        System.out.println("Total page faults: " + page_fault);
        System.out.println("----------------------------------------");
    }

    static int check(int[] pages, int len, int frames){
        int pf=0;
        int next_frame=0;
        int[] frame = new int[100];

        for(int j=0;j<frames;j++)
            frame[j]=-1;
       // System.out.println(Arrays.toString(frame));

        for(int i=0;i<len;i++)
        {
            int found =0;
            System.out.print("Current frames: ");
            for (int k = 0; k < frames; k++)
            {
                if(frame[k]!=-1)
                    System.out.print(frame[k] + " ");
                else
                    System.out.print("_" + " ");
            }
            System.out.println();
            System.out.println("Current page request for page: " + pages[i]);

            for(int a = 0;a<frames;a++)
            {
                if(frame[a]==pages[i])
                {
                    found = 1;
                    System.out.println("Page found at frame " + (a + 1));
                    break;
                }
            }

            if(found==0){
                System.out.println("Page fault. Replacing page");
                frame[next_frame] = pages[i];
                next_frame= (next_frame + 1)%frames;
                pf++;
            }
        }
        return pf;
    }
}