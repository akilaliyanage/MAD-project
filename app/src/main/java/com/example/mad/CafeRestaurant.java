package com.example.mad;

public class CafeRestaurant {
    String cafeID;
    String name;
    String addressLine1;
    String addressLine2;
    String City;
    String category;
    Integer contact;
    String email;

    public CafeRestaurant() {
    }

    public CafeRestaurant(String cafeID, String name, String addressLine1, String addressLine2, String city, String category, Integer contact, String email) {
        this.cafeID = cafeID;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        City = city;
        this.category = category;
        this.contact = contact;
        this.email = email;
    }

    public String getCafeID() {
        return cafeID;
    }

    public String getName() {
        return name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public String getCity() {
        return City;
    }

    public String getCategory() {
        return category;
    }

    public Integer getContact() {
        return contact;
    }

    public String getEmail() {
        return email;
    }
}
