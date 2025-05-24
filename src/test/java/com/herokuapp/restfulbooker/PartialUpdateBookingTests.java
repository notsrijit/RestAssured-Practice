package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartialUpdateBookingTests extends Base {

	@Test
	public void partialUpdateBookingTest() {

		// Create booking with POST from Base.java
		Response response = createBooking();
		response.print();

		// Get booking id of new booking
		int bookingID = response.jsonPath().getInt("bookingid");

		// Creating new JSON Body
		JSONObject partialUpdatedBody = new JSONObject();

		// Putting data inside
		partialUpdatedBody.put("firstname", "Prangan");

		JSONObject updatedBookingDates = new JSONObject();
		updatedBookingDates.put("checkin", "2025-06-01");
		updatedBookingDates.put("checkout", "2025-07-01");

		partialUpdatedBody.put("bookingdates", updatedBookingDates);

		// PATCH booking data into the API to update and receive response back
		// Update also requires Authorization - Else error 'Forbidden' is received
		Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.contentType(ContentType.JSON).body(partialUpdatedBody.toString())
				.patch("/booking/" + bookingID);
		responseUpdate.print();

		/*
		 * auth().preemptive().basic("admin", "password123")
		 * "username" : "admin", "password" : "password123"
		 */

		// Verify Response 200
		Assert.assertEquals(responseUpdate.getStatusCode(), 200, "Expected 200, but not found");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String firstName = responseUpdate.jsonPath().getString("firstname");
		softAssert.assertEquals(firstName, "Prangan", "First name not matching");

		String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2025-06-01", "Checkin date not matching");

		// To remove false positives
		softAssert.assertAll();

	}

}
