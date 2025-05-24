package com.herokuapp.restfulbooker;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class UpdateBookingTests extends Base {

	@Test
	public void updateBookingTest() {

		// Create booking with POST from Base.java
		Response response = createBooking();
		response.print();

		// Get booking id of new booking
		int bookingID = response.jsonPath().getInt("bookingid");

		// Creating new JSON Body
		JSONObject updatedBody = new JSONObject();

		// Putting data inside
		updatedBody.put("firstname", "Prangan");
		updatedBody.put("lastname", "Mukherjee");
		updatedBody.put("totalprice", 699);
		updatedBody.put("depositpaid", true);

		JSONObject updatedBookingDates = new JSONObject();
		updatedBookingDates.put("checkin", "2025-06-01");
		updatedBookingDates.put("checkout", "2025-07-01");

		updatedBody.put("bookingdates", updatedBookingDates);
		updatedBody.put("additionalneeds", "Lunch");

		// PUT booking data into the API to update and receive response back
		// Update also requires Authorization - Else error 'Forbidden' is received
		Response responseUpdate = RestAssured.given(spec).auth().preemptive().basic("admin", "password123")
				.contentType(ContentType.JSON).body(updatedBody.toString())
				.put("/booking/" + bookingID);
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

		String lastName = responseUpdate.jsonPath().getString("lastname");
		softAssert.assertEquals(lastName, "Mukherjee", "Last name not matching");

		int totalPrice = responseUpdate.jsonPath().getInt("totalprice");
		softAssert.assertEquals(totalPrice, 699, "Tptal price not matching");

		boolean deposit = responseUpdate.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(deposit, "Deposit should be true but it is not");

		String actualCheckin = responseUpdate.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2025-06-01", "Checkin date not matching");

		String actualCheckout = responseUpdate.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2025-07-01", "Checkout date not matching");

		// To remove false positives
		softAssert.assertAll();

	}

}
