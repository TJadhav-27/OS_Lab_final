//input:
for information: 4
1
4
2
4
3
5

for communication:3
1
3
2
5

for process: 1
1
1
3
1
5
1
4
1
2

for file: 2
1
2
2
2
3
5

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <errno.h>
#include <signal.h>
#include <sys/utsname.h>

void fork_example();
void exit_example();
void wait_example();
void kill_example();
void exec_example();

void open_read_write_example();
void link_unlink_example();
void stat_example();

void pipe_example();
void fifo_example();

void getpid_example();
void getppid_example();
void uname_example();

int main() {
    int main_choice;
    int sub_choice;

    while (1) {
        printf("\nMenu Driven System Call Demonstration\n");
        printf("1. Process related system calls\n");
        printf("2. File related system calls\n");
        printf("3. Communication related system calls\n");
        printf("4. Information related system calls\n");
        printf("5. Exit\n");
        printf("Enter your choice:\n");
        scanf("%d", &main_choice);

        switch (main_choice) {
            case 1:
                printf("1. Fork\n2. Exit\n3. Wait\n4. Kill\n5. Exec\n");
                printf("Enter your choice:\n");
                scanf("%d", &sub_choice);
                switch (sub_choice) {
                    case 1: fork_example(); break;
                    case 2: exit_example(); break;
                    case 3: wait_example(); break;
                    case 4: kill_example(); break;
                    case 5: exec_example(); break;
                    default: printf("Invalid choice! Please try again.\n"); break;
                }
                break;
            case 2:
                printf("1. Open, Read, Write, Close\n2. Link, Unlink\n3. Stat\n");
                printf("Enter your choice:\n");
                scanf("%d", &sub_choice);
                switch (sub_choice) {
                    case 1: open_read_write_example(); break;
                    case 2: link_unlink_example(); break;
                    case 3: stat_example(); break;
                    default: printf("Invalid choice! Please try again.\n"); break;
                }
                break;
            case 3:
                printf("1. Pipe\n2. FIFO\n");
                printf("Enter your choice:\n");
                scanf("%d", &sub_choice);
                switch (sub_choice) {
                    case 1: pipe_example(); break;
                    case 2: fifo_example(); break;
                    default: printf("Invalid choice! Please try again.\n"); break;
                }
                break;
            case 4:
                printf("1. Get PID\n2. Get PPID\n3. Uname\n");
                printf("Enter your choice:\n");
                scanf("%d", &sub_choice);
                switch (sub_choice) {
                    case 1: getpid_example(); break;
                    case 2: getppid_example(); break;
                    case 3: uname_example(); break;
                    default: printf("Invalid choice! Please try again.\n"); break;
                }
                break;
            case 5:
                printf("Exiting program.\n");
                exit(0);
            default:
                printf("Invalid choice! Please try again.\n");
                break;
        }
    }
    return 0;
}

void fork_example() {
    pid_t pid = fork();
    if (pid == 0) {
        printf("Child process: PID = %d\n", getpid());
        exit(0);
    } else if (pid > 0) {
        printf("Parent process: PID = %d\n", getpid());
        wait(NULL);
    } else {
        perror("Fork failed");
    }
}

void exit_example() {
    printf("This process will terminate using exit().\n");
    exit(0);
}

void wait_example() {
    pid_t pid = fork();
    if (pid == 0) {
        printf("Child process running. PID = %d\n", getpid());
        sleep(1);
        exit(0);
    } else if (pid > 0) {
        printf("Parent waiting for child to terminate.\n");
        wait(NULL);
        printf("Child terminated.\n");
    } else {
        perror("Fork failed");
    }
}

void kill_example() {
    pid_t pid = fork();
    if (pid == 0) {
        printf("Child process running. PID = %d\n", getpid());
        while (1);
    } else if (pid > 0) {
        sleep(1);
        printf("Killing child process.\n");
        kill(pid, SIGKILL);
        wait(NULL);
        printf("Child process killed.\n");
    } else {
        perror("Fork failed");
    }
}

