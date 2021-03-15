package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void shouldReturnLogin() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Login");
	}

	@Test
	public void shouldLogin() throws Exception {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/",
				String.class)).contains("Login");
	}

	// @Test
    // public void whenRequested_thenForwardToCorrectUrl() throws ServletException, IOException {
    //     MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
    //     request.addParameter("name", "Dennis");
    //     MockHttpServletResponse response = new MockHttpServletResponse();
    //     HelloServlet servlet = new HelloServlet();

    //     servlet.doGet(request, response);

    //     assertEquals("/forwarded", response.getForwardedUrl());
    //     assertEquals(200, response.getStatus());
    // }
}
