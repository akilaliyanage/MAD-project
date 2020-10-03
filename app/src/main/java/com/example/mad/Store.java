package com.example.mad;

public class Store {

    private String store_id;
    private String name;
    private String category;
    private String description;
    private String location;
    private String branch;

    public Store() {
    }

    public Store(String store_id, String name, String category, String description, String location, String branch ) {
        this.name = name;
        this.store_id = store_id;
        this.description = description;
        this.location = location;
        this.category = category;
        this.branch = branch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCategory() {
        return category;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
