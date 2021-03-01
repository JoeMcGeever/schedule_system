package com.example.demo;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class EventService {

    @Autowired
    private EventRepository repo;

    // public List<Event> listAll() {
    //     return repo.findAll();
    // }

    public List<Event> getEvents(int weekNumber, String username) {
             return repo.findAll();
    }

    public void save(Event eventInstance) {
        repo.save(eventInstance);
    }

        
    // public void delete(String name) {
    //     repo.deleteByUsername(name); //username - primary key in the User table
    // }          
}

