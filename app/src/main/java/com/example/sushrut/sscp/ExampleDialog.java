package com.example.sushrut.sscp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class ExampleDialog extends AppCompatDialogFragment {
    private EditText mEditText;




    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_layout,null);

       // Bundle extras = getIntent().getExtras();
        //String name = extras.getString("Food Item");
     //   Toast.makeText(this, "" + name , Toast.LENGTH_SHORT).show();

        builder.setView(view);
        builder.setTitle("Food Items");
        builder.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }

        });
        mEditText = view.findViewById(R.id.edit_noitem);
        return builder.create();
    }
}
