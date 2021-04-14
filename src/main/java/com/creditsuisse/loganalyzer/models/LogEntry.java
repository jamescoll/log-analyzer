package com.creditsuisse.loganalyzer.models;

import lombok.Data;

@Data
public class LogEntry {
    private final String id;
    private final String state;
    private final long timestamp;
    private final String type;
    private final String host;

    LogEntry() {
        this.id = null;
        this.state = null;
        this.timestamp = 0L;
        this.type = null;
        this.host = null;
    }

    public LogEntry(String id, String state, long timestamp, String type, String host) {
        this.id = id;
        this.state = state;
        this.timestamp = timestamp;
        this.type = type;
        this.host = host;
    }
}
