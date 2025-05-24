package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.annotations.BeforeMethod;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Base {

	/*
	 * SOAP vs REST
	 * 
	 * SOAP:
	 * 
	 * ▷Simple Object Access Protocol
	 * ▷Higher complexity, slower
	 * ▷Only allows XML
	 * ▷Enterprise-level web services
	 * ▷HTTP POST
	 * 
	 * REST:
	 * 
	 * ▷Representational State Transfer
	 * ▷More flexible, better performance
	 * ▷HTML, JSON, XML, and plain text
	 * ▷Popular in public APIs
	 * ▷HTTP GET, POST, PUT, DELETE and PATCH methods
	 * 
	 */

	protected RequestSpecification spec;

	@BeforeMethod
	public void setUp() {

		// Using Request Specification so that URL fetching is no longer redundant
		spec = new RequestSpecBuilder().setBaseUri("https://restful-booker.herokuapp.com").build();

	}

	public Response createBooking() {
		// Creating JSON Body
		JSONObject body = new JSONObject();

		// Putting data inside
		body.put("firstname", "Srijit");
		body.put("lastname", "Sutradhar");
		body.put("totalprice", 669);
		body.put("depositpaid", true);

		JSONObject bookingDates = new JSONObject();
		bookingDates.put("checkin", "2025-04-01");
		bookingDates.put("checkout", "2025-05-01");

		body.put("bookingdates", bookingDates);
		body.put("additionalneeds", "Breakfast");

		// POST booking data into the API and receive response back
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(body.toString())
				.post("/booking");
		return response;
	}

}
