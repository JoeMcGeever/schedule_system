package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class SmokeTest {

	@Autowired
    public AppController controller;
    
    @Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}


	@Autowired
	public EventService event_service;

	@MockBean
	public EventRepository event_repo;



    @Test
    public void eventAddedCorrectly() {
		Event eventInstance = new Event();
		eventInstance.setTopic("test");
		eventInstance.setClassSize(30);
		eventInstance.setLocation("test");
		eventInstance.setDate("2021-05-15");;
		eventInstance.setDuration(30);
		eventInstance.setTime("17:00");
		eventInstance.setTrainee("Joe");
		event_service.setEndTime(eventInstance);
		System.out.println(eventInstance.eventID());
        assertThat(event_service.getEvent(eventInstance.eventID()).getTopic()).isEqualTo("test");
    }

	@Test
    public void setEndTimeWorks() {
		Event eventInstance = new Event();
		eventInstance.setDuration(30);
		eventInstance.setTime("17:00");
		event_service.setEndTime(eventInstance);
        assertThat(eventInstance.getEndTime()).isEqualTo("17:30");
    }

	


}