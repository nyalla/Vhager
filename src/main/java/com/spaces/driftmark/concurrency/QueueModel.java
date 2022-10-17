package com.spaces.driftmark.concurrency;

public class QueueModel {
    private String personId;
    private String personDesc;

    public QueueModel() {
    }

    public QueueModel(String personId, String personDesc) {
        this.personId = personId;
        this.personDesc = personDesc;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonDesc() {
        return personDesc;
    }

    public void setPersonDesc(String personDesc) {
        this.personDesc = personDesc;
    }
}
