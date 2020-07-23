package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmanagement.adapter.ActivityTotalMealAdapter;
import com.example.mealmanagement.adapter.DepositMoneyAdapter1;
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IDailyMealDao;
import com.example.mealmanagement.dao.IDepositAmount;
import com.example.mealmanagement.imp.DailyMealDao;
import com.example.mealmanagement.imp.DepositAmountDao;
import com.example.mealmanagement.model.DailyMeal;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.util.DateUtil;
import com.example.mealmanagement.util.PreferenceConnector;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TotalMealActivity extends AppCompatActivity {
    KProgressHUD hud;
    ActivityTotalMealAdapter activityTotalMealAdapter;
    RecyclerView recycler_view;
    LinearLayoutManager mLayoutManager;
    ArrayList<DailyMeal> dailyMealArrayList;
    int isFromAdmin = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_meal);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Total Meal");


        isFromAdmin = getIntent().getIntExtra("isFromAdmin",0);

        dailyMealArrayList = new ArrayList<>();
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        activityTotalMealAdapter = new ActivityTotalMealAdapter(dailyMealArrayList, TotalMealActivity.this, m_onlistner);
        mLayoutManager = new LinearLayoutManager(TotalMealActivity.this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(activityTotalMealAdapter);


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
        Animatoo.animateSwipeRight(TotalMealActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(isFromAdmin==0) {
            getTotalMealByMonthAndUserID();
        }else{

            getTotalMealByMonth();
        }

    }

    ActivityTotalMealAdapter.onSelectedPlaceListener m_onlistner = new ActivityTotalMealAdapter.onSelectedPlaceListener() {
        @Override
        public void onClick(DailyMeal place) {




        }
    };




    private void getTotalMealByMonth() {

        showProgress(TotalMealActivity.this);


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
                params.put("mess_name", PreferenceConnector.getMessname(TotalMealActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.getTotalMealByMonth, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDailyMealDao iUserDao = new DailyMealDao(
                                        TotalMealActivity.this);

                                final ArrayList<DailyMeal> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {



                                            activityTotalMealAdapter.setData(userinfoArrayList);
                                            activityTotalMealAdapter.notifyDataSetChanged();







                                        }
                                    });


                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();


                    dismissProgress(TotalMealActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(TotalMealActivity.this);
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
            dismissProgress(TotalMealActivity.this);

        }




    }




    private void getTotalMealByMonthAndUserID() {

        showProgress(TotalMealActivity.this);


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
                params.put("user_id", PreferenceConnector.getID(TotalMealActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("mess_name", PreferenceConnector.getMessname(TotalMealActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.getTotalMealByMonthAndUserID, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDailyMealDao iUserDao = new DailyMealDao(
                                        TotalMealActivity.this);

                                final ArrayList<DailyMeal> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {



                                            activityTotalMealAdapter.setData(userinfoArrayList);
                                            activityTotalMealAdapter.notifyDataSetChanged();







                                        }
                                    });


                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();

                    dismissProgress(TotalMealActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(TotalMealActivity.this);
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
            dismissProgress(TotalMealActivity.this);

        }




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


}