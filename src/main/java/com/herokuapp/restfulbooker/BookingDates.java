package com.herokuapp.restfulbooker;

public class BookingDates {
	
	//Doing this as an alternative method to JSONObject - SERIALIZATION
	//Concept known as POJO - Plain Old Java Object
	
	private String checkin;
	private String checkout;
	
	//Need to create instance of this class by creating constructor
	public BookingDates(String checkin, String checkout) {

		this.checkin = checkin;
		this.checkout = checkout;
	}
	
	public BookingDates() {
	}

	@Override
	public String toString() {
		return "BookingDates [checkin=" + checkin + ", checkout=" + checkout + "]";
	}

	public String getCheckin() {
		return checkin;
	}

	public void setCheckin(String checkin) {
		this.checkin = checkin;
	}

	public String getCheckout() {
		return checkout;
	}

	public void setCheckout(String checkout) {
		this.checkout = checkout;
	}
	
	

}
