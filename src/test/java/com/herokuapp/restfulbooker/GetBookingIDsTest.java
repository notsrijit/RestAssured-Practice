package com.herokuapp.restfulbooker;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetBookingIDsTest extends Base {
	
	@Test
	public void getBookingIdsWithoutFilter () {
		
		//GET Response about booking ids
		Response response = RestAssured.given(spec).get("/booking");
		response.print();
		
		//Verify Response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Expected 200, but not found");
		
		//Verify booking IDs in response
		List<Integer> bookingIDs = response.jsonPath().getList("bookingId");
		Assert.assertFalse(bookingIDs.isEmpty(), "List empty, but should not be");
		
	}
	
	/*
	 * Path Parameter: GET/booking/{bookingID} -> Path parameters are used to identify specific resource
	 *
	 * Query Parameter: GET/booking?firstname=Sally -> Query parameters are used to sort or filter specific resources
	 */
	
	@Test
	public void getBookingIdsWithFilter () {
		
		// Set Query Parameter
		spec.queryParam("firstname", "Srijit");
		spec.queryParam("lastname", "Sutradhar");
		
		//GET Response about booking ids
//		Response response = RestAssured.given(spec).get("/booking?firstname=Srijit&lastname=Sutradhar");
//		Or
		Response response = RestAssured.given(spec).get("/booking");
		
		response.print();
		
		//Verify Response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Expected 200, but not found");
		
		//Verify booking IDs in response
		List<Integer> bookingIDs = response.jsonPath().getList("bookingId");
		Assert.assertFalse(bookingIDs.isEmpty(), "List empty, but should not be");
		
	}

}

