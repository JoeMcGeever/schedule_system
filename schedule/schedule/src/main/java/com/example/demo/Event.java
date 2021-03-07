package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "event")
public class Event {

    @Id
    @Column(name="eventID") //eventID - primary key in the Event table
    @GeneratedValue(strategy=GenerationType.IDENTITY) //auto increment
    private int eventID;

    @Column(name="classsize")
    private int classSize;

    @Column(name="topic")
    private String topic;

    @Column(name="location")
    private String location;

    @Column(name="date")
    private String date; //string??

    @Column(name="time")
    private String time; //string??

    @Column(name="endtime")
    private String endTime; //string??

    @Column(name="duration")
    private int duration;
    
    @Column(name="traineename")
    private String traineeName;



    public int eventID() {
        return eventID;
    }

    public int getClassSize() {
      return classSize;
    }

     public void setClassSize(int classSize) {
       this.classSize = classSize;
    } 

    public String getTopic() {
      return topic;
    }

     public void setTopic(String topic) {
      this.topic = topic;
    } 

    public String getLocation() {
      return location;
    }

    public void setLocation(String location) {
      this.location = location;
    } 

    public String getDate() {
      return date;
    }

    public void setDate(String date) {
      this.date = date;
    } 

    public String getTime() {
      return time;
    }

    public void setTime(String time) {
      this.time = time;
    } 

    public String getEndTime() {
      return endTime;
    }

    public void setEndTime(String endTime) {
      this.endTime = endTime;
    } 

    public int getDuration() {
      return duration;
    }

    public void setDuration(int duration) {
      this.duration = duration;
    } 

    public String getTrainee() {
      return traineeName;
    }

    public void setTrainee(String trainee) {
      this.traineeName = trainee;
    } 

}
