package com.spaces.driftmark.concurrency;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class ReaderProducer implements Runnable {
    private BlockingQueue<QueueWorker> queue;
    private final int MAX_SIZEOF_LIST = 500;
    private String tempStr = null;
    private String[] test = null;
    private final int POISON_PILLS;

    public ReaderProducer(BlockingQueue<QueueWorker> queue, int POISON_PILLS) {
        this.queue = queue;
        this.POISON_PILLS = POISON_PILLS;
    }

    @Override
    public void run() {
        try {
            System.out.println("Start"+Instant.now());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\gear\\smallfile.txt"), "UTF-8"));
            List<QueueModel> additionList = new ArrayList<>();
            int counter = 0;
            int totalCounter = 0;
            while ((tempStr = reader.readLine()) != null) {
                counter++;
                totalCounter++;
                test = tempStr.split("<hide>");
                additionList.add(new QueueModel(test[0], test[1]));
                if (counter == MAX_SIZEOF_LIST) {
                    QueueModel[] arr = new QueueModel[additionList.size()];
                    arr = additionList.toArray(arr);
                    queue.put(new QueueWorker(arr));
                    counter = 0;
                    additionList.clear();
                }
            }
            QueueModel[] arr = new QueueModel[additionList.size()];
            arr = additionList.toArray(arr);
            queue.put(new QueueWorker(arr));
            System.out.println("Total count fo elemetns from producer: " + totalCounter);

            for (int i = 0; i < POISON_PILLS; i++) {
                queue.put(new QueueWorker(true));
            }
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            Thread.currentThread().interrupt();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
