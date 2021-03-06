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

            LocalDateTime lastMonday = event_service.getWeekCommencingDate(page); //gets the last monday date
            List<Event> weeklyEvents = event_service.getWeeklyEvents(lastMonday, (authentication.getName())); //sends to the getWeeklyEvents function

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //format the last monday date to display on page
            String weekCommencing = dtf.format(lastMonday);  

            System.out.println(weeklyEvents.size());

            model.addAttribute("footer", page); //send current page number
            model.addAttribute("weekCommencing", "Week commencing: " + weekCommencing); //send week commencing information

            
            model.addAttribute("mondayEvents", weeklyEvents); //send events for each day to be displayed
            model.addAttribute("tuesdayEvents", weeklyEvents); //send events for each day to be displayed
            model.addAttribute("wednesdayEvents", weeklyEvents); //send events for each day to be displayed
            model.addAttribute("thursdayEvents", weeklyEvents); //send events for each day to be displayed
            model.addAttribute("fridayEvents", weeklyEvents); //send events for each day to be displayed
            model.addAttribute("satudayEvents", weeklyEvents); //send events for each day to be displayed
            model.addAttribute("sundayEvents", weeklyEvents); //send events for each day to be displayed
            
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

       

}
