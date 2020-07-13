package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.OvershootInterpolator;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.mealmanagement.adapter.AddOrRemoveMemberAdapter;
import com.example.mealmanagement.constant.Constant;
import com.example.mealmanagement.dao.IUserInfoDao;
import com.example.mealmanagement.imp.UserInfoDao;
import com.example.mealmanagement.model.UserInfo;
import com.example.mealmanagement.util.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddOrRemoveMember extends AppCompatActivity {

    private AddOrRemoveMemberAdapter addOrRemoveMemberAdapter;
    RecyclerView recycler_view;
    LinearLayoutManager mLayoutManager;
    ArrayList<UserInfo>userInfoArrayList;

    int isAddmember = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_remove_member);

        isAddmember = getIntent().getIntExtra("isAddMember",0);

        userInfoArrayList = new ArrayList<>();
        recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
        addOrRemoveMemberAdapter = new AddOrRemoveMemberAdapter(isAddmember,userInfoArrayList, AddOrRemoveMember.this, m_onlistner);
        mLayoutManager = new LinearLayoutManager(AddOrRemoveMember.this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(addOrRemoveMemberAdapter);


    }

    @Override
    protected void onResume() {
        super.onResume();


        getUserList();



    }

    AddOrRemoveMemberAdapter.onSelectedPlaceListener m_onlistner = new AddOrRemoveMemberAdapter.onSelectedPlaceListener() {
        @Override
        public void onClick(UserInfo place) {

            MemberAddOrJoinToServer(place);


        }
    };

    private void MemberAddOrJoinToServer(final UserInfo userInfo) {

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

            try {
                params.put("manager", userInfo.getManager());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            if(isAddmember==1){

                try {
                    params.put("approve", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{

                try {
                    params.put("approve", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


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


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {


                                            ArrayList<UserInfo>tempuserinfo = addOrRemoveMemberAdapter.getData();

                                            if(tempuserinfo!=null && tempuserinfo.size()>0){
                                                for (int i = 0;i <tempuserinfo.size();i++){

                                                    if(tempuserinfo.get(i).getId()== userInfo.getId()){
                                                        tempuserinfo.remove(i);
                                                        break;
                                                    }
                                                }

                                                addOrRemoveMemberAdapter.setData(tempuserinfo);
                                                addOrRemoveMemberAdapter.notifyDataSetChanged();



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








    private void getUserList() {

        try {
            // RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);


            JSONObject params = new JSONObject();

            if(isAddmember==1){

                try {
                    params.put("approve", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else {

                try {
                    params.put("approve", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,Constant.getAllUserApproveStatus, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IUserInfoDao iUserDao = new UserInfoDao(
                                        AddOrRemoveMember.this);

                                final ArrayList<UserInfo> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {


                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            addOrRemoveMemberAdapter.setData(userinfoArrayList);
                                            addOrRemoveMemberAdapter.notifyDataSetChanged();
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