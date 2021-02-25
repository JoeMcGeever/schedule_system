package com.example.demo;

import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import java.util.List;


@Controller
public class AppController {



    @Autowired
    private EventService event_service;
    @Autowired
    private TraineeService trainee_service;
    @Autowired
    private LearnerService learner_service;

    @RequestMapping("/")
    public String viewHomePage(Model model, Authentication authentication) {


        System.out.println(authentication.getName());

        //do check on getName to see if the id is a trainer or a learner. If learner -> redirect to a html page showing "Learner login --> not implemented though as not task"

        return "index";


        // System.out.println("----------------------------------------------------------");
        // int currentWeek = 1;
        // List<Event> currentWeekEvents = event_service.getEvents(currentWeek); //gets current weeks events
        
        // model.addAttribute("ft_users", currentWeekEvents); // index page - this add attribute must happen


        // System.out.println("Events for this week: " + currentWeekEvents.size());

        // return "index"; // home page
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

    @RequestMapping("/login-error-learner")  
    public String loginErrorLearner(Model model) {  
        model.addAttribute("loginLearnerError", true);  
        return "login.html";  
    }  

   


    

    // // to add a new full-time user
    // @RequestMapping("/new_ftuser")
    // public String ShowNewFullTimeUserForm(Model model) {
    //     User user = new FTUser();
    //     user.setDtype("Full-Time");

    //     model.addAttribute("user", user);
    //     return "new_ftuser"; // new_ftuser form
    // }

    // // to add a new part-time user
    // @RequestMapping("/new_ptuser")
    // public String ShowNewPartTimeUserForm(Model model) {
    //     User user = new PTUser();
    //     user.setDtype("Part-Time");

    //     model.addAttribute("user", user);
    //     return "new_ptuser"; // new_ptuser form
    // }    

    
    // //to save a new full-time user to the database      
    // @RequestMapping(value = "/save_ftuser", method = RequestMethod.POST) 
    // public String saveFullTimeUser(@ModelAttribute("ftuser") FTUser ftuser){     
    //     fservice.save(ftuser); //save to User table        
    //     System.out.println("write to the user  table (full-time).");

    //     Contract contract = ftuser.constructContractObject(); //composition
    //     contract_service.save(contract);  //save to Contract table
    //     System.out.println("write to the contract table.");

    //     Team team = new Team(); 
    //     team.setName(ftuser.getTeam());
    //     team_service.save(team); //save to Team table
    //     System.out.println("write to the team table.");

    //     TeamUser team_user = new TeamUser(); //aggregation
    //     team_user.setUser(ftuser.getUsername());     
    //     team_user.setTeam(ftuser.getTeam()); 
    //     team_user_service.save(team_user); //save to TeamUser table
    //     System.out.println("write to the teamuser table.");

    //     return "redirect:/"; //return to index page  
    // }

    // //to save a new part-time user to the database      
    // @RequestMapping(value = "/save_ptuser", method = RequestMethod.POST) 
    // public String savePartTimeUser(@ModelAttribute("ptuser") PTUser ptuser){  
    //     System.out.println(ptuser);   
    //     pservice.save(ptuser); //save to User table
    //     System.out.println("write to the user table (part-time).");

    //     Contract contract = ptuser.constructContractObject(); //composition
    //     contract_service.save(contract);  //save to Contract table
    //     System.out.println("write to the contract table.");

    //     Team team = new Team(); 
    //     team.setName(ptuser.getTeam());
    //     team.setName(team.getName());
    //     team_service.save(team); //save to Team table
    //     System.out.println("write to the team table.");

    //     TeamUser team_user = new TeamUser(); //aggregation
    //     team_user.setUser(ptuser.getUsername());     
    //     team_user.setTeam(ptuser.getTeam()); 
    //     team_user_service.save(team_user); //save to TeamUser table
    //     System.out.println("write to the teamuser table.");

    //     return "redirect:/"; //return to index page  
    // }

    // //to update a full-time user in the database
    // @RequestMapping("/edit_ftuser/{mode}")
    // public ModelAndView showEditFullTimeUserPage(@PathVariable(name = "mode") String mode) {
    //     ModelAndView mav = new ModelAndView("edit_ftuser"); // edit_ftuser form
    //     User user  = fservice.get(mode);  
    //     mav.addObject("ftuser", user);
        
    //     return mav;
    // }

    // //to update a part-time user in the database
    // @RequestMapping("/edit_ptuser/{mode}")
    // public ModelAndView showEditPartTimeUserPage(@PathVariable(name = "mode") String mode) {
    //     ModelAndView mav = new ModelAndView("edit_ptuser"); // edit_ptuser form
    //     User user  = pservice.get(mode);  
    //     mav.addObject("ptuser", user);

    //     return mav;
    // } 
    
    // //to delete a full-time user from the database
    // @RequestMapping("/delete_ftuser/{username}")
    // public String deleteFullTimeUser(@PathVariable(name = "username") String username) {
    //     fservice.delete(username); //delete user from user table

    //     TeamUser team_user = team_user_service.get(username);
    //     team_service.delete(team_user.getTeam()); //delete user from team table
    
    //     team_user_service.delete(username); //delete user from teamuser table        
    //     contract_service.delete(username); //delete user from contract table        
    //     System.out.println("delete a full-time user. ");

    //     return "redirect:/"; //return to index page      
    // }   

    // //to delete a part-time user from the database
    // @RequestMapping("/delete_ptuser/{username}")
    // public String deletePartTimeUser(@PathVariable(name = "username") String username) {
    //     pservice.delete(username); //delete user from user table

    //     TeamUser team_user = team_user_service.get(username);
    //     team_service.delete(team_user.getTeam()); //delete user from team table
        
    //     team_user_service.delete(username); //delete user from teamuser table
    //     contract_service.delete(username); //delete user from contract table
    //     System.out.println("delete a part-time user. ");

    //     return "redirect:/"; //return to index page      
    // }   
}
