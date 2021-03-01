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

        
        if(page == null){
            page = 1;
        }

    
        System.out.println(page);
        
        

        if(user.getDtype().equals("trainee")){

            //here is the plan:
            //get from the layer: all events for currently selected week (footer)
            int footer = 5;
            model.addAttribute("footer", footer);
            
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
    public String saveFullTimeUser(@ModelAttribute("event") Event event, Authentication authentication){    
        
        event.setTrainee(authentication.getName()); //set the trainee name 
        
        event_service.save(event); //save to Event table
        //if success:        
        return "redirect:/"; //return to index page  
        //otherwise, return an error -- load add.html but add a true statement to an error value in model.
    }

       

}
