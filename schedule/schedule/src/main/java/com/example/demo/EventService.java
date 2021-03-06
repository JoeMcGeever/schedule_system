package com.example.demo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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

    public List<Event> getWeeklyEvents(LocalDateTime recentMonday, String username) {
        //startDate = current date + 7*weekNumber days
        //endDate = startDate + 7 days (maybe do before 8 days)
        //find all events between start and end with username
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd"); 
        
        String currentDate = dtf.format(recentMonday);  //needs to always show last monday
        String weekEnd = dtf.format(recentMonday.plusDays(6)); //gets the end of the week

        return repo.findAllByDateLessThanEqualAndDateGreaterThanEqualAndTraineeName(weekEnd, currentDate, username);
    }

    public LocalDateTime getWeekCommencingDate(int weekNumber){
        LocalDateTime now = LocalDateTime.now();

        if(weekNumber<0){ // if the page is below 0
            weekNumber += 1 ; // Add one to work with plusDays - if weekNumber==-1 set weekNumber to 0
        }

        System.out.println(now.getDayOfWeek());
        while(now.getDayOfWeek() != DayOfWeek.MONDAY){ //get the most recent monday
            now = now.minusDays(1);
            System.out.println(now); 
            System.out.println(now.getDayOfWeek()); 
        }
        now = now.plusDays((weekNumber-1) * 7); //add on a week depending on page (week 1 should not add any, 2 should add 7 etc)

        return now;
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

