package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickSignin(View view) {
        Intent intent =new Intent( this, MessJoin.class);
        startActivity( intent );
    }

    public void clickSignup(View view) {
        Intent intent = new Intent( this, Signup.class);
        startActivity( intent );
    }
}
