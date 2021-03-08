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

    public List<Learner> getAttendance(int eventID) {
        return repo.getAttendingUsers(eventID);
    }
      
}
