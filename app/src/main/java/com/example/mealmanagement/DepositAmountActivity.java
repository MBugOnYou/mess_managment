package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmanagement.adapter.AddOrRemoveMemberAdapter;
import com.example.mealmanagement.adapter.DepositMoneyAdapter1;
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IDepositAmount;
import com.example.mealmanagement.dao.IUserInfoDao;
import com.example.mealmanagement.imp.DepositAmountDao;
import com.example.mealmanagement.imp.UserInfoDao;
import com.example.mealmanagement.model.DepositAmount;
import com.example.mealmanagement.model.UserInfo;
import com.example.mealmanagement.util.DateUtil;
import com.example.mealmanagement.util.PreferenceConnector;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DepositAmountActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    KProgressHUD hud;
    ArrayList<UserInfo> userinfoArrayList;
    EditText edtAmount;
    Button btnDeposit;
    UserInfo selecteduser;

    DepositMoneyAdapter1 depositMoneyAdapter;
    RecyclerView recycler_view;
    LinearLayoutManager mLayoutManager;
    ArrayList<DepositAmount>depositAmountArrayList;
    LinearLayout topView;

    int isFromAdmin = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_amount);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Deposit Amount");

        topView = findViewById(R.id.topView);

        isFromAdmin = getIntent().getIntExtra("isFromAdmin",0);

        if(isFromAdmin==0){
            topView.setVisibility(View.GONE);
        }

        depositAmountArrayList = new ArrayList<>();
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        depositMoneyAdapter = new DepositMoneyAdapter1(depositAmountArrayList, DepositAmountActivity.this, m_onlistner);
        mLayoutManager = new LinearLayoutManager(DepositAmountActivity.this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(depositMoneyAdapter);

        edtAmount = findViewById(R.id.edtAmount);

        btnDeposit = findViewById(R.id.btnDeposit);
        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String amount = edtAmount.getText().toString();

                if(amount!=null && amount.length()<1){

                    return;

                }else if(selecteduser == null){
                    return;
                }

                depositAmountToServer(amount);

            }
        });






    }

    DepositMoneyAdapter1.onSelectedPlaceListener m_onlistner = new DepositMoneyAdapter1.onSelectedPlaceListener() {
        @Override
        public void onClick(DepositAmount place) {




        }
    };

    private void depositAmountToServer(String amount) {


        showProgress(DepositAmountActivity.this);

        Calendar c = Calendar.getInstance();
        String yearMonth = DateUtil.getYear(new Date())+"-"+DateUtil.getMonth(c.get(Calendar.MONTH));

        try {

            JSONObject params = new JSONObject();

            try {
                params.put("user_id", selecteduser.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("amount", amount);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("creation_date", DateUtil.GetFormatedDateString(new Date()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("yr_month", yearMonth);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("mess_name", PreferenceConnector.getMessname(DepositAmountActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,Constant.createDepositMoney, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDepositAmount iUserDao = new DepositAmountDao(
                                        DepositAmountActivity.this);

                                ArrayList<DepositAmount> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            edtAmount.setText("");
                                        }
                                    });


                                    getAllDepositAmountFromServer();


                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();


                    dismissProgress(DepositAmountActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(DepositAmountActivity.this);
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

//                @Override
//                public byte[] getBody() {
//                    try {
//                        return requestBody == null ? null : requestBody.getBytes("utf-8");
//                    } catch (UnsupportedEncodingException uee) {
//                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                        return null;
//                    }
//                }

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
            dismissProgress(DepositAmountActivity.this);

        }


    }

    private void getAllDepositAmountFromServer() {


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
                params.put("mess_name", PreferenceConnector.getMessname(DepositAmountActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,Constant.getDepositMoneyByYearMonth, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDepositAmount iUserDao = new DepositAmountDao(
                                        DepositAmountActivity.this);

                                final ArrayList<DepositAmount> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {



                                            depositMoneyAdapter.setData(userinfoArrayList);
                                            depositMoneyAdapter.notifyDataSetChanged();







                                        }
                                    });


                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();

                    dismissProgress(DepositAmountActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(DepositAmountActivity.this);
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
            dismissProgress(DepositAmountActivity.this);

        }











    }

    @Override
    protected void onResume() {
        super.onResume();

        if(isFromAdmin==1) {
            getUserList();
            getAllDepositAmountFromServer();
        }else{

            getAlldepositAmountByUserIDFromServer();
        }


    }

    private void getAlldepositAmountByUserIDFromServer() {


        showProgress(DepositAmountActivity.this);

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
                params.put("user_id", PreferenceConnector.getID(DepositAmountActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("mess_name", PreferenceConnector.getMessname(DepositAmountActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,Constant.getDepositMoneyByYearMonthAndUserID, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDepositAmount iUserDao = new DepositAmountDao(
                                        DepositAmountActivity.this);

                                final ArrayList<DepositAmount> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {



                                            depositMoneyAdapter.setData(userinfoArrayList);
                                            depositMoneyAdapter.notifyDataSetChanged();







                                        }
                                    });


                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();

                    dismissProgress(DepositAmountActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(DepositAmountActivity.this);
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
            dismissProgress(DepositAmountActivity.this);

        }














    }

    private void initSpinner(ArrayList<UserInfo> userinfoArrayList) {


        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        for(int i = 0;i<userinfoArrayList.size();i++){

            categories.add(userinfoArrayList.get(i).getName());

        }


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        selecteduser = userinfoArrayList.get(position);

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

        selecteduser = null;

    }



    private void getUserList() {

        showProgress(DepositAmountActivity.this);

        try {

            JSONObject params = new JSONObject();

            try {
                params.put("mess_name", PreferenceConnector.getMessname(DepositAmountActivity.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.getAllApprovedUserList, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                if (response.getString("success").equals("1")) {


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {



                                            try {

                                                IUserInfoDao iUserDao = new UserInfoDao(
                                                        DepositAmountActivity.this);



                                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                                    initSpinner(userinfoArrayList);

                                                } else {

                                                }

                                            } catch (Exception e) {
                                                e.printStackTrace();

                                            }

                                        }
                                    });

                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();

                    dismissProgress(DepositAmountActivity.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(DepositAmountActivity.this);
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }


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
            dismissProgress(DepositAmountActivity.this);

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
        Animatoo.animateSwipeRight(DepositAmountActivity.this);
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