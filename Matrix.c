#include <stdio.h>
#include <pthread.h>
#include <stdlib.h>

#define MAX 10 

int A[MAX][MAX], B[MAX][MAX], C[MAX][MAX], rows, cols;

void* matrix_add(void* arg) {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            C[i][j] = A[i][j] + B[i][j];
        }
    }
    printf("Matrix after addition:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }
    pthread_exit(NULL);
}


void* matrix_subtract(void* arg) {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            C[i][j] = A[i][j] - B[i][j];
        }
    }
    printf("Matrix after subtraction:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }
    pthread_exit(NULL);
}


void* matrix_multiply(void* arg) {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            C[i][j] = 0;
            for (int k = 0; k < cols; k++) {
                C[i][j] += A[i][k] * B[k][j];
            }
        }
    }
    printf("Matrix after multiplication:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }
    pthread_exit(NULL);
}


void* matrix_divide(void* arg) {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            if (B[i][j] != 0)
                C[i][j] = A[i][j] / B[i][j];
            else
                C[i][j] = 0;  
        }
    }
    printf("Matrix after division:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%d ", C[i][j]);
        }
        printf("\n");
    }
    pthread_exit(NULL);
}

void* matrix_transpose(void* arg) {
    int transpose[rows][cols];
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            transpose[j][i] = A[i][j];
        }
    }
    printf("Matrix after transpose:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%d ", transpose[i][j]);
        }
        printf("\n");
    }
    pthread_exit(NULL);
}

void display_matrix(int matrix[MAX][MAX]) {
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            printf("%d ", matrix[i][j]);
        }
        printf("\n");
    }
}

int main() {
    pthread_t threads[5];

    printf("Enter the number of rows and columns of matrices: ");
    scanf("%d %d", &rows, &cols);

    printf("Enter elements of matrix A:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            scanf("%d", &A[i][j]);
        }
    }

    printf("Enter elements of matrix B:\n");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            scanf("%d", &B[i][j]);
        }
    }

    printf("Matrix A:\n");
    display_matrix(A);
    printf("Matrix B:\n");
    display_matrix(B);
    
    pthread_create(&threads[0], NULL, matrix_add, NULL);
    pthread_create(&threads[1], NULL, matrix_subtract, NULL);
    pthread_create(&threads[2], NULL, matrix_multiply, NULL);
    pthread_create(&threads[3], NULL, matrix_divide, NULL);
    pthread_create(&threads[4], NULL, matrix_transpose, NULL);

    for (int i = 0; i < 5; i++) {
        pthread_join(threads[i], NULL);
    }

    return 0;
}