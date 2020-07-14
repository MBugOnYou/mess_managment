package com.example.mealmanagement.model;

public class DepositAmount {

    long id;
    int user_id;
    int amount;
    String creation_date;
    String yr_month;


    public DepositAmount() {
        this.id = 0;
        this.amount = 0;
        this.user_id = 0;
        this.creation_date = "";
        this.yr_month = "";
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getYr_month() {
        return yr_month;
    }

    public void setYr_month(String yr_month) {
        this.yr_month = yr_month;
    }
}
