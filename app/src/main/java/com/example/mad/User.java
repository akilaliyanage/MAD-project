package com.example.add_hotel;

public class User {
    private  String hotelName;
    private String location;
    private String serviceCharge;
    private String email;
    private String phoneNumber;
    private String userId;
    private String imageUrl;

    public User() {
    }

    public User(String hotelName, String location, String serviceCharge, String email, String phoneNumber, String userId, String imageUrl) {
        this.hotelName = hotelName;
        this.location = location;
        this.serviceCharge = serviceCharge;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.userId = userId;
        this.imageUrl = imageUrl;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getLocation() {
        return location;
    }

    public String getServiceCharge() {
        return serviceCharge;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUserId() { return userId; }

    public String getImageUrl() { return imageUrl; }
}
