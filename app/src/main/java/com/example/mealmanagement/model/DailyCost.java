package com.example.mealmanagement.model;

public class DailyCost {

    long id;
    int cost;
    String date;
    int user_id;
    String yr_month;


    public DailyCost() {
        this.id = 0;
        this.date = "";
        this.cost = 0;
        this.yr_month = "";
        this.user_id = 0;
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

    public String getYr_month() {
        return yr_month;
    }

    public void setYr_month(String yr_month) {
        this.yr_month = yr_month;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
