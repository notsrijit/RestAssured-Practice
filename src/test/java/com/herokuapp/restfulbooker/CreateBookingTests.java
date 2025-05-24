package com.herokuapp.restfulbooker;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class CreateBookingTests extends Base {

	@Test (enabled = false)
	public void createBookingTest () {

		//POST functionality being done in Base.java to make it reusable
		Response response = createBooking();
		response.print();
		
		//Verify Response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Expected 200, but not found");

		//Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String firstName = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(firstName, "Srijit", "First name not matching");
		
		String lastName = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(lastName, "Sutradhar", "Last name not matching");
		
		int totalPrice = response.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(totalPrice, 669, "Tptal price not matching");
		
		boolean deposit = response.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(deposit, "Deposit should be true but it is not");

		String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2025-04-01", "Checkin date not matching");

		String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2025-05-01", "Checkout date not matching");

		//To remove false positives
		softAssert.assertAll();

	}
	
	@Test(enabled = false)
	public void createBookingWithPOJOTest () {

		//Creating body using Class Object for Booking details (Much like POM)
		//Can also use Properties file or ApachePOI Excel Reader to enter data instead
		BookingDates bookingDates = new BookingDates("2025-04-01", "2025-05-01");
		Booking bookingBody = new Booking("Jit", "Sarkar", 505, true, bookingDates, "Dinner");
		
		//POST booking data into the API and receive response back
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(bookingBody)
				.post("/booking");
		response.print();
		
		//Verify Response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Expected 200, but not found");

		//Verify all fields
		SoftAssert softAssert = new SoftAssert();
		String firstName = response.jsonPath().getString("booking.firstname");
		softAssert.assertEquals(firstName, "Jit", "First name not matching");
		
		String lastName = response.jsonPath().getString("booking.lastname");
		softAssert.assertEquals(lastName, "Sarkar", "Last name not matching");
		
		int totalPrice = response.jsonPath().getInt("booking.totalprice");
		softAssert.assertEquals(totalPrice, 505, "Tptal price not matching");
		
		boolean deposit = response.jsonPath().getBoolean("booking.depositpaid");
		softAssert.assertTrue(deposit, "Deposit should be true but it is not");

		String actualCheckin = response.jsonPath().getString("booking.bookingdates.checkin");
		softAssert.assertEquals(actualCheckin, "2025-04-01", "Checkin date not matching");

		String actualCheckout = response.jsonPath().getString("booking.bookingdates.checkout");
		softAssert.assertEquals(actualCheckout, "2025-05-01", "Checkout date not matching");

		//To remove false positives
		softAssert.assertAll();

	}
	
	@Test
	public void createBookingWithPOJOandDeserialTest () {

		//Creating body using Class Object for Booking details (Much like POM)
		//Can also use Properties file or ApachePOI Excel Reader to enter data instead
		BookingDates bookingDates = new BookingDates("2025-04-01", "2025-05-01");
		Booking bookingBody = new Booking("Jit", "Sarkar", 505, true, bookingDates, "Dinner");
		
		//POST booking data into the API and receive response back
		Response response = RestAssured.given(spec).contentType(ContentType.JSON).body(bookingBody)
				.post("/booking");
		response.print();
		
		//Verify Response 200
		Assert.assertEquals(response.getStatusCode(), 200, "Expected 200, but not found");

		//Getting the Response body and mapping it to the Java object - DESERIALIZATION
		BookingID bookingID = response.as(BookingID.class);
		
		System.out.println("Request Body: " + bookingBody.toString());
		System.out.println("Response Body:" + bookingID.getBooking().toString());
		
		//Verify all fields
		Assert.assertEquals(bookingID.getBooking().toString(), bookingBody.toString());

	}

}
