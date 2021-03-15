package com.example.demo;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Transactional
public interface TraineeRepository extends UserBaseRepository<Trainee> { 

    // @Query(value = "SELECT * FROM User WHERE name IN (SELECT attendee FROM Attendance WHERE eventID=:eventID)", nativeQuery = true)
    // public List<User> getAttendingUsers(@Param("eventID") int eventID);

    @Modifying
    @Query(value = "DELETE FROM Attendance WHERE eventID=:eventID AND attendee=:attendee", nativeQuery = true)
    public int deleteRegistration(@Param("eventID") int eventID, @Param("attendee") String attendee);

}

