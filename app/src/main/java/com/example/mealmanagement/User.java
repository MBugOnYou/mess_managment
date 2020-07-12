package com.example.mealmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class User extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
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
}
