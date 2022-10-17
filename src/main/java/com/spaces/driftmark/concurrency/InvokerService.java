package com.spaces.driftmark.concurrency;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class InvokerService {
    public static void main(String[] args) {
        int BOUND = 100;
        int N_PRODUCERS = 1;
        int N_CONSUMERS = Runtime.getRuntime().availableProcessors();
        BlockingQueue<QueueWorker> queue = new LinkedBlockingQueue<>(BOUND);

        for (int i = 1; i <= N_PRODUCERS; i++) {
            new Thread(new ReaderProducer(queue,N_CONSUMERS)).start();
        }

        for (int j = 0; j < N_CONSUMERS; j++) {
            new Thread(new WriterConsumer(queue)).start();
        }
    }
}
