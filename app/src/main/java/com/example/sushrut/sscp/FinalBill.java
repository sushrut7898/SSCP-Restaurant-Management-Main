package com.example.sushrut.sscp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FinalBill extends AppCompatActivity {
    TextView tv1,tv2;
    int tot = 0;
    DatabaseReference ref,ref1;
    String str = "";
    String total = "";
    FloatingActionButton bill ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_final_bill);
        getSupportActionBar().hide();
        tv1 = findViewById(R.id.tv_bill);
        tv1.setMovementMethod(new ScrollingMovementMethod());
        tv2 = findViewById(R.id.tvgrand);
        bill = findViewById(R.id.exitbutton);

        if(ChoiceActivity.flagtk[0] != 1) {
            ref = FirebaseDatabase.getInstance().getReference().child("BILL").child(DineActivity.name);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        str = str + "\n" + ds.child("Name").getValue().toString();
                        str = str + "\nQuantity :- " + ds.child("Quantity").getValue().toString();
                        str = str + "\t\tTotal Price :- " + ds.child("Total").getValue().toString() + "\n";
                        tv1.setText(str);
                        tot = tot + Integer.parseInt(ds.child("Total").getValue().toString());
                    }
                    total = total + "\nGRAND TOTAL :- " + tot;
                    tv2.setText(total);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


        else {
            ref1 = FirebaseDatabase.getInstance().getReference().child("BILL").child(ChoiceActivity.nametk);
            ref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        str = str + "\n" + ds.child("Name").getValue().toString();
                        str = str + "\nQuantity :- " + ds.child("Quantity").getValue().toString();
                        str = str + "\t\tTotal Price :- " + ds.child("Total").getValue().toString() + "\n";
                        tv1.setText(str);
                        tot = tot + Integer.parseInt(ds.child("Total").getValue().toString());
                    }
                    total = total + "\nGRAND TOTAL :- " + tot;
                    tv2.setText(total);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FinalBill.this,exit.class);
                startActivity(intent);
            }
        });




    }
}
