package com.example.mad;

public class Item {
    String itemID;
    String itemName;
    float itemPrize;
    String itemStyle;
    String itemOffers;
    String itemDescription;

    public Item(){}

    public Item(String itemID, String itemName, float itemPrize, String itemStyle, String itemOffers, String itemDescription) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrize = itemPrize;
        this.itemStyle = itemStyle;
        this.itemOffers = itemOffers;
        this.itemDescription = itemDescription;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public float getItemPrize() {
        return itemPrize;
    }

    public void setItemPrize(float itemPrize) {
        this.itemPrize = itemPrize;
    }

    public String getItemStyle() {
        return itemStyle;
    }

    public void setItemStyle(String itemStyle) {
        this.itemStyle = itemStyle;
    }

    public String getItemOffers() {
        return itemOffers;
    }

    public void setItemOffers(String itemOffers) {
        this.itemOffers = itemOffers;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
}
