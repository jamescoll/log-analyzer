package com.creditsuisse.loganalyzer;

import com.creditsuisse.loganalyzer.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, String> {
}
