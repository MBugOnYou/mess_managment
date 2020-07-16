package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IUserInfoDao;
import com.example.mealmanagement.imp.UserInfoDao;
import com.example.mealmanagement.model.UserInfo;
import com.example.mealmanagement.util.PreferenceConnector;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessJoin extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    KProgressHUD hud;
    RadioButton joinRd,createRD;
    Button MessJoin,MessCreate;
    EditText inputMessJoin;
    Spinner spinner;
    String messName="";
    ArrayList<UserInfo> userinfoArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_join);

        getSupportActionBar().setTitle("Mess Join Or Create");

        spinner = (Spinner) findViewById(R.id.spinner);
        MessJoin = (Button) findViewById(R.id.MessJoin);
        MessCreate = (Button) findViewById(R.id.MessCreate);
         inputMessJoin = (EditText) findViewById(R.id.inputMessJoin);


        joinRd =findViewById( R.id.joinRd);
        joinRd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    joinRd.setChecked(true);
                    createRD.setChecked(false);

                    inputMessJoin.setVisibility(View.GONE);
                    spinner.setVisibility(View.VISIBLE);

                    MessJoin.setVisibility(View.VISIBLE);
                    MessCreate.setVisibility(View.GONE);


                }

            }
        });


        createRD =findViewById( R.id.createRD);
        createRD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    joinRd.setChecked(false);
                    createRD.setChecked(true);

                    inputMessJoin.setVisibility(View.VISIBLE);
                    spinner.setVisibility(View.GONE);

                    MessJoin.setVisibility(View.GONE);
                    MessCreate.setVisibility(View.VISIBLE);
                }
            }
        });

        joinRd.setChecked(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getMessnameList();
    }

    private void getMessnameList() {

        showProgress(MessJoin.this);

        try {

            JSONObject params = new JSONObject();


            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, Constant.getAllMessList, params, new Response.Listener<JSONObject>() {
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
                                                        MessJoin.this);



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

                    dismissProgress(MessJoin.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(MessJoin.this);
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
            dismissProgress(MessJoin.this);

        }

    }



    private void initSpinner(ArrayList<UserInfo> userinfoArrayList) {


        // Spinner element


        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();

        for(int i = 0;i<userinfoArrayList.size();i++){

            categories.add(userinfoArrayList.get(i).getMess_name());

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

        messName = item;

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

        messName = "";

    }



    public void clickJoin(View view) {




        if(joinRd.isChecked()==true && messName.equals("")){
            return;
        }


        UserInfo userInfo = new UserInfo();
        userInfo.setId(PreferenceConnector.getID(MessJoin.this));
        userInfo.setApprove(0);
        userInfo.setManager(0);
        userInfo.setMess_name(messName);


        JoinOrCreate(userInfo,false);



    }

    public void clickCreate(View view) {



         inputMessJoin = (EditText) findViewById(R.id.inputMessJoin);

        if(inputMessJoin.getText().toString().length()<1){
            return;
        }




        UserInfo userInfo = new UserInfo();
        userInfo.setId(PreferenceConnector.getID(MessJoin.this));
        userInfo.setApprove(0);
        userInfo.setManager(0);
        userInfo.setMess_name(inputMessJoin.getText().toString());


        JoinOrCreate(userInfo,true);

    }




    private void JoinOrCreate(UserInfo userInfo, final boolean isCreate) {

        showProgress(MessJoin.this);

        try {
            // RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);


            JSONObject params = new JSONObject();

            try {
                params.put("id", userInfo.getId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("mess_name", userInfo.getMess_name());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if(isCreate) {
                try {
                    params.put("manager", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }else{

                try {
                    params.put("manager", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            try {
                params.put("approve", 0);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,Constant.updateUserInfoModel, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                if (response.getString("success").equals("1")) {


                                    //PreferenceConnector.saveUser(MessJoin.this,userinfoArrayList.get(0));


                                    if(isCreate){

                                        Intent intent = new Intent( MessJoin.this, Admin.class );
                                       startActivity( intent );
                                        Animatoo.animateSwipeLeft(MessJoin.this);

                                    }else{
                                        Intent intent = new Intent( MessJoin.this, User.class );
                                        startActivity( intent );
                                        Animatoo.animateSwipeLeft(MessJoin.this);

                                    }




                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();

                    dismissProgress(MessJoin.this);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(MessJoin.this);
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
            dismissProgress(MessJoin.this);

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
        Animatoo.animateSwipeRight(MessJoin.this);
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
