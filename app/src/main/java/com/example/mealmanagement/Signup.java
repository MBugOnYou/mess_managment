package com.example.mealmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Signup extends AppCompatActivity {
    KProgressHUD hud;
    //DatabaseReference databaseReference;
    //String userID = "ID-10", nickName, mail, password;
    int increment;
    RadioButton signinRadio,signupRadio;
    EditText inputNickname,inputMail,inputPassword;
    Button Signin,Signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("SignIn or SignUp");

        Signin = (Button) findViewById(R.id.Signin);
        Signup = (Button) findViewById(R.id.Signup);

         inputNickname = (EditText) findViewById(R.id.inputNickname);
         inputMail = (EditText) findViewById(R.id.inputMail);
         inputPassword = (EditText) findViewById(R.id.inputPassword);


        signinRadio =findViewById( R.id.signinRadio);
        signinRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if(isChecked){
                    signinRadio.setChecked(true);
                    signupRadio.setChecked(false);

                    inputNickname.setVisibility(View.GONE);
                    inputMail.setVisibility(View.VISIBLE);
                    inputPassword.setVisibility(View.VISIBLE);

                    Signin.setVisibility(View.VISIBLE);
                    Signup.setVisibility(View.GONE);


                }

            }
        });


        signupRadio =findViewById( R.id.signupRadio);
        signupRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    signinRadio.setChecked(false);
                    signupRadio.setChecked(true);

                    inputNickname.setVisibility(View.VISIBLE);
                    inputMail.setVisibility(View.VISIBLE);
                    inputPassword.setVisibility(View.VISIBLE);

                    Signin.setVisibility(View.GONE);
                    Signup.setVisibility(View.VISIBLE);
                }
            }
        });

        signinRadio.setChecked(true);

    }

    public void clickSignup(View view) {



//        nickName = inputNickname.getText().toString();
//        mail = inputMail.getText().toString();
//        password = inputPassword.getText().toString();


        UserInfo userInfo = new UserInfo();
        userInfo.setApprove(0);
        userInfo.setManager(0);
        userInfo.setMess_name("");
        userInfo.setPassword(inputPassword.getText().toString());
        userInfo.setMail(inputMail.getText().toString().trim());
        userInfo.setName(inputNickname.getText().toString());


        SigninorSingUpToServer(userInfo,false);


    }

    public void clickSignin(View view) {
//        Intent intent = new Intent( this, MainActivity.class);
//        startActivity( intent );


        EditText inputMail = (EditText) findViewById(R.id.inputMail);
        EditText inputPassword = (EditText) findViewById(R.id.inputPassword);


        UserInfo userInfo = new UserInfo();
        userInfo.setApprove(0);
        userInfo.setManager(0);
        userInfo.setMess_name("");
        userInfo.setPassword(inputPassword.getText().toString());
        userInfo.setMail(inputMail.getText().toString().trim());
        userInfo.setName("");


        SigninorSingUpToServer(userInfo,true);

    }

    public void ToastMessage( String Message){
        Toast.makeText(this, Message, Toast.LENGTH_SHORT).show();
    }




    private void SigninorSingUpToServer(UserInfo userInfo, boolean islogin) {

        showProgress(Signup.this);

        try {
            // RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);


            JSONObject params = new JSONObject();

            try {
                params.put("mail", userInfo.getMail());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("password", userInfo.getPassword());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                params.put("name", userInfo.getName());
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


            try {
                params.put("approve", userInfo.getApprove());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String url = "";

            if(islogin){
                url = Constant.login;
            }else{
                url = Constant.signup;
            }


            final String requestBody = params.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST,url, params, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {

                    Log.i("LOG_VOLLEY", response.toString());


                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try{

                                String message = response.getString("message");

                                if(message!=null && message.length()>0) {
                                    showAlertUser(message);
                                    return;
                                }


                            }catch (Exception e){

                            }




                            try {

                                IUserInfoDao iUserDao = new UserInfoDao(
                                        Signup.this);

                                ArrayList<UserInfo> userinfoArrayList;

                                userinfoArrayList = iUserDao.GetAppdataFromJSONObject(response);

                                if (userinfoArrayList != null && userinfoArrayList.size() > 0) {


                                    PreferenceConnector.saveUser(Signup.this,userinfoArrayList.get(0));


                                    int manager = PreferenceConnector.getmanager(Signup.this);
                                    int approve = PreferenceConnector.getapprove(Signup.this);

                                    if(manager==1){
                                        startActivity(new Intent(Signup.this,Admin.class));
                                        Animatoo.animateSwipeLeft(Signup.this);
                                        finish();
                                    }else if(manager == 0 && approve==1){

                                        Intent intent = new Intent(Signup.this, User.class);
                                        startActivity(intent);
                                        Animatoo.animateSwipeLeft(Signup.this);
                                        finish();

                                    }
                                    else {
                                        Intent intent = new Intent(Signup.this, MessJoin.class);
                                        startActivity(intent);
                                        Animatoo.animateSwipeLeft(Signup.this);
                                        finish();
                                    }





                                } else {


                                }

                            } catch (Exception e) {
                                e.printStackTrace();

                            }

                        }
                    }).start();

                    dismissProgress(Signup.this);


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                    dismissProgress(Signup.this);
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
            dismissProgress(Signup.this);

        }

    }


    private void showAlertUser(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog alertDialog = new AlertDialog.Builder(Signup.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage(message);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
        });

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
