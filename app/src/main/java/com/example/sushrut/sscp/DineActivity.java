package com.example.sushrut.sscp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sushrut.sscp.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import static com.example.sushrut.sscp.App.CHANNEL_1_ID;


public class DineActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;

    Button proceed;
    EditText seats;
    DatabaseReference ref;
    static String name ,j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dine);
        getSupportActionBar().hide();

        proceed = findViewById(R.id.button_proceed);
        seats =  findViewById(R.id.dine_seats);
        notificationManager = NotificationManagerCompat.from(this);


        String title = " Welcome To SSCP ";

        String nm = "You Are In Waiting";

        final Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(nm)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                j = seats.getText().toString();
                int i = Integer.parseInt(j);
                int k =0;
                if(i<=2 && i>0) {

                    ref = FirebaseDatabase.getInstance().getReference().child("TABLES").child("2");
                    k=1;
                }

                else if(i>=3 && i<=4){

                    ref = FirebaseDatabase.getInstance().getReference().child("TABLES").child("4");
                    k=1;
                }

                else if(i>=5 && i<=8){

                    ref = FirebaseDatabase.getInstance().getReference().child("TABLES").child("8");
                    k=1;
                }


                if(k==1) {
                    final int[] flag = {0};
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                String status = dataSnapshot1.child("Status").getValue(String.class);

                                if (status.equals("0")) {
                                     name = dataSnapshot1.child("Name").getValue(String.class);
                                    Toast.makeText(DineActivity.this, "Table Allocated: "+name, Toast.LENGTH_LONG).show();

                                    ref.child(name).child("Status").setValue("1");
                                    ref.child(name).child("Bill").setValue("1");
                                    flag[0] = 1;


                                    Intent ii = new Intent(DineActivity.this , MainActivity.class);
                                    startActivity(ii);
                                    break;
                                }

                            }

                            if (flag[0] == 0) {
                                Toast.makeText(DineActivity.this, "Sorry,You are in waiting,Try again later.", Toast.LENGTH_LONG).show();
                                notificationManager.notify(1, notification);

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    Toast.makeText(DineActivity.this, "Sorry ! Contact the manager for special arrangement .", Toast.LENGTH_SHORT).show();
                }



            }


        });



    }



}