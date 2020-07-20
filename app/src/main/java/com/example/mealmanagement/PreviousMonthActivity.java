package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
import com.example.mealmanagement.dao.IPreviousMonthDao;
import com.example.mealmanagement.imp.PreviousMonthDao;
import com.example.mealmanagement.model.DailyMeal;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.model.PreviousMonth;
import com.example.mealmanagement.model.UserInfo;
import com.example.mealmanagement.util.DateUtil;
import com.example.mealmanagement.util.PreferenceConnector;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PreviousMonthActivity extends AppCompatActivity {



    RecyclerView recycler_view;
    KProgressHUD hud;
    ArrayList<PreviousMonth>previousMonthArrayList;
    CalculateMillRateAdapter calculateMillRateAdapter;
    LinearLayoutManager mLayoutManager;
    int isFromAdmin = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_month2);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Previous Month");


        isFromAdmin = getIntent().getIntExtra("isFromAdmin",0);

        previousMonthArrayList = new ArrayList<>();
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        calculateMillRateAdapter = new CalculateMillRateAdapter(previousMonthArrayList, PreviousMonthActivity.this, m_onlistner);
        mLayoutManager = new LinearLayoutManager(PreviousMonthActivity.this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(calculateMillRateAdapter);



    }


    CalculateMillRateAdapter.onSelectedPlaceListener m_onlistner = new CalculateMillRateAdapter.onSelectedPlaceListener() {
        @Override
        public void onClick(PreviousMonth place) {




        }
    };


    @Override
    protected void onResume() {
        super.onResume();

        getAllPreviousMontdataFromServer();
    }

    private void getAllPreviousMontdataFromServer() {


        // showProgress(PreviousMonthActivity.this);

        String url = "";

        Calendar c = Calendar.getInstance();

         String yearMonth = "";


            c.add(Calendar.MONTH, -1);
            yearMonth = DateUtil.getYear(new Date())+"-"+DateUtil.getMonth(c.get(Calendar.MONTH));



        try {

            JSONObject params = new JSONObject();


            try {
                params.put("yr_month", yearMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(isFromAdmin==0){
                url = Constant.getPreviousMonthByUserID;
                try {
                    params.put("user_id", PreferenceConnector.getID(PreviousMonthActivity.this));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                url = Constant.getPreviousMonthByMonth;
            }

            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            IPreviousMonthDao previousMonthDao = new PreviousMonthDao(PreviousMonthActivity.this);
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
        Animatoo.animateSwipeRight(PreviousMonthActivity.this);
    }

}