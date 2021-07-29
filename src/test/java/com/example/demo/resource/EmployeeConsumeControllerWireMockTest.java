package com.example.demo.resource;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

/* 2 ways to use Wiremock server
 * 1. Wiremock server instance
 * 2. @Rule annotation and use WireMockRule
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // for restTemplate
public class EmployeeConsumeControllerWireMockTest {

	private WireMockServer wm; 
	
	@LocalServerPort
	  private Integer port;
	
	@Autowired
	private TestRestTemplate restTemplate;

	private static final ObjectMapper om = new ObjectMapper();
	
	@BeforeEach
	void setup() {
		wm = new WireMockServer(8085);
		wm.stubFor(get(urlEqualTo("/hello")).willReturn(
				aResponse()
				.withStatus(200)
	            .withHeader("Content-Type", "text/plain")
	            .withBody("Welcome to Hello World!")));
		
		wm.stubFor(get(urlPathMatching("/products"))
				.willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json")
						.withBody("{ \"id\": \"1\",\"name\": \"Honey\"},{\"id\": \"2\",\"name\": \"Almond\" }")));
		
		wm.start();
		
	}
	
	@AfterEach
	void cleanup() {
		wm.stop();
	}


	@Test
	void testHello() throws ClientProtocolException, IOException  {
		
		String expected = "Welcome to Hello World!";
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet("http://localhost:8085/hello");
		HttpResponse httpResponse = httpClient.execute(request);
		String responseString = convertResponseToString(httpResponse);
		System.out.println("$$$"+responseString);
		assertEquals(expected, responseString);
        
	}
	
	@Test
	void testGetProducts() throws ClientProtocolException, IOException, JSONException  {
		
		
		String expected = "{ \"id\": \"1\",\"name\": \"Honey\"},{\"id\": \"2\",\"name\": \"Almond\" } ";

		
		ResponseEntity<String> response = restTemplate.getForEntity("/consume/products", String.class);

        printJSON(response);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        //assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        JSONAssert.assertEquals(expected, response.getBody(), false);
        
	}
	 private static void printJSON(Object object) {
	        String result;
	        try {
	            result = om.writerWithDefaultPrettyPrinter().writeValueAsString(object);
	            System.out.println(result);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 private String convertResponseToString(HttpResponse response) throws IOException {
		    InputStream responseStream = response.getEntity().getContent();
		    Scanner scanner = new Scanner(responseStream, "UTF-8");
		    String responseString = scanner.useDelimiter("\\Z").next();
		    scanner.close();
		    return responseString;
		}
}
