package com.spaces.driftmark.concurrency;

import com.spaces.driftmark.service.DatapusherCon;

import java.sql.Connection;

/**
 * Handles the Items and operation agaisnt the collection of items
 */
public class QueueWorker {

    private QueueModel[] itemsHolder;
    private boolean POISON_PILL = false;


    public QueueWorker(QueueModel[] itemsHolder) {
        this.itemsHolder = itemsHolder;
    }

    public QueueWorker(boolean POISON_PILL) {
        this.POISON_PILL = POISON_PILL;
    }

    public boolean isPOISON_PILL() {
        return POISON_PILL;
    }

    public QueueModel[] getItemsHolder() {
        return itemsHolder;
    }

    /***
     * `Custom operations like Database operation for the given collection of items(items resides under itemHolder)
     * @param connection
     */
    public void insert(Connection connection) {
        //System.out.println("Insert Loop with new Thread BEGIN" + Thread.currentThread().getName()+" with list size: " +getItemsHolder().length);
        /*DataPusherService pusherService = new DataPusherService();
        pusherService.insertIntoDatabase(Arrays.asList(getItemsHolder().clone()));*/
        new DatapusherCon().insertNew(getItemsHolder(), connection);
        //System.out.println("Insert Loop with new Thread END" + Thread.currentThread().getName()+" with list size: " +getItemsHolder().length);
        //System.out.println(Instant.now());
        //System.out.println("Thread of " + Thread.currentThread().getName()+", with connection"+connection.toString());
    }

}
