package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.response.Response;

public class GetBookingTest extends Base {

	@Test (enabled = false)
	public void getBookingTest() {

		// Create booking with POST from Base.java
		Response responseCreate = createBooking();
		responseCreate.print();

		// Get booking id of new booking
		int booking = responseCreate.jsonPath().getInt("bookingid");

		/*
		 * Path Parameter: GET/booking/{bookingID} -> Path parameters are used to identify specific resource
		 *
		 * Query Parameter: GET/booking?firstname=Sally -> Query parameters are used to sort or filter specific resources
		 */

		// Set Path Parameter - As an example, not needed
		spec.pathParam("bookingID", booking);

		// Using GET Response to lookup the same booking
		Response response = RestAssured.given(spec).get("/booking/{bookingID}");
		response.print();

		// Verify Response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Expected 200, but not found");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String firstName = response.jsonPath().getString("firstname");
		softAssert.assertEquals(firstName, "Srijit", "First name not matching");

		String lastName = response.jsonPath().getString("lastname");
		softAssert.assertEquals(lastName, "Sutradhar", "Last name not matching");

		int totalPrice = response.jsonPath().getInt("totalprice");
		softAssert.assertEquals(totalPrice, 669, "Tptal price not matching");

		boolean deposit = response.jsonPath().getBoolean("depositpaid");
		softAssert.assertTrue(deposit, "Deposit should be true but it is not");

		String actualCheckin = response.jsonPath().getString("bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2025-04-01", "Checkin date not matching");

		String actualCheckout = response.jsonPath().getString("bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2025-05-01", "Checkout date not matching");

		// To remove false positives
		softAssert.assertAll();

	}
	
	@Test
	public void getBookingXMLTest() {

		// Create booking with POST from Base.java
		Response responseCreate = createBooking();
		responseCreate.print();

		// Get booking id of new booking
		int booking = responseCreate.jsonPath().getInt("bookingid");

		/*
		 * Path Parameter: GET/booking/{bookingID} -> Path parameters are used to identify specific resource
		 *
		 * Query Parameter: GET/booking?firstname=Sally -> Query parameters are used to sort or filter specific resources
		 */

		// Set Path Parameter - As an example, not needed
		spec.pathParam("bookingID", booking);

		// Using GET Response to lookup the same booking
		Header xml = new Header("Accept", "application/xml");
		spec.header(xml);
		Response response = RestAssured.given(spec).get("/booking/{bookingID}");
		response.print();

		// Verify Response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Expected 200, but not found");

		// Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String firstName = response.xmlPath().getString("booking.firstname");
		softAssert.assertEquals(firstName, "Srijit", "First name not matching");

		String lastName = response.xmlPath().getString("booking.lastname");
		softAssert.assertEquals(lastName, "Sutradhar", "Last name not matching");

		int totalPrice = response.xmlPath().getInt("booking.totalprice");
		softAssert.assertEquals(totalPrice, 669, "Total price not matching");

		boolean deposit = response.xmlPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(deposit, "Deposit should be true but it is not");

		String actualCheckin = response.xmlPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2025-04-01", "Checkin date not matching");

		String actualCheckout = response.xmlPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2025-05-01", "Checkout date not matching");

		// To remove false positives
		softAssert.assertAll();

	}

}
