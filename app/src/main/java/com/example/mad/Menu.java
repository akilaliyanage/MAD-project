package com.example.mad;

public class Menu {
    private String menuID;
    private String menuName;
    private Double menuPrice;
    private Double menuDiscount;

    public Menu() {
    }

    public Menu(String menuID, String menuName, Double menuPrice, Double menuDiscount) {
        this.menuID = menuID;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
        this.menuDiscount = menuDiscount;
    }

    public String getMenuID() {
        return menuID;
    }

    public String getMenuName() {
        return menuName;
    }

    public Double getMenuPrice() {
        return menuPrice;
    }

    public Double getMenuDiscount() {
        return menuDiscount;
    }
}
