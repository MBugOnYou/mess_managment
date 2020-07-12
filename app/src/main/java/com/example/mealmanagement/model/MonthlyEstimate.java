package com.example.mealmanagement.model;

public class MonthlyEstimate {
    long id;
    int user_id;
    String date;
    int meal;
    int deposita;


    public MonthlyEstimate() {
        this.id = 0;
        this.user_id = 0;
        this.date = "";
        this.meal = 0;
        this.deposita = 0;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMeal() {
        return meal;
    }

    public void setMeal(int meal) {
        this.meal = meal;
    }

    public int getDeposita() {
        return deposita;
    }

    public void setDeposita(int deposita) {
        this.deposita = deposita;
    }
}
