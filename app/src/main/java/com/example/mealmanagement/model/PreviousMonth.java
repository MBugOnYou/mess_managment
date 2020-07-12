package com.example.mealmanagement.model;

public class PreviousMonth {

    long id;
    int user_id;
    int meal_rate;
    int total_deposit;
    int total_cost;
    int extra_money;
    int given_money;


    public PreviousMonth() {
        this.id = 0;
        this.user_id = 0;
        this.meal_rate = 0;
        this.total_deposit = 0;
        this.total_cost = 0;
        this.extra_money = 0;
        this.given_money = 0;
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

    public int getMeal_rate() {
        return meal_rate;
    }

    public void setMeal_rate(int meal_rate) {
        this.meal_rate = meal_rate;
    }

    public int getTotal_deposit() {
        return total_deposit;
    }

    public void setTotal_deposit(int total_deposit) {
        this.total_deposit = total_deposit;
    }

    public int getTotal_cost() {
        return total_cost;
    }

    public void setTotal_cost(int total_cost) {
        this.total_cost = total_cost;
    }

    public int getExtra_money() {
        return extra_money;
    }

    public void setExtra_money(int extra_money) {
        this.extra_money = extra_money;
    }

    public int getGiven_money() {
        return given_money;
    }

    public void setGiven_money(int given_money) {
        this.given_money = given_money;
    }
}
