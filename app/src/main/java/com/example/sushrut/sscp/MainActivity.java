package com.example.sushrut.sscp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.app.Notification;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import java.util.ArrayList;

import static com.example.sushrut.sscp.App.CHANNEL_1_ID;


public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;

    private DatabaseReference ref;
        private FloatingActionButton billb;
        private ListView listView;
        private ArrayList<String> arrayList = new ArrayList<>();
        ViewFlipper viewFlipper;

        String table = DineActivity.name;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_main);

            int images[] = {R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4,R.drawable.food5,R.drawable.food6,R.drawable.food7};


            getSupportActionBar().hide();

            notificationManager = NotificationManagerCompat.from(this);

            String title = " Welcome To SSCP ";
            String nm = "Table no Allocated : " + table;

            final Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                    .setSmallIcon(R.drawable.logo)
                    .setContentTitle(title)
                    .setContentText(nm)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            notificationManager.notify(1, notification);




            billb = findViewById(R.id.float_bill);
            viewFlipper = findViewById(R.id.autoslider);

            listView = findViewById(R.id.menu_list);

//            for(int i = 0;i<images.length;i++){
//                flipperImages(images[i]);
//            }
//

            for(int image:images){
                flipperImages(image);
            }


            ref = FirebaseDatabase.getInstance().getReference().child("FOOD");
            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
            listView.setAdapter(arrayAdapter);

            billb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this,FinalBill.class);
                    startActivity(intent);
                }

            });



            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //openDialog();

                    Intent intent = new Intent(MainActivity.this , Bill.class);
                    intent.putExtra("Food Item" , (String) listView.getItemAtPosition(position));
                    startActivity(intent);


                }

            });




           ref.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                    {

                       int i = 1;
                       for(DataSnapshot ds : dataSnapshot.getChildren())
                       {
                           ++i;
                           if(i%2 == 0)
                           {
                               String val = ds.getValue(String.class);
                               arrayList.add(val);
                           }

                       }

                    }

                    arrayAdapter.notifyDataSetChanged();
                }
                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }
                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

    private void setSupportActionBar(Toolbar toolbar) {
    }

    private void openDialog(){
        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"Example Dialog");
    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);

        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(this,android.R.anim.slide_in_left);



    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please choose billing option to exit", Toast.LENGTH_SHORT).show();
    }
}