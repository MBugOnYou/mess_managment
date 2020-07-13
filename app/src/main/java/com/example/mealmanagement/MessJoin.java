package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IUserInfoDao;
import com.example.mealmanagement.imp.UserInfoDao;
import com.example.mealmanagement.model.UserInfo;
import com.example.mealmanagement.util.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MessJoin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_join);
    }


    public void clickJoin(View view) {


        EditText inputMessJoin = (EditText) findViewById(R.id.inputMessJoin);

        if(inputMessJoin.getText().toString().length()<1){
            return;
        }


        UserInfo userInfo = new UserInfo();
        userInfo.setId(PreferenceConnector.getID(MessJoin.this));
        userInfo.setApprove(0);
        userInfo.setManager(0);
        userInfo.setMess_name(inputMessJoin.getText().toString());


        JoinOrCreate(userInfo,false);



    }

    public void clickCreate(View view) {



        EditText inputMessJoin = (EditText) findViewById(R.id.inputMessJoin);

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

                                    }else{
                                        Intent intent = new Intent( MessJoin.this, User.class );
                                        startActivity( intent );

                                    }




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

        }

    }





}
