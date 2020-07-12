package com.example.mealmanagement.model;

public class DailyCost {

    long id;
    String date;
    int cost;


    public DailyCost() {
        this.id = 0;
        this.date = "";
        this.cost = 0;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
