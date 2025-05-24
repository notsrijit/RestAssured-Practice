package com.herokuapp.restfulbooker;

public class BookingID {
	
	//De-Serializing the Response we have to compare with Request body. I.e, Breaking down the Response and comparing
	
	private int bookingid;
	private Booking booking;
	
	public BookingID(int bookingid, Booking booking) {
		this.bookingid = bookingid;
		this.booking = booking;
	}
	
	public BookingID() {
	}

	@Override
	public String toString() {
		return "BookingID [bookingid=" + bookingid + ", booking=" + booking + "]";
	}

	public int getBookingid() {
		return bookingid;
	}

	public void setBookingid(int bookingid) {
		this.bookingid = bookingid;
	}

	public Booking getBooking() {
		return booking;
	}

	public void setBooking(Booking booking) {
		this.booking = booking;
	}
	
	
	

}
