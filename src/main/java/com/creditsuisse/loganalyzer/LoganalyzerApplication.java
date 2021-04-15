package com.creditsuisse.loganalyzer;

import com.creditsuisse.loganalyzer.service.LogEntryReaderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoganalyzerApplication implements ApplicationRunner {

    Logger logger = LoggerFactory.getLogger(LoganalyzerApplication.class);

    @Autowired
    private LogEntryReaderService logEntryReaderService;

    public static void main(String[] args) {

        SpringApplication.run(LoganalyzerApplication.class, args);

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("LoganalyzerApplication: Starting log entry parsing service");
        logEntryReaderService.read();

    }
}
