package com.example.demo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface UserBaseRepository<T extends User> extends JpaRepository<T, String> {

    public T findByDtype(String mode); //dtype - attribute in the User table for FTUser or PTUser
    public T findByUsername(String mode); //used for loggin in
    public void deleteByUsername(String username); //username - primary key in the User table

}