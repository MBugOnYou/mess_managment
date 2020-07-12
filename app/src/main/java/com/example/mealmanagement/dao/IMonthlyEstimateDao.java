package com.example.mealmanagement.dao;


import com.example.mealmanagement.model.MonthlyEstimate;
import com.example.mealmanagement.model.UserInfo;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public interface IMonthlyEstimateDao {

    ArrayList<MonthlyEstimate> GetAppdataFromJSONObject(JSONObject json) throws Exception;



}
