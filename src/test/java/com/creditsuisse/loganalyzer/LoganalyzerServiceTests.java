package com.creditsuisse.loganalyzer;

import com.creditsuisse.loganalyzer.models.Event;
import com.creditsuisse.loganalyzer.service.LogEntryReaderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(properties = {"file.path=input/test_file"})
class LoganalyzerServiceTests {

    @Autowired
    private LogEntryReaderService logEntryReaderService;

    @Autowired
    private EventRepository eventRepository;

    @BeforeEach
    public void init() throws IOException {
        logEntryReaderService.read();
    }

    @Test
    public void dbIsPopulated() {
        assertNotEquals(0, eventRepository.count());
    }

    @Test
    public void dbIsPopulatedCorrectly() {
        assertEquals(3, eventRepository.count());
    }

    @Test
    public void alertOnCorrectValue() {
        Optional<Event> event = eventRepository.findById("scsmbstgrc");
        event.ifPresent(e -> assertTrue(e.isAlert()));
    }

    @Test
    public void alertNotOnIncorrectValue() {
        Optional<Event> event = eventRepository.findById("scsmbstgrb");
        event.ifPresent(e -> assertFalse(e.isAlert()));
    }

    @Test
    public void typeAndHostValuesAreReadWhereApplicable() {
        Optional<Event> event = eventRepository.findById("scsmbstgra");
        event.ifPresent(e -> assertFalse(e.getType().isEmpty()));
        event.ifPresent(e -> assertFalse(e.getHost().isEmpty()));
    }
}
