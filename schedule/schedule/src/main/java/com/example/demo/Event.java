package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {
    private String contract;
    private String username;

    @Id
    @Column(name="username") //username - primary key in the Contract table
    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
		this.username = name;
    } 

    @Column(name="contract")
    public String getContract() {
        return contract;
    }

    public void setContract(String content) {
		this.contract = content;
    } 
}
