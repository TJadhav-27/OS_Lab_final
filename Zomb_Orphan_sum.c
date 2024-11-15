#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>

void createZombieProcess() {
    pid_t pid = fork();
    if (pid > 0) {
        printf("Parent process (PID: %d) created a zombie process.\n", getpid());
        sleep(5);
        printf("Parent process is now calling wait().\n");
        wait(NULL);
    } else if (pid == 0) {
        printf("Child process (PID: %d) is exiting to become a zombie.\n", getpid());
        exit(0);
    } else {
        perror("Fork failed");
    }
}

void createOrphanProcess() {
    pid_t pid = fork();
    if (pid > 0) {
        printf("Parent process (PID: %d) will exit, making the child an orphan.\n", getpid());
        sleep(2);
    } else if (pid == 0) {
        sleep(5);
        printf("Child process (PID: %d) is now an orphan adopted by init.\n", getpid());
    } else {
        perror("Fork failed");
    }
}

void calculateSum() {
    // int arr[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    // int n = sizeof(arr) / sizeof(arr[0]);
    int n;
    printf("Enter the number of elements in the array: ");
    scanf("%d", &n);

    int arr[n];
    int evenSum = 0, oddSum = 0;

    printf("Enter the elements of the array:\n");
    for (int i = 0; i < n; i++) {
        scanf("%d", &arr[i]);
    }

    pid_t pid = fork();
    if (pid > 0) {
        wait(NULL);
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 == 0) {
                evenSum += arr[i];
            }
        }
        printf("Parent process (PID: %d) sum of even numbers: %d\n", getpid(), evenSum);
    } else if (pid == 0) {
        for (int i = 0; i < n; i++) {
            if (arr[i] % 2 != 0) {
                oddSum += arr[i];
            }
        }
        printf("Child process (PID: %d) sum of odd numbers: %d\n", getpid(), oddSum);
        exit(0);
    } else {
        perror("Fork failed");
    }
}

int main() {
    int choice;
    do {
        printf("\nMenu:\n");
        printf("1. Create a Zombie Process\n");
        printf("2. Create an Orphan Process\n");
        printf("3. Calculate Sum of Even/Odd Numbers\n");
        printf("4. Exit\n");
        printf("Enter your choice: ");
        scanf("%d", &choice);

        switch (choice) {
            case 1:
                createZombieProcess();
                break;
            case 2:
                createOrphanProcess();
                break;
            case 3:
                calculateSum();
                break;
            case 4:
                printf("Exiting program.\n");
                break;
            default:
                printf("Invalid choice, please try again.\n");
        }
    } while (choice != 4);

    return 0;
}
