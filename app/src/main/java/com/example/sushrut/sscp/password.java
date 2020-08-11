package com.example.sushrut.sscp;

import android.app.Activity;
import android.content.Intent;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class password extends AppCompatActivity {
    EditText et1;
    Button btn1;
    DatabaseReference ref, ref1;
    final String str1 = "sscp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().hide();



        et1 = findViewById(R.id.et_password);
        btn1 = findViewById(R.id.btn_proceed);

        et1.setTransformationMethod(new PasswordTransformationMethod());

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    String str = et1.getText().toString();
                    if (str.equals(str1)) {


                        Intent i = new Intent(password.this,ChoiceActivity.class);
                        startActivity(i);

                    } else {
                        Toast.makeText(password.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                    }
                }


        });
    }

    @Override
    public void onBackPressed() {
        if (et1.getText() == null) {
            Toast.makeText(this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
        } else  {

        }
    }

}