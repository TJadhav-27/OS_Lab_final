//input: 3
3
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>
#include <unistd.h>
#include <time.h>

#define BUFFER_SIZE 5  

int buffer[BUFFER_SIZE];  
int count = 0;            

sem_t empty;
sem_t full;   
sem_t s;  

void displayBuffer() {
    printf("[ ");
    for (int i = 0; i < BUFFER_SIZE; i++) {
        if (i < count)
            printf("%d ", buffer[i]);
        else
            printf("- "); 
    }
    printf("] ");
    
    if (count == 0) {
        printf("(Buffer is empty)\n");
    } else if (count == BUFFER_SIZE) {
        printf("(Buffer is full)\n");
    } else {
        printf("\n");
    }
}

int produceItem() {
    return rand() % 100;
}

void addElementToBuffer(int item) {
    buffer[count] = item;  
    printf("\nElement %d has been added to buffer\n", item);
    count++;
}

void* producer(void* arg) {
    int id = *(int*)arg;
    while (1) {
        int item = produceItem();  
        printf("\nProducer %d is trying to access critical section\n", id);

        sem_wait(&empty); 
        sem_wait(&s);  

        printf("\nProducer %d has got access to critical section\n", id);
        addElementToBuffer(item);
        printf("\nProducer %d produced item: %d\n", id, item);
        printf("Buffer after adding element:");
        displayBuffer();  

        printf("\nProducer %d has left the critical section\n", id);
        sem_post(&full);
        sem_post(&s);  

        usleep(rand() % 1000000);
    }
}

int removeElementFromBuffer() {
    count--;
    int item = buffer[count];  
    return item;
}

void consumeItem(int item, int id) {
    printf("\nConsumer %d consumed item: %d\n", id, item);
}

void* consumer(void* arg) {
    int id = *(int*)arg;
    while (1) {
        printf("\nConsumer %d is trying to access critical section\n", id);

        sem_wait(&full);  
        sem_wait(&s);  

        printf("\nConsumer %d has got access to critical section\n", id);
        
        int item = removeElementFromBuffer();  
        printf("Buffer after removing element:");
        displayBuffer();  

        printf("\nConsumer %d has left the critical section\n", id);
        sem_post(&empty);  
        sem_post(&s);  
        consumeItem(item, id);

        usleep(rand() % 1000000);
    }
}

int main() {
    srand(time(NULL));
    int numProducers, numConsumers;
    printf("Enter the number of producers:\n");
    scanf("%d", &numProducers);
    printf("Enter the number of consumers:\n");
    scanf("%d", &numConsumers);

    pthread_t prod_threads[numProducers];
    pthread_t cons_threads[numConsumers];
    int prod_ids[numProducers];
    int cons_ids[numConsumers];

    sem_init(&empty, 0, BUFFER_SIZE);  
    sem_init(&full, 0, 0); 
    sem_init(&s, 0, 1);

    for (int i = 0; i < numProducers; i++) {
        prod_ids[i] = i + 1;
        pthread_create(&prod_threads[i], NULL, producer, &prod_ids[i]);
    }

    for (int i = 0; i < numConsumers; i++) {
        cons_ids[i] = i + 1;
        pthread_create(&cons_threads[i], NULL, consumer, &cons_ids[i]);
    }

    for (int i = 0; i < numProducers; i++) {
        pthread_join(prod_threads[i], NULL);
    }

    for (int i = 0; i < numConsumers; i++) {
        pthread_join(cons_threads[i], NULL);
    }

    sem_destroy(&empty);
    sem_destroy(&full);
    sem_destroy(&s);

    return 0;
}
