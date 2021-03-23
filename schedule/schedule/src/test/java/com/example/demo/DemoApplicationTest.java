package com.example.demo;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoApplicationTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private EventRepository eventRepository;

	

	@Test
	public void shouldReturnDefaultMessage() throws Exception {
		this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Login")));
	}

	@Test
	public void testSayHi() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/").param("name", "Joe"))
					.andExpect(MockMvcResultMatchers.status().isOk())
					.andExpect(MockMvcResultMatchers.model().attribute("msg", "Hi there, Joe."))
					.andExpect(MockMvcResultMatchers.view().name("hello-page"))
					.andDo(MockMvcResultHandlers.print());
	}

	// @Test
	// public void testEventSave() throws Exception {
	// 	when(event_service.save(null)).thenReturn("Hello, Mock");
	// 	this.mockMvc.perform(get("/add")).andDo(print()).andExpect(status().isOk())
	// 			.andExpect(content().string(containsString("Hello, Mock")));
	// }
	
}