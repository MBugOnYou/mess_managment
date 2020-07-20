package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.mealmanagement.util.PreferenceConnector;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {


                int userid = PreferenceConnector.getID(SplashScreenActivity.this);
                int manager = PreferenceConnector.getmanager(SplashScreenActivity.this);
                int approve = PreferenceConnector.getapprove(SplashScreenActivity.this);
                if(userid==0){

                    startActivity(new Intent(SplashScreenActivity.this,Signup.class));

                }else{

                    if(manager==1){
                        startActivity(new Intent(SplashScreenActivity.this,Admin.class));
                    }else if(manager==0 && approve == 1){
                        startActivity(new Intent(SplashScreenActivity.this,User.class));
                    }else{
                        startActivity(new Intent(SplashScreenActivity.this,Signup.class));
                    }



                }

                finish();




            }
        },3000);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}