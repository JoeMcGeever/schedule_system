package com.example.demo;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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

    public List<Event>[] getWeeklyEvents(LocalDate recentMonday, String username) { 
    //returns the list of lists of each day events for the given week: monday = 0, tue=1 etc
        List<Event>[] weeklyEvents = (List<Event>[]) new List[7];
        for(int j = 0; j<7; j++){ 
            weeklyEvents[j] = new ArrayList<Event>(); 
        }//create empty lists of events in all 7 array slots (one for each day of the week)
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd"); //changed from yyyy/MM/dd
        String currentDate = dtf.format(recentMonday);  //needs to always show last monday
        String weekEnd = dtf.format(recentMonday.plusDays(6)); //gets the end of the week
        List<Event> allEvents = repo.findAllByDateLessThanEqualAndDateGreaterThanEqualAndTraineeName(weekEnd, currentDate, username);
        //make the JPA request
        for(int i = 0; i<allEvents.size(); i++){ //for each event
            LocalDate instanceDate = LocalDate.parse(allEvents.get(i).getDate(), dtf); //get the date object
            int daysBetween = (int) ChronoUnit.DAYS.between(recentMonday, instanceDate);
            //get the number of days from this weeks monday and therefore the index for where I want to place the event
            weeklyEvents[daysBetween].add(allEvents.get(i)); //add at the relevant index 
        }
        return weeklyEvents;
    }

    public LocalDate getWeekCommencingDate(int weekNumber){
        LocalDate now = LocalDate.now();

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


    public String save(Event eventInstance) { //returns null if successfully adds the event

    
        setEndTime(eventInstance);
        String errorMessage = checkEventClash(eventInstance); 
        //checks if the event clashes with another event the user has created
        if(errorMessage==null){
            repo.save(eventInstance); //calls the save function in the repo
            return null;
        }else{
            return errorMessage;
        }
    }

    public void setEndTime(Event eventInstance){
        LocalTime eventTimeStart = LocalTime.parse( eventInstance.getTime() ) ; //gets the starting time of the users new event
        LocalTime eventTimeEnd = eventTimeStart.plus(eventInstance.getDuration(), ChronoUnit.MINUTES); //gets the ending time
        String endTime = eventTimeEnd.toString();
        eventInstance.setEndTime(endTime);
    }

    public String checkEventClash(Event eventInstance){ // returns true if no timetable clash

        System.out.println("check event clash here");
        List<Event> events = repo.findByDateAndTraineeName(eventInstance.getDate(), eventInstance.getTrainee());
        
        LocalTime eventTimeStart = LocalTime.parse( eventInstance.getTime() ) ; //gets the starting time of the users new event
        LocalTime eventTimeEnd = LocalTime.parse( eventInstance.getEndTime() ) ; //gets the ending time
        for (int i = 0; i < events.size(); i++) { //loop through all events the user already has on that day



            if(eventInstance.eventID() == events.get(i).eventID()){ //for edit -> if they have the same id, then do not worry about clashing
                continue;
            }

            
            System.out.println(events.get(i).getTopic() + " is an event on this day: " + events.get(i).getTime() + " - " + events.get(i).getEndTime());
            System.out.println("VS");
            System.out.println(eventInstance.getTopic() + " is the new event: " + eventInstance.getTime() + " - " + eventInstance.getEndTime());



            LocalTime iTime = LocalTime.parse( events.get(i).getTime() ) ; //get the current loop instance starting time
            LocalTime iEndTime = LocalTime.parse(events.get(i).getEndTime()); //get the duration
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

        
    public void delete(int eventID) {
        repo.deleteByEventID(eventID);; //username - primary key in the User table
    }          

    public Event getEvent(int eventID){
        return repo.findByEventID(eventID);
    }





    public List<Event> getTomorrowsEvents(String traineeName) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        String tomorrowsDate = dtf.format(tomorrow); 
        
        
        return repo.findAllByTraineeNameAndDate(traineeName, tomorrowsDate);
    }


    public void purgeEvents(LocalDate date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = dtf.format(date);
        repo.deleteByDateBefore(dateString);
        return;
    }


}

