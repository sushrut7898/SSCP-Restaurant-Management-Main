package com.example.sushrut.sscp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.sushrut.sscp.App.CHANNEL_1_ID;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class exit extends AppCompatActivity {
    private NotificationManagerCompat notificationManager;

    Button b1;
    EditText scle , sugg , nm , mob;
    TextView txt1;
    int s , rat;
    String ss , nam , rate;
    private final int REQUEST_SEND_SMS = 123;
    DatabaseReference ref, ref1;
    final String str1 = "sscp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        getSupportActionBar().hide();

        notificationManager = NotificationManagerCompat.from(this);


        String title = " Thank You For Visiting Our Restaurant ";

        String nmn = "Bill Paid";

        final Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(title)
                .setContentText(nmn)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();


        notificationManager.notify(1, notification);


        if(ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String []{Manifest.permission.SEND_SMS},REQUEST_SEND_SMS);
        }


        txt1 = findViewById(R.id.tv_rate);

        scle = findViewById(R.id.et_scale);

        sugg = findViewById(R.id.et_sugg);

        nm = findViewById(R.id.et_name);

        mob = findViewById(R.id.et_mob);

        b1 = findViewById(R.id.bt_submit);



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ss = mob.getText().toString();


                nam = nm.getText().toString();

                rate = scle.getText().toString();
                rat = Integer.parseInt(rate);


                if(rat > 3)
                {
                    Toast.makeText(exit.this, "We appreciate the review! We will try to keep up the impression!", Toast.LENGTH_SHORT).show();
                }
                else if(rat > 1 && rat < 4)
                {
                    Toast.makeText(exit.this, "Thanks a lot for the review!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(exit.this, "Please give us suggestions for improvement", Toast.LENGTH_SHORT).show();

                }
                sendSMS(ss,  "Dear "+ nam + ", thank you for visiting the restaurant. Do visit again!\nTeam SSCP");

                if (ChoiceActivity.flagtk[0] != 1)
                {
                    ref = FirebaseDatabase.getInstance().getReference().child("BILL").child(DineActivity.name);
                    ref.removeValue();



                }
                else
                {
                    ref1 = FirebaseDatabase.getInstance().getReference().child("BILL").child(ChoiceActivity.nametk);


                    ref1.removeValue();



                }
            }



    });



    }



    private void sendSMS(String phoneNumber,String message){
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber,null,message,null,null);
    }



    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        android.os.Process.killProcess(Process.myPid());
        System.exit(1);
    }






}
