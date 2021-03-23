package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


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
    public void getEventServiceTest() {
		Event eventInstance = getTestEvent();

		event_service.setEndTime(eventInstance);
		System.out.println(eventInstance.eventID());

		given(event_repo.findByEventID(anyInt())).willReturn(eventInstance);

        assertThat(event_service.getEvent(eventInstance.eventID()).getTopic()).isEqualTo("test");
    }


	@Test
    public void setEndTime30Works() {
		Event eventInstance = new Event();
		eventInstance.setDuration(30);
		eventInstance.setTime("17:00");
		event_service.setEndTime(eventInstance);
        assertThat(eventInstance.getEndTime()).isEqualTo("17:30");
    }

	@Test
    public void setEndTime60Works() {
		Event eventInstance = new Event();
		eventInstance.setDuration(60);
		eventInstance.setTime("17:15"); //a time that would not be accepted in from the View
		event_service.setEndTime(eventInstance);
        assertThat(eventInstance.getEndTime()).isEqualTo("18:15");
    }

	@Test
    public void setEndTime95Works() {
		Event eventInstance = new Event();
		eventInstance.setDuration(95); //a duration that would not be accepted in the View
		eventInstance.setTime("17:00");
		event_service.setEndTime(eventInstance);
        assertThat(eventInstance.getEndTime()).isEqualTo("18:35");
    }



	@Test
    public void add() {
		Event eventInstance = getTestEvent();

		event_service.setEndTime(eventInstance);
		given(event_repo.findByEventID(anyInt())).willReturn(eventInstance);
		event_repo.save(eventInstance);

        assertThat(event_service.getEvent(eventInstance.eventID()).getTopic()).isEqualTo("test");
    }







	@Test
    public void testGetTomorrowEvents() {
		Event eventInstanceA = getTestEvent();
		Event eventInstanceB = getTestEvent();
		LocalDate now = LocalDate.now();
		now = now.plusDays(1);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String tomorrow = dtf.format(now); 
		eventInstanceB.setDate(tomorrow);
		event_service.save(eventInstanceA);
		event_service.save(eventInstanceB);
        assertThat(event_service.getTomorrowsEvents("Joe").size()).isEqualTo(0);
    }
	

	public Event getTestEvent(){
		Event eventInstance = new Event();
		eventInstance.setTopic("test");
		eventInstance.setClassSize(30);
		eventInstance.setLocation("test");
		eventInstance.setDate("2021-05-15");;
		eventInstance.setDuration(30);
		eventInstance.setTime("17:00");
		eventInstance.setTrainee("Joe");
		return eventInstance; 
	}


}