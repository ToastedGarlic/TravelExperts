package com.example.travelexperts.Model;

public class Bookings {
    private int bookingId;
    private String bookingDate;
    private String bookingNo;
    private double travelerCount;
    private int customerId;


    public Bookings(int bookingId, String bookingDate, String bookingNo, double travelerCount, int customerId) {
        this.bookingId = bookingId;
        this.bookingDate = bookingDate;
        this.bookingNo = bookingNo;
        this.travelerCount = travelerCount;
        this.customerId = customerId;

    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public double getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(double travelerCount) {
        this.travelerCount = travelerCount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Booking Number: " + bookingNo;
    }
}
