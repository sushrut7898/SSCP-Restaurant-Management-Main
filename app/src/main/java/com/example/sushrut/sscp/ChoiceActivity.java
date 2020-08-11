package com.example.sushrut.sscp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class ChoiceActivity extends AppCompatActivity {



    Button button_dine;
    Button button_takeaway;
    DatabaseReference ref;
   static  String nametk;
    static final int[] flagtk = {0};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choice2);
        getSupportActionBar().hide();

        button_dine = findViewById(R.id.button_dine);
        button_takeaway = findViewById(R.id.button_takeaway);

        button_dine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent choice1 = new Intent(ChoiceActivity.this, DineActivity.class);
                startActivity(choice1);
            }
        });

        button_takeaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref = FirebaseDatabase.getInstance().getReference().child("TAKEAWAY");
                final int[] flag = {0};
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                            String status = dataSnapshot1.child("Status").getValue(String.class);

                            if (status.equals("0")) {
                                nametk = dataSnapshot1.child("Name").getValue(String.class);
                                Toast.makeText(ChoiceActivity.this, ""+nametk, Toast.LENGTH_SHORT).show();

                                ref.child(nametk).child("Status").setValue("1");
                                ref.child(nametk).child("Bill").setValue("1");
                                flag[0] = 1;

                                flagtk[0] = 1;
                                Intent intent = new Intent(ChoiceActivity.this,MainActivity.class);
                                startActivity(intent);
                                break;
                            }


                        }
                        if (flag[0] == 0) {
                            Toast.makeText(ChoiceActivity.this, "Your are in waiting list", Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });


            }

        });
    }
}