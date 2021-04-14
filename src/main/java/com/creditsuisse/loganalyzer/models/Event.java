package com.creditsuisse.loganalyzer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class Event {
    @Id
    private String id;
    private long duration;
    private String type;
    private String host;
    private boolean alert;

    public Event(LogEntry entry, long duration) {
        this.id = entry.getId();
        this.duration = duration;
        this.alert = duration > 4;
        this.type = entry.getType();
        this.host = entry.getHost();
    }

}
