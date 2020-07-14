package com.example.mealmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.example.mealmanagement.dao.IUserInfoDao;
import com.example.mealmanagement.imp.DailyMealDao;
import com.example.mealmanagement.imp.UserInfoDao;
import com.example.mealmanagement.model.DailyMeal;
import com.example.mealmanagement.model.UserInfo;
import com.example.mealmanagement.util.DateUtil;
import com.example.mealmanagement.util.PreferenceConnector;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class User extends AppCompatActivity {


    LinearLayout linDepositAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        linDepositAmount =findViewById(R.id.linDepositAmount);
        linDepositAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(User.this,DepositAmountActivity.class);
                intent.putExtra("isFromAdmin",0);
                startActivity(intent);
            }
        });

    }

    public void clickFloatingButton(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this );
        View view = getLayoutInflater().inflate(R.layout.dailyinput, null);

        final EditText inputBreakfast = (EditText) view.findViewById(R.id.inputBreakfast);
        final EditText inputLunch = (EditText) view.findViewById(R.id.inputLunch);
        final EditText inputDinner = (EditText) view.findViewById(R.id.inputDinner);

        final TextView setBreakfast = (TextView) findViewById(R.id.setBreakfast);
        final TextView setLunch = (TextView) findViewById(R.id.setLunch);
        final TextView setDinner = (TextView) findViewById(R.id.setDinner);

        builder.setTitle(" Set Meal");
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String breakfast = inputBreakfast.getText().toString();
                String lunch = inputLunch.getText().toString();
                String dinner = inputDinner.getText().toString();

                setBreakfast.setText(breakfast);
                setLunch.setText(lunch);
                setDinner.setText(dinner);


                Calendar c = Calendar.getInstance();
                String yearMonth = DateUtil.getYear(new Date())+"-"+DateUtil.getMonth(c.get(Calendar.MONTH));

                DailyMeal dailyMeal = new DailyMeal();
                dailyMeal.setDinner(Integer.parseInt(dinner));
                dailyMeal.setLunch(Integer.parseInt(lunch));
                dailyMeal.setBreakfast(Integer.parseInt(breakfast));
                dailyMeal.setUser_id((int) PreferenceConnector.getID(User.this));
                dailyMeal.setCreation_date(DateUtil.GetFormatedDateString(new Date()));
                dailyMeal.setYr_month(yearMonth);
                dailyMeal.setTotal_meal(dailyMeal.getDinner()+dailyMeal.getBreakfast()+dailyMeal.getLunch());

                SetDailyMeal(dailyMeal);




            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.setCancelable(false);
        builder.setView( view );
        builder.show();

    }


    @Override
    protected void onResume() {
        super.onResume();


        getDailyMealByDate();

    }

    private void SetDailyMeal(DailyMeal dailyMeal) {

        try {
            // RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);


            JSONObject params = new JSONObject();

            try {
                params.put("user_id", dailyMeal.getUser_id());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("breakfast", dailyMeal.getBreakfast());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("lunch", dailyMeal.getLunch());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("dinner", dailyMeal.getDinner());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                params.put("creation_date", dailyMeal.getCreation_date());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("total_meal", dailyMeal.getTotal_meal());
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                params.put("yr_month", dailyMeal.getYr_month());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,Constant.createDailyMeal, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDailyMealDao iUserDao = new DailyMealDao(
                                        User.this);

                                ArrayList<DailyMeal> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {




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



    private void getDailyMealByDate() {

        try {
            // RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);


            JSONObject params = new JSONObject();


            try {
                params.put("user_id", PreferenceConnector.getID(User.this));
            } catch (JSONException e) {
                e.printStackTrace();
            }



            try {
                params.put("creation_date", DateUtil.GetFormatedDateString(new Date()));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,Constant.getDailyMealByDate, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {


                            try {

                                IDailyMealDao iUserDao = new DailyMealDao(
                                        User.this);

                                final ArrayList<DailyMeal> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            final TextView setBreakfast = (TextView) findViewById(R.id.setBreakfast);
                                            final TextView setLunch = (TextView) findViewById(R.id.setLunch);
                                            final TextView setDinner = (TextView) findViewById(R.id.setDinner);

                                            setBreakfast.setText(userinfoArrayList.get(0).getBreakfast()+"");
                                            setLunch.setText(userinfoArrayList.get(0).getLunch()+"");
                                            setDinner.setText(userinfoArrayList.get(0).getDinner()+"");
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
