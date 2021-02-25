package com.example.demo;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class LearnerService {

    @Autowired
    private LearnerRepository repo;

    public List<Learner> listAll() {
        return repo.findAll();
    }

    public void save(Learner user) {
        repo.save(user);
    }

    public User get(String mode) {
        return repo.findByDtype(mode); //findByDtype: the default method to find a sub-type
    }
        
    public void delete(String username) {
        repo.deleteByUsername(username); //username - primary key in the User table
    }          
}
