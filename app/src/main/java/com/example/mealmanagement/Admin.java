package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
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

public class Admin extends AppCompatActivity {
    KProgressHUD hud;
    LinearLayout lnRemoveMember,lnAddMember,linDepositAmount,linTotalMeal,linPreviousMonth,linDailyCost;

    TextView totalMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        getSupportActionBar().setTitle("Admin Panel");

        totalMeal = findViewById(R.id.totalMeal);


        linDailyCost = findViewById(R.id.linDailyCost);
        linDailyCost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Admin.this,DailyCostActivity.class);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Admin.this);




            }
        });



        linDepositAmount = findViewById(R.id.linDepositAmount);
        linDepositAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(Admin.this,DepositAmountActivity.class);
                intent.putExtra("isFromAdmin",1);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Admin.this);




            }
        });


        linTotalMeal = findViewById(R.id.linTotalMeal);
        linTotalMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Admin.this,TotalMealActivity.class);
                intent.putExtra("isFromAdmin",1);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Admin.this);

            }
        });


        linPreviousMonth = findViewById(R.id.linPreviousMonth);
        linPreviousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(Admin.this,PreviousMonthActivity.class));



            }
        });





        lnAddMember = findViewById(R.id.lnAddMember);
        lnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this,AddOrRemoveMember.class);
                intent.putExtra("isAddMember",1);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Admin.this);

            }
        });



        lnRemoveMember = findViewById(R.id.lnRemoveMember);
        lnRemoveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this,AddOrRemoveMember.class);
                intent.putExtra("isAddMember",0);
                startActivity(intent);
                Animatoo.animateSwipeLeft(Admin.this);

            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        getTodayTotalMealFromServer();
    }

    private void getTodayTotalMealFromServer() {

        showProgress(Admin.this);


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

                    dismissProgress(Admin.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(Admin.this);
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
            dismissProgress(Admin.this);

        }














    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            PreferenceConnector.LogoutsaveUser(Admin.this);
            startActivity(new Intent(Admin.this,Signup.class));
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
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
