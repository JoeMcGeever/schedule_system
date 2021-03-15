package com.example.demo;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) throws MessagingException{
        
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("training@fabrikum.com");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
        
    }



    public void email(Event eventInstance, List<Learner> attendingUsers, String userEmail) throws MessagingException{
      String subject = "Your class: " + eventInstance.getTopic() + " is tomorrow.";

      String text;

      if(attendingUsers.size()==0){
        text = "At the moment, there are no learners who are signed up for this event. \n";
      } else {
        text = "Here is the attending user list: \n\n";
        for(int i=0; i < attendingUsers.size(); i++){
          text = text + attendingUsers.get(i).getUsername() + " : " + attendingUsers.get(i).getEmail() + "\n";
        }
      }
      text = text + "To delete users registrations, please log in here by entering this URL:\nhttp://localhost:8080/details?id=" + eventInstance.eventID();



      sendSimpleMessage(userEmail, subject, text);
    }

    public void email(Event eventInstance, List<Learner> attendingUsers) throws MessagingException{

      String subject = "The details for  the '" + eventInstance.getTopic() + "' class have changed";
      String text = "Here are the new details for the event:\n";
      text = text + "Topic : " + eventInstance.getTopic() + "\n";
      text = text + "Date : " + eventInstance.getDate() + "\n";
      text = text + "Time : " + eventInstance.getTime() + "-" + eventInstance.getEndTime() + "\n";
      text = text + "Location : " + eventInstance.getLocation() + "\n\n";
      text = text + "Tutor : " + eventInstance.getTrainee() + "\n";

      for(int i=0; i<attendingUsers.size(); i++){
        sendSimpleMessage(attendingUsers.get(i).getEmail(), subject, text);
      }
    }

}
