package com.example.travelexperts.Model;

public class BookingDetails {
    private int bookingDetailId;
    private double itineraryNo;
    private String tripStart;
    private String tripEnd;
    private String description;
    private String Destination;
    private double basePrice;
    private double agencyCommission;
    private int bookingId;
    private String regionId;
    private String classId;
    private String feeId;
    private int ProductSupplierId;

    public BookingDetails(int bookingDetailId, double itineraryNo, String tripStart, String tripEnd, String description, String destination, double basePrice, double agencyCommission, int bookingId, String regionId, String classId, String feeId, int productSupplierId) {
        this.bookingDetailId = bookingDetailId;
        this.itineraryNo = itineraryNo;
        this.tripStart = tripStart;
        this.tripEnd = tripEnd;
        this.description = description;
        Destination = destination;
        this.basePrice = basePrice;
        this.agencyCommission = agencyCommission;
        this.bookingId = bookingId;
        this.regionId = regionId;
        this.classId = classId;
        this.feeId = feeId;
        ProductSupplierId = productSupplierId;
    }

    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public double getItineraryNo() {
        return itineraryNo;
    }

    public void setItineraryNo(double itineraryNo) {
        this.itineraryNo = itineraryNo;
    }

    public String getTripStart() {
        return tripStart;
    }

    public void setTripStart(String tripStart) {
        this.tripStart = tripStart;
    }

    public String getTripEnd() {
        return tripEnd;
    }

    public void setTripEnd(String tripEnd) {
        this.tripEnd = tripEnd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public double getAgencyCommission() {
        return agencyCommission;
    }

    public void setAgencyCommission(double agencyCommission) {
        this.agencyCommission = agencyCommission;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public int getProductSupplierId() {
        return ProductSupplierId;
    }

    public void setProductSupplierId(int productSupplierId) {
        ProductSupplierId = productSupplierId;
    }
}
