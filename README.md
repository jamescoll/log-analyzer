# log-analyzer

This is a very brief service which:
* Parses log files based in the input folder
* Calculates time durations between log events
* Writes those events to a HSQLDB

It uses the Spring application runner to launch the service as this was the fastest way to implement this. With more time, better test data and more resources more interesting methods could have been explored.

## How to run

To run this application, after cloning this repository, execute

*mvn spring-boot:run -Dspring-boot.run.arguments=--file.path=input/sample_file*

## Dependencies

This application uses:

* GSON - for parsing/extracting file lines from JSON into objects
* Apache commons io - for scaling to very large file input sizes without issues
* Spring Boot

## Tests

To run the application tests, execute

*mvn test*



