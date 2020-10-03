package com.example.mad;

//cafe restaurant class - database table of cafe
public class CafeRestaurant {
    String cafeID;
    String name;
    String addressLine1;
    String addressLine2;
    String city;
    String category;
    Integer contact;
    String email;

    //constructor
    public CafeRestaurant() {
    }

    //parameterized constructor
    public CafeRestaurant(String cafeID, String name, String addressLine1, String addressLine2, String city, String category, Integer contact, String email) {
        this.cafeID = cafeID;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
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

        return city;
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
