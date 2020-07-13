package com.example.mealmanagement.model;

public class DailyMeal {

    long id;
    int user_id;
    int breakfast;
    int lunch;
    int dinner;
    String creation_date;

    public DailyMeal() {
        this.id = 0;
        this.user_id = 0;
        this.breakfast = 0;
        this.lunch = 0;
        this.dinner = 0;
        this.creation_date="";
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

    public int getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(int breakfast) {
        this.breakfast = breakfast;
    }

    public int getLunch() {
        return lunch;
    }

    public void setLunch(int lunch) {
        this.lunch = lunch;
    }

    public int getDinner() {
        return dinner;
    }

    public void setDinner(int dinner) {
        this.dinner = dinner;
    }


    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }
}
