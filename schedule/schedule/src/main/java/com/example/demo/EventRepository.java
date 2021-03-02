package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called ContractRepository
// CRUD refers Create, Read, Update, Delete

public interface EventRepository extends JpaRepository<Event, String> {

    public void deleteByEventID(int eventID); //eventID - primary key in the Event table
    public List<Event> findByDateAndTraineeName(String date, String traineeName);
}
