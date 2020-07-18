package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IDepositAmount;
import com.example.mealmanagement.imp.DepositAmountDao;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.util.DateUtil;
import com.example.mealmanagement.util.PreferenceConnector;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PreviousMonthActivity extends AppCompatActivity {

    TextView txtMonthName,txtTotalCost,txtMillRate;
    Button btnCalculate;
    RecyclerView recycler_view;
    KProgressHUD hud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_month);

        txtMonthName = findViewById(R.id.txtMonthName);
        txtTotalCost = findViewById(R.id.txtTotalCost);
        txtMillRate = findViewById(R.id.txtMillRate);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        recycler_view = findViewById(R.id.recycler_view);


    }


    @Override
    protected void onResume() {
        super.onResume();

        getTotalDailyCostByMonth();

    }




    private void getTotalDailyCostByMonth() {


        showProgress(PreviousMonthActivity.this);

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


                    try {
                        final int  totalCost = Integer.parseInt(response.getString("totalCost"));
                        final int  totalMeal = Integer.parseInt(response.getString("totalmeal"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                txtTotalCost.setText("TotalCost: "+totalCost+"");
                                txtMonthName.setText("Month: "+yearMonth+"");
                                double millrate = totalCost/totalMeal;
                                txtMillRate.setText("Mill Rate: "+millrate+"");

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }






                    dismissProgress(PreviousMonthActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(PreviousMonthActivity.this);
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
            dismissProgress(PreviousMonthActivity.this);

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