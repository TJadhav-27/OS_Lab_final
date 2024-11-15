//input: 4
#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <unistd.h>
#include <time.h>

#define N 5
#define THINKING 0
#define HUNGRY 1
#define EATING 2

pthread_mutex_t mutex;
pthread_cond_t cond[N];
int states[N];

void thinking(int philNumber)
{
    states[philNumber] = THINKING;
    printf("\nPhilosopher %d is thinking.\n", philNumber);
    usleep(rand() % 1000000);
}

void test(int philNumber)
{
    if (states[philNumber] == HUNGRY &&
        states[(philNumber + 1) % N] != EATING &&
        states[(philNumber - 1 + N) % N] != EATING)
    {
        states[philNumber] = EATING;
        printf("\nPhilosopher %d starts eating.\n", philNumber);
        pthread_cond_signal(&cond[philNumber]);
    }
}

void takeForks(int philNumber)
{
    pthread_mutex_lock(&mutex);
    states[philNumber] = HUNGRY;
    printf("\nPhilosopher %d is hungry.\n", philNumber);
    test(philNumber);
    pthread_mutex_unlock(&mutex);
}

void eating(int philNumber)
{
    pthread_mutex_lock(&mutex);
    while (states[philNumber] != EATING)
        pthread_cond_wait(&cond[philNumber], &mutex);
    printf("\nPhilosopher %d is eating.\n", philNumber);
    usleep(rand() % 1000000);
    pthread_mutex_unlock(&mutex);
}

void putForks(int philNumber)
{
    pthread_mutex_lock(&mutex);
    states[philNumber] = THINKING;
    printf("\nPhilosopher %d has finished eating.\n", philNumber);
    test((philNumber + 1) % N);
    test((philNumber - 1 + N) % N);
    pthread_mutex_unlock(&mutex);
}

void *philosopher(void *args)
{
    int philNumber = *(int *)args;
    thinking(philNumber);
    takeForks(philNumber);
    eating(philNumber);
    putForks(philNumber);
}

int main()
{
    srand(time(NULL));
    pthread_t threads[N];
    int philNumber[N];

    pthread_mutex_init(&mutex, NULL);
    for (int i = 0; i < N; i++) {
        pthread_cond_init(&cond[i], NULL);
    }

    for (int i = 0; i < N; i++)
    {
        philNumber[i] = i;
        pthread_create(&threads[i], NULL, philosopher, &philNumber[i]);
    }

    for (int i = 0; i < N; i++)
    {
        pthread_join(threads[i], NULL);
    }

    for (int i = 0; i < N; i++) {
        pthread_cond_destroy(&cond[i]);
    }
    pthread_mutex_destroy(&mutex);
    return 0;
}
