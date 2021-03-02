package com.example.demo;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.transaction.Transactional;

import org.dom4j.rule.NullAction;
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

    public String save(Event eventInstance) { //returns true if successfully adds the event
        String errorMessage = checkEventClash(eventInstance);
        if(errorMessage==null){
            repo.save(eventInstance);
            return null;
        }else{
            return errorMessage;
        }
    }

    public String checkEventClash(Event eventInstance){ // returns true if no timetable clash
        List<Event> events = repo.findByDateAndTraineeName(eventInstance.getDate(), eventInstance.getTrainee());

        LocalTime eventTimeStart = LocalTime.parse( eventInstance.getTime() ) ; //gets the starting time of the users new event
        LocalTime eventTimeEnd = eventTimeStart.plus(eventInstance.getDuration(), ChronoUnit.MINUTES); //gets the ending time

        for (int i = 0; i < events.size(); i++) { //loop through all events the user already has on that day
            LocalTime iTime = LocalTime.parse( events.get(i).getTime() ) ; //get the current loop instance starting time
            LocalTime iEndTime = iTime.plus(events.get(i).getDuration(), ChronoUnit.MINUTES); //get the duration


            if(eventTimeStart == iTime || eventTimeEnd == iEndTime){ //is before and is after do not include if they are the same, so breaks logic
                return events.get(i).getTopic();
            }

            Boolean hasOverlap = !eventTimeEnd.isBefore(iTime) && !eventTimeStart.isAfter(iEndTime); //checks to see if there is an overlap
            //The above negates the only two cases when there isnt an overlap. End of one is before the start of the other, and the mirror.



            if(hasOverlap){
                System.out.println("Clash!");
                return events.get(i).getTopic(); //return the topic name that 
            }
          }

        return null; //return null if there is no clash
    }

        
    // public void delete(String name) {
    //     repo.deleteByUsername(name); //username - primary key in the User table
    // }          
}