void exec_example() {
    pid_t pid = fork();
    if (pid == 0) {
        printf("Child process: Executing 'ls' using execl\n");
        execl("/bin/ls", "ls", "-l", NULL);
        perror("execl failed");
        exit(1);
    } else if (pid > 0) {
        wait(NULL);
        printf("Parent process: Child process completed\n");
    } else {
        perror("Fork failed");
    }
}

void open_read_write_example() {
    int fd;
    char buffer[100];
    fd = open("demo_output.txt", O_CREAT | O_RDWR, 0644);
    if (fd == -1) {
        perror("Error opening file");
        return;
    }
    write(fd, "Hello, World!\n", 14);
    lseek(fd, 0, SEEK_SET);
    read(fd, buffer, sizeof(buffer) - 1);
    buffer[99] = '\0';
    printf("File content: %s\n", buffer);
    close(fd);
}

void link_unlink_example() {
    if (link("demo_output.txt", "testfile_link.txt") == 0) {
        printf("Link created.\n");
    } else {
        perror("Error creating link");
    }
    if (unlink("testfile_link.txt") == 0) {
        printf("Link removed.\n");
    } else {
        perror("Error removing link");
    }
}

void stat_example() {
    struct stat fileStat;
    if (stat("demo_output.txt", &fileStat) < 0) {
        perror("Error getting file stats");
        return;
    }
    printf("Information for demo_output.txt:\n");
    printf("File Size: %ld bytes\n", fileStat.st_size);
    printf("Number of Links: %ld\n", fileStat.st_nlink);
    printf("File inode: %ld\n", fileStat.st_ino);
    printf("File Permissions: ");
    printf((S_ISDIR(fileStat.st_mode)) ? "d" : "-");
    printf((fileStat.st_mode & S_IRUSR) ? "r" : "-");
    printf((fileStat.st_mode & S_IWUSR) ? "w" : "-");
    printf((fileStat.st_mode & S_IXUSR) ? "x" : "-");
    printf("\n");
}

void pipe_example() {
    int pipefds[2];
    char buffer[100];

    if (pipe(pipefds) == -1) {
        perror("Pipe creation failed");
        exit(1);
    }

    if (fork() == 0) {
        close(pipefds[0]);
        write(pipefds[1], "Hello from child!\n", 18);
        close(pipefds[1]);
        exit(0);
    } else {
        close(pipefds[1]);

        ssize_t bytesRead = read(pipefds[0], buffer, sizeof(buffer) - 1);

        if (bytesRead == -1) {
            perror("Failed to read from pipe");
        } else {
            buffer[bytesRead] = '\0';
            printf("Parent received: %s\n", buffer);
        }

        close(pipefds[0]);
        wait(NULL);
    }
}

void fifo_example() {
    char *fifo = "/tmp/myfifo";
    mkfifo(fifo, 0666);

    if (fork() == 0) {
        int fd = open(fifo, O_WRONLY);
        if (fd == -1) {
            perror("Failed to open FIFO in child");
            exit(1);
        }
        write(fd, "Hello via FIFO!\n", 16);
        close(fd);
        exit(0);
    } else {
        char buffer[100];
        int fd = open(fifo, O_RDONLY);
        if (fd == -1) {
            perror("Failed to open FIFO in parent");
            exit(1);
        }
        ssize_t bytesRead = read(fd, buffer, sizeof(buffer) - 1);

        if (bytesRead == -1) {
            perror("Failed to read from FIFO");
        } else {
            buffer[bytesRead] = '\0';
            printf("Parent received: %s\n", buffer);
        }

        close(fd);
        wait(NULL);
    }

    unlink(fifo);
}

void getpid_example() {
    printf("Process ID: %d\n", getpid());
}

void getppid_example() {
    printf("Parent Process ID: %d\n", getppid());
}

void uname_example() {
    struct utsname sysinfo;
    if (uname(&sysinfo) == 0) {
        printf("System information:\n");
        printf("Sysname: %s\n", sysinfo.sysname);
        printf("Nodename: %s\n", sysinfo.nodename);
        printf("Release: %s\n", sysinfo.release);
        printf("Version: %s\n", sysinfo.version);
        printf("Machine: %s\n", sysinfo.machine);
    } else {
        perror("Error fetching system info");
    }
}


