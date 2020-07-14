package com.example.mealmanagement.dao;


import com.example.mealmanagement.model.DailyCost;
import com.example.mealmanagement.model.DepositAmount;

import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public interface IDepositAmount {

    ArrayList<DepositAmount> GetAppdataFromJSONObject(JSONObject json) throws Exception;



}
