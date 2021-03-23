package com.example.demo;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;


public interface EventRepository extends JpaRepository<Event, String> {

    public void deleteByEventID(int eventID); //eventID - primary key in the Event table
    public Event findByEventID(int eventID);
    public List<Event> findByDateAndTraineeName(String date, String traineeName);
    
    public List<Event> findAllByDateLessThanEqualAndDateGreaterThanEqualAndTraineeName(String endDate, String startDate, String traineeName);

    @Modifying
    public void deleteByDateBefore(String date);

    
    public List<Event> findAllByTraineeNameAndDate(String traineeName, String date); //find all events a trainee has for a given day

}
