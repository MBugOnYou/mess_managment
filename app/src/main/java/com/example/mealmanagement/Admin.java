package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IDailyMealDao;
import com.example.mealmanagement.dao.IDepositAmount;
import com.example.mealmanagement.imp.DailyMealDao;
import com.example.mealmanagement.imp.DepositAmountDao;
import com.example.mealmanagement.model.DailyMeal;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.util.DateUtil;
import com.example.mealmanagement.util.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Admin extends AppCompatActivity {

    LinearLayout lnRemoveMember,lnAddMember,linDepositAmount,linTotalMeal,linPreviousMonth;

    TextView totalMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        totalMeal = findViewById(R.id.totalMeal);



        linDepositAmount = findViewById(R.id.linDepositAmount);
        linDepositAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Admin.this,DepositAmountActivity.class);
                intent.putExtra("isFromAdmin",1);
                startActivity(intent);




            }
        });

        linTotalMeal = findViewById(R.id.linTotalMeal);
        linTotalMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        linPreviousMonth = findViewById(R.id.linPreviousMonth);
        linPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });





        lnAddMember = findViewById(R.id.lnAddMember);
        lnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this,AddOrRemoveMember.class);
                intent.putExtra("isAddMember",1);
                startActivity(intent);

            }
        });



        lnRemoveMember = findViewById(R.id.lnRemoveMember);
        lnRemoveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this,AddOrRemoveMember.class);
                intent.putExtra("isAddMember",0);
                startActivity(intent);

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        getTodayTotalMealFromServer();
    }

    private void getTodayTotalMealFromServer() {



        Calendar c = Calendar.getInstance();
        String yearMonth = DateUtil.getYear(new Date())+"-"+DateUtil.getMonth(c.get(Calendar.MONTH));

        try {

            JSONObject params = new JSONObject();


            try {
                params.put("yr_month", yearMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("creation_date", DateUtil.GetFormatedDateString(new Date()));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.getTotalDailyMealByDate, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDailyMealDao iUserDao = new DailyMealDao(
                                        Admin.this);

                                final ArrayList<DailyMeal> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            int totalmeal = 0;

                                            for(int i = 0;i<userinfoArrayList.size();i++){
                                                totalmeal+=userinfoArrayList.get(i).getTotal_meal();
                                            }


                                            totalMeal.setText(totalmeal+"");



                                        }
                                    });


                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

//

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(60 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MyApplication.getInstance().getRequestQueue().getCache().clear();
            MyApplication.getInstance().addToRequestQueue(stringRequest, "string_req");
        } catch (Exception e) {
            e.getMessage();

        }














    }
}
