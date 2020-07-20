package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmanagement.adapter.CalculateMillRateAdapter;
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IDailyMealDao;
import com.example.mealmanagement.dao.IDepositAmount;
import com.example.mealmanagement.dao.IPreviousMonthDao;
import com.example.mealmanagement.dao.IUserInfoDao;
import com.example.mealmanagement.imp.DailyMealDao;
import com.example.mealmanagement.imp.DepositAmountDao;
import com.example.mealmanagement.imp.PreviousMonthDao;
import com.example.mealmanagement.imp.UserInfoDao;
import com.example.mealmanagement.model.DailyMeal;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.model.PreviousMonth;
import com.example.mealmanagement.model.UserInfo;
import com.example.mealmanagement.util.DateUtil;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalculateMealRateActivity extends AppCompatActivity {

    TextView txtMonthName,txtTotalCost,txtMillRate,txttotalMeal;
    Button btnCalculate;
    RecyclerView recycler_view;
    KProgressHUD hud;
    ArrayList<DailyMeal>dailyMealArrayList;
    ArrayList<DepositAmount>depositAmountArrayList;
    ArrayList<UserInfo>userInfoArrayList;
    ArrayList<PreviousMonth>previousMonthArrayList;

    CalculateMillRateAdapter calculateMillRateAdapter;
    LinearLayoutManager mLayoutManager;
    int currentPosition = 0;


     int totalMeal;
     int totalCost;
     double millrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_month);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Calculate Meal Rate");


        txttotalMeal=findViewById(R.id.txttotalMeal);
        txtMonthName = findViewById(R.id.txtMonthName);
        txtTotalCost = findViewById(R.id.txtTotalCost);
        txtMillRate = findViewById(R.id.txtMillRate);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(userInfoArrayList == null || userInfoArrayList.size() ==0){
                    return;
                }

                if(depositAmountArrayList == null || depositAmountArrayList.size() ==0){
                    return;
                }

                if(dailyMealArrayList == null || dailyMealArrayList.size() ==0){
                    return;
                }


                showProgress(CalculateMealRateActivity.this);
                uploadMillRateToServer();




            }
        });


        previousMonthArrayList = new ArrayList<>();
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        calculateMillRateAdapter = new CalculateMillRateAdapter(previousMonthArrayList, CalculateMealRateActivity.this, m_onlistner);
        mLayoutManager = new LinearLayoutManager(CalculateMealRateActivity.this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(calculateMillRateAdapter);


    }

    private void uploadMillRateToServer() {


        if(userInfoArrayList.size()>currentPosition){

            PreviousMonth previousMonth = new PreviousMonth();
            previousMonth.setUser_id((int) userInfoArrayList.get(currentPosition).getId());
            previousMonth.setMeal_rate(millrate+"");
            previousMonth.setTotal_deposit(calculeTotalDepositByUserID(userInfoArrayList.get(currentPosition).getId()));
            previousMonth.setTotal_cost(calculateTotalCostByUserID(userInfoArrayList.get(currentPosition).getId()));
            previousMonth.setGiven_money(calculateGivenMoney(previousMonth.getTotal_deposit(),previousMonth.getTotal_cost()));
            previousMonth.setExtra_money(calculateExtraMoney(previousMonth.getTotal_deposit(),previousMonth.getTotal_cost()));




            uploadToServerData(previousMonth);


        }else{
            dismissProgress(CalculateMealRateActivity.this);
            getAllPreviousMontdataFromServer();

        }




    }

    private void uploadToServerData(PreviousMonth previousMonth) {


       // showProgress(PreviousMonthActivity.this);

        Calendar c = Calendar.getInstance();
        final String yearMonth = DateUtil.getYear(new Date())+"-"+DateUtil.getMonth(c.get(Calendar.MONTH));

        try {

            JSONObject params = new JSONObject();


            try {
                params.put("user_id", previousMonth.getUser_id());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("meal_rate", previousMonth.getMeal_rate());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("total_deposit", previousMonth.getTotal_deposit());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("total_cost", previousMonth.getTotal_cost());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                params.put("extra_money", previousMonth.getExtra_money());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                params.put("given_money", previousMonth.getGiven_money());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                params.put("yr_month", yearMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.createPreviousMonth, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());

                    currentPosition ++ ;
                    uploadMillRateToServer();

                    new Thread(new Runnable() {
                        @Override
                        public void run() {





                        }
                    }).start();




                    //dismissProgress(PreviousMonthActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(CalculateMealRateActivity.this);
                    currentPosition ++ ;
                    uploadMillRateToServer();
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
            dismissProgress(CalculateMealRateActivity.this);
            currentPosition ++ ;
            uploadMillRateToServer();

        }











    }

    private int calculateExtraMoney(int total_deposit, int total_cost) {

        int totalExtraMoney=0;


        if(total_deposit >= total_cost) {
            totalExtraMoney = total_deposit - total_cost;
        }


        return totalExtraMoney;



    }

    private int calculateGivenMoney(int total_deposit, int total_cost) {
        int totalGivenMoney=0;


        if(total_deposit <= total_cost) {
             totalGivenMoney = total_cost - total_deposit;
        }


        return totalGivenMoney;
    }

    private int calculateTotalCostByUserID(int id) {
        int totalMeal=0;
        int totalCost=0;

        for(int i = 0; i<dailyMealArrayList.size();i++){

            if(id == dailyMealArrayList.get(i).getUser_id()) {
                totalMeal += dailyMealArrayList.get(i).getTotal_meal();
            }

        }

        totalCost = (int) (totalMeal * millrate);


        return totalCost;
    }

    private int calculeTotalDepositByUserID(long id) {

        int totalDeposit=0;

        for(int i = 0; i<depositAmountArrayList.size();i++){

            if(id == depositAmountArrayList.get(i).getUser_id()) {
                totalDeposit += depositAmountArrayList.get(i).getAmount();
            }

        }

        return totalDeposit;
    }


    CalculateMillRateAdapter.onSelectedPlaceListener m_onlistner = new CalculateMillRateAdapter.onSelectedPlaceListener() {
        @Override
        public void onClick(PreviousMonth place) {




        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        getTotalDailyCostByMonth();
        getAllPreviousMontdataFromServer();

    }




    private void getTotalDailyCostByMonth() {


        showProgress(CalculateMealRateActivity.this);

        Calendar c = Calendar.getInstance();
        final String yearMonth = DateUtil.getYear(new Date())+"-"+DateUtil.getMonth(c.get(Calendar.MONTH));

        try {

            JSONObject params = new JSONObject();


            try {
                params.put("yr_month", yearMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }



            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.getTotalDailyCostByMonth, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());



                    new Thread(new Runnable() {
                        @Override
                        public void run() {



                    try {

                        if(response.getString("success").equals("1")) {


                             totalCost = Integer.parseInt(response.getString("totalCost"));
                             totalMeal = Integer.parseInt(response.getString("totalmeal"));


                            IDailyMealDao dailyMealDao = new DailyMealDao(CalculateMealRateActivity.this);

                             dailyMealArrayList= dailyMealDao.GetAppdataFromJSONArray(response.getJSONArray("userMealList"));

                            IDepositAmount depositAmountDao = new DepositAmountDao(CalculateMealRateActivity.this);

                            depositAmountArrayList= depositAmountDao.GetAppdataFromJSONArray(response.getJSONArray("userDepositList"));

                            IUserInfoDao iUserInfoDao = new UserInfoDao(CalculateMealRateActivity.this);

                            userInfoArrayList= iUserInfoDao.GetAppdataFromJSONArray(response.getJSONArray("approveuserList"));


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    txtTotalCost.setText("TotalCost: " + totalCost + "");
                                    txtMonthName.setText("Month: " + yearMonth + "");
                                    txttotalMeal.setText("Total Meal: "+ totalMeal+"");
                                     millrate = totalCost / totalMeal;
                                    txtMillRate.setText("Mill Rate: " + millrate + "");

                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                        }
                    }).start();




                    dismissProgress(CalculateMealRateActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(CalculateMealRateActivity.this);
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
            dismissProgress(CalculateMealRateActivity.this);

        }



    }



    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return (super.onOptionsItemSelected(menuItem));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Bungee.swipeRight(LatestCueCard.this);
        Animatoo.animateSwipeRight(CalculateMealRateActivity.this);
    }


    void showProgress(final Context context) {


        try {

            runOnUiThread(new Runnable() {
                public void run() {
                    hud = KProgressHUD.create(context)
                            .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                            .setLabel("Please wait")
                            .setCancellable(true)
                            .setAnimationSpeed(2)
                            .setDimAmount(0.5f)
                            .show();
                }
            });

        } catch (Exception e) {


        }

    }

    void dismissProgress(Context context) {


        try {

            runOnUiThread(new Runnable() {
                public void run() {
                    if (hud != null && hud.isShowing()) {
                        hud.dismiss();
                        hud = null;
                    }
                }
            });

        } catch (Exception e) {
            e.getMessage();

        }
    }





    private void getAllPreviousMontdataFromServer() {


        // showProgress(PreviousMonthActivity.this);

        Calendar c = Calendar.getInstance();
        final String yearMonth = DateUtil.getYear(new Date())+"-"+DateUtil.getMonth(c.get(Calendar.MONTH));

        try {

            JSONObject params = new JSONObject();


            try {
                params.put("yr_month", yearMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.getPreviousMonthByMonth, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            IPreviousMonthDao previousMonthDao = new PreviousMonthDao(CalculateMealRateActivity.this);
                            try {
                                final ArrayList<PreviousMonth> previousMonths = previousMonthDao.GetAppdataFromJSONObject(response);

                                if(previousMonths!=null && previousMonths.size()>0){

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            calculateMillRateAdapter.setData(previousMonths);
                                            calculateMillRateAdapter.notifyDataSetChanged();

                                        }
                                    });
                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                    }).start();




                    //dismissProgress(PreviousMonthActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    //dismissProgress(PreviousMonthActivity.this);

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
            //dismissProgress(PreviousMonthActivity.this);


        }











    }



}