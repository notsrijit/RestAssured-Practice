package com.herokuapp.restfulbooker;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class HealthCheckTest extends Base {
	
	@Test
	public void healthCheckTest() {
		
		given(spec).
		when().
			get("/ping").
		then().
			assertThat().
			statusCode(201);		
		
	}
	
	@Test
	public void headersAndCookiesTest() {
		
		Response response = RestAssured.given(spec).get("/ping");
		
		//Get Headers
		Headers headers = response.getHeaders();
		System.out.println("Headers are: "+ headers);
				
		//Get individual Header
		Header headerOne = headers.get("Server");
		System.out.println(headerOne.getName() + ": " + headerOne.getValue());
		//Or
		String headerTwo = response.getHeader("Server");
		System.out.println("Server: " + headerTwo);
				
		//Get Cookies
		Cookies cookies = response.getDetailedCookies();
		System.out.println("Cookies are: "+ cookies);
		
	}
	
	@Test
	public void createHeadersAndCookiesTest() {
		
		//Creating Headers and Cookies
		Header newHeader = new Header("header_title", "header_value");
		spec.header(newHeader);
		
		Cookie newCookie = new Cookie.Builder("cookie_title", "cookie_value").build();
		spec.cookie(newCookie);
		
		//Or, Creating by adding directly in Response call
		Response responseCreate = RestAssured.given(spec).
				cookie("Test Cookie Name", "Test Cookie Value").
				header("Test Header Name", "Test Header Value").
				log().all().get("/ping");
		
		//Get Headers
		Headers headers = responseCreate.getHeaders();
		System.out.println("Headers are: "+ headers);

		//Get Cookies
		Cookies cookies = responseCreate.getDetailedCookies();
		System.out.println("Cookies are: "+ cookies);	
		
	}

}
