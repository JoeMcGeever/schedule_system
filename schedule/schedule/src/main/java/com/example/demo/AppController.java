package com.example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Controller
public class AppController {



    @Autowired
    private EventService event_service;
    @Autowired
    private TraineeService trainee_service;
    @Autowired
    private LearnerService learner_service;

    @RequestMapping("/")
    public String viewHomePage(Model model, Authentication authentication, @RequestParam(value="page", required = false) Integer page) {

        User user = trainee_service.getUser(authentication.getName());

        

        if(user.getDtype().equals("trainee")){

            //here is the plan:
            //get from the layer: all events for currently selected week (footer)
            if(page == null){
                page = 1;
            }    

            LocalDate lastMonday = event_service.getWeekCommencingDate(page); //gets the last monday date
            List<Event>[] weeklyEvents = event_service.getWeeklyEvents(lastMonday, (authentication.getName())); //sends to the getWeeklyEvents function

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //format the last monday date to display on page
            String weekCommencing = dtf.format(lastMonday);  

            System.out.println(weeklyEvents.length);

            model.addAttribute("footer", page); //send current page number
            model.addAttribute("weekCommencing", "Week commencing: " + weekCommencing); //send week commencing information

            
            model.addAttribute("mondayEvents", weeklyEvents[0]); //send events for each day to be displayed
            model.addAttribute("tuesdayEvents", weeklyEvents[1]); //send events for each day to be displayed
            model.addAttribute("wednesdayEvents", weeklyEvents[2]); //send events for each day to be displayed
            model.addAttribute("thursdayEvents", weeklyEvents[3]); //send events for each day to be displayed
            model.addAttribute("fridayEvents", weeklyEvents[4]); //send events for each day to be displayed
            model.addAttribute("saturdayEvents", weeklyEvents[5]); //send events for each day to be displayed
            model.addAttribute("sundayEvents", weeklyEvents[6]); //send events for each day to be displayed
            
            return "index";
        } else {
            return "learner_index";
        }

    }

    @RequestMapping("/login")
    public String viewLoginPage(Model model) {
        return "login";
    }

    @RequestMapping("/login-error")  
    public String loginError(Model model) {  
        model.addAttribute("loginError", true);  
        return "login.html";  
    }  


    @RequestMapping("/add")  
    public String add(Model model) {   
        return "add.html";  
    }  

    //to save a new event to the database      
    @RequestMapping(value = "/add", method = RequestMethod.POST) 
    public String saveFullTimeUser(@ModelAttribute("event") Event event, Authentication authentication, Model model){    
        
        event.setTrainee(authentication.getName()); //set the trainee name 
        
        String errorMessage = event_service.save(event);
        if(errorMessage != null){ //if the saving fails, and false is returned
            model.addAttribute("addError", true);
            model.addAttribute("addErrorMessage", "Error: <br>This clashes with your '" + errorMessage + "' class!<p></p>");
            return "add.html";
        } //save to Event table
        //if success:        
        return "redirect:/"; //return to index page  
        //otherwise, return an error -- load add.html but add a true statement to an error value in model.
        //this error could be if there is a timetabling clash
    }

    @RequestMapping("/details")
    public String viewLoginPage(Model model, Authentication authentication, @RequestParam(value="id", required = true) int eventID) {
        String username = authentication.getName();
        
        Event eventDetails = event_service.getEvent(eventID);

        if(!eventDetails.getTrainee().equals(username)){ //if the user is not the creator of the event, return to index
            return "redirect:/";
        }

        
        model.addAttribute("eventDetails", eventDetails); //add the event details

        List<Learner> attendenceList = learner_service.getAttendance(eventID); //get all attending learners

        System.out.println(attendenceList.size());
        
        for(int i = 0; i < attendenceList.size(); i++){
            System.out.println(attendenceList.get(i).getUsername());
        }
        
        model.addAttribute("attendenceList", attendenceList);


        //need to also get the users already signed up ---> user service
        //return "details";
        return "index";
    }

       

}
