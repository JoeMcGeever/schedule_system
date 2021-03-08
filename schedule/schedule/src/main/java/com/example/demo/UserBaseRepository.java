package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends JpaRepository<T, String> {

    public T findByDtype(String mode); //dtype - attribute in the User table for trainee or learner
    public T findByUsername(String mode); //used for loggin in
    public void deleteByUsername(String username); //username - primary key in the User table

    @Query(value = "SELECT * FROM User WHERE name IN (SELECT attendee FROM Attendance WHERE eventID=:eventID)", nativeQuery = true)
    public List<T> getAttendingUsers(@Param("eventID") int eventID);

}