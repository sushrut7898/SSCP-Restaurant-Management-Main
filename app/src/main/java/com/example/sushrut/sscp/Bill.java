package com.example.sushrut.sscp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.HashMap;

public class Bill extends AppCompatActivity {

    DatabaseReference ref,ref1,ref2 ;

    TextView tvpri , tvnm;
    EditText qt;
    String price;
    String tno;
    Button btn;
    int total , grandtotal;
    Bill c1;
    int ch = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);
        getSupportActionBar().hide();


        qt = (EditText)findViewById(R.id.qty);
        tvpri = (TextView) findViewById(R.id.tvprice);
        tvnm = (TextView)findViewById(R.id.tvname);
        btn = (Button)findViewById(R.id.btn_push);






        Bundle extras = getIntent().getExtras();
        final String name = extras.getString("Food Item");
        tvnm.setText(name);
        ref = FirebaseDatabase.getInstance().getReference().child("FOOD").child(name).child("Price");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                 price = dataSnapshot.getValue(String.class);
                tvpri.setText(price);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String quantity = qt.getText().toString().trim();

                int p = Integer.valueOf(price);

                int q = Integer.valueOf(quantity);

                total = p * q;



                String tot = String.valueOf(total);

                if(ChoiceActivity.flagtk[0]!=1) {
                    ref1 = FirebaseDatabase.getInstance().getReference().child("BILL").child(DineActivity.name);
                    HashMap<String, String> datamap = new HashMap<>();
                    datamap.put("Name", name);
                    datamap.put("Price", price);
                    datamap.put("Quantity", quantity);
                    datamap.put("Total", tot);
                    datamap.put("Table_no", DineActivity.name);

                    ref1.push().setValue(datamap);
                }
                else {
                    ref2 = FirebaseDatabase.getInstance().getReference().child("BILL").child(ChoiceActivity.nametk);
                    HashMap<String, String> datamap11 = new HashMap<>();
                    datamap11.put("Name", name);
                    datamap11.put("Price", price);
                    datamap11.put("Quantity", quantity);
                    datamap11.put("Total", tot);
                    datamap11.put("Table_no", ChoiceActivity.nametk);


                    ref2.push().setValue(datamap11);
                }

                Intent i = new Intent(Bill.this,MainActivity.class);
                startActivity(i);

            }
        });

    }

}


/*
public void onClick(View v) {
    final String quantity = qt.getText().toString().trim();

    int p = Integer.valueOf(price);

    int q = Integer.valueOf(quantity);

    total = p * q;








    Intent i = new Intent(Bill.this,MainActivity.class);
    startActivity(i);

}
});
*/
