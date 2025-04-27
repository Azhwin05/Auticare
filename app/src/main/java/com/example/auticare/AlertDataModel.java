package com.example.auticare;

public class AlertDataModel {
    private long timestamp;
    private float value;

    public AlertDataModel() {}

    public AlertDataModel(long timestamp, float value) {
        this.timestamp = timestamp;
        this.value = value;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public float getValue() {
        return value;
    }
}
