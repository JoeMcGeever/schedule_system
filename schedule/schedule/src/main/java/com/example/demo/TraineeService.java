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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not find user with that email");
        }
         
        return new SignedInUser(user);
    }

    public List<Trainee> listAll() {
        return repo.findAll();
    }

    public void save(Trainee user) {
        repo.save(user);
    }

    public User get(String mode) {
        return repo.findByDtype(mode); //findByDtype: the default method to find a sub-type
    }

    public User getUser(String username) {
        return repo.findByUsername(username); //get a user by username
    }
        
    public void delete(String username) {
        repo.deleteByUsername(username); //username - primary key in the User table
    }          
}

