package com.example.mealmanagement.model;

public class DepositAmount {

    int id;
    int user_id;
    int amount;
    String creation_date;
    String yr_month;
    String name;


    public DepositAmount() {
        this.id = 0;
        this.amount = 0;
        this.user_id = 0;
        this.creation_date = "";
        this.yr_month = "";
        this.name = "";
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
