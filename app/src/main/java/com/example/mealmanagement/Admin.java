package com.example.mealmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Admin extends AppCompatActivity {

    LinearLayout lnRemoveMember,lnAddMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);








        lnAddMember = findViewById(R.id.lnAddMember);
        lnAddMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this,AddOrRemoveMember.class);
                intent.putExtra("isAddMember",1);
                startActivity(intent);

            }
        });



        lnRemoveMember = findViewById(R.id.lnRemoveMember);
        lnRemoveMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Admin.this,AddOrRemoveMember.class);
                intent.putExtra("isAddMember",0);
                startActivity(intent);

            }
        });


    }
}
