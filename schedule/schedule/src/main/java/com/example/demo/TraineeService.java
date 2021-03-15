package com.example.demo;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.security.core.userdetails.*;

@Service
@Transactional
public class TraineeService implements UserDetailsService{

    
    @Autowired
    private TraineeRepository repo;

    public List<Trainee> getAllTrainees(){
        return repo.findAllBydtype("trainee");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with that email");
        }
         
        return new SignedInUser(user);
    }

    public void save(Trainee user) {
        repo.save(user);
    }


    public User getUser(String username) {
        return repo.findByUsername(username); //get a user by username
    }
    
    public void deleteRegistrationInstance(int eventID, String user ) {

        System.out.println(repo.deleteRegistration(eventID, user));
    }
             

        
}

