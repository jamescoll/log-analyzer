package com.creditsuisse.loganalyzer.service;

import com.creditsuisse.loganalyzer.EventRepository;
import com.creditsuisse.loganalyzer.models.Event;
import com.creditsuisse.loganalyzer.models.LogEntry;
import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogEntryReaderService {

    private final Map<String, LogEntry> logEntryMap;
    Logger logger = LoggerFactory.getLogger(LogEntryReaderService.class);
    @Autowired
    private EventRepository eventRepository;

    @Value("${file.path}")
    private String path;

    {
        logEntryMap = new HashMap<>();
    }

    public void read() throws IOException {

        //so the process here is
        //1. use apache commons io as it can cater for very large files
        //2. use a map to store log entries until they repeat
        //3. when they repeat extract the event and write to db
        //4. debug log the event write

        try (LineIterator iterator = FileUtils.lineIterator(new File(path), "UTF-8")) {
            while (iterator.hasNext()) {
                String line = iterator.nextLine();
                Gson gson = new Gson();
                if (!line.isEmpty()) {
                    LogEntry logEntry = gson.fromJson(line, LogEntry.class);
                    if (logEntryMap.containsKey(logEntry.getId())) {
                        LogEntry pairLogEntry = logEntryMap.get(logEntry.getId());
                        long duration = Math.abs(logEntry.getTimestamp() - pairLogEntry.getTimestamp());
                        Event event = new Event(logEntry, duration);
                        logger.debug("LogEntryReaderService: adding event to database: " + event.toString());
                        eventRepository.save(event);
                        logEntryMap.remove(pairLogEntry.getId());
                    } else {
                        logEntryMap.put(logEntry.getId(), logEntry);
                    }
                }

            }
        }
    }
}
