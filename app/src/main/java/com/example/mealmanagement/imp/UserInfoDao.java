package com.example.mealmanagement.imp;

import android.content.Context;

import com.example.mealmanagement.dao.IUserInfoDao;
import com.example.mealmanagement.model.UserInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Hafiz_Hp on 12/18/2015.
 */
public class UserInfoDao implements IUserInfoDao {

    public UserInfoDao(Context activity) {


    }


    @Override
    public ArrayList<UserInfo> GetAppdataFromJSONObject(JSONObject json) throws Exception {

        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        UserInfo appData = null;
        JSONObject jsonObject = null;
        try {
            JSONArray jsonArray = json.getJSONArray("data");

                jsonArray = json.getJSONArray("data");

                if (jsonArray != null && !jsonArray.equals("")) {

                    for (int k = 0; k < jsonArray.length(); k++) {

                        jsonObject = jsonArray.getJSONObject(k);
                        appData = new UserInfo();


                        try {
                            appData.setId(jsonObject.getInt("id"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setName(jsonObject.getString("name"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setMail(jsonObject.getString("mail"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setPassword(jsonObject.getString("password"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setMess_name(jsonObject.getString("mess_name"));
                        } catch (Exception ex) {
                        }

                        try {
                            appData.setManager(jsonObject.getInt("manager"));
                        } catch (Exception ex) {
                        }
                        try {
                            appData.setApprove(jsonObject.getInt("approve"));
                        } catch (Exception ex) {
                        }



                        userInfoArrayList.add(appData);
                    }
                }



        } catch (Exception ex) {
            ex.getMessage();
        }

        return userInfoArrayList;
    }

    @Override
    public ArrayList<UserInfo> GetAppdataFromJSONArray(JSONArray json) throws Exception {

        ArrayList<UserInfo> userInfoArrayList = new ArrayList<>();
        //JSONArray jsonArray = null;
        UserInfo appData = null;
        JSONObject jsonObject = null;
        try {


            if (json != null && !json.equals("")) {

                for (int k = 0; k < json.length(); k++) {

                    jsonObject = json.getJSONObject(k);
                    appData = new UserInfo();


                    try {
                        appData.setId(jsonObject.getInt("id"));
                    } catch (Exception ex) {
                    }
                    try {
                        appData.setName(jsonObject.getString("name"));
                    } catch (Exception ex) {
                    }
                    try {
                        appData.setMail(jsonObject.getString("mail"));
                    } catch (Exception ex) {
                    }

                    try {
                        appData.setPassword(jsonObject.getString("password"));
                    } catch (Exception ex) {
                    }
                    try {
                        appData.setMess_name(jsonObject.getString("mess_name"));
                    } catch (Exception ex) {
                    }

                    try {
                        appData.setManager(jsonObject.getInt("manager"));
                    } catch (Exception ex) {
                    }
                    try {
                        appData.setApprove(jsonObject.getInt("approve"));
                    } catch (Exception ex) {
                    }



                    userInfoArrayList.add(appData);
                }
            }



        } catch (Exception ex) {
            ex.getMessage();
        }

        return userInfoArrayList;
    }

}
