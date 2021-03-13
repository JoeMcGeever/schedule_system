package com.example.demo;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends JpaRepository<T, String> {

    public T findByUsername(String username); //used for loggin in
    public void deleteByUsername(String username); //username - primary key in the User table

    @Query(value = "SELECT * FROM User WHERE name IN (SELECT attendee FROM Attendance WHERE eventID=:eventID)", nativeQuery = true)
    public List<T> getAttendingUsers(@Param("eventID") int eventID);

    @Modifying
    @Query(value = "DELETE FROM Attendance WHERE eventID=:eventID AND attendee=:attendee", nativeQuery = true)
    public int deleteRegistration(@Param("eventID") int eventID, @Param("attendee") String attendee);

}