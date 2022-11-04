package com.example.staure;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.staures.R;

public class dmenu extends AppCompatDialogFragment {
    public EditText regno;
    public EditText dob;
    private dialogListener Listener;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View view=inflater.inflate(R.layout.alert,null);
        builder.setView(view)
                .setTitle("Add Details")
                //get back the alertDialog
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                //to save the data which was given in the alert dialogue
                .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String rollno=regno.getText().toString();
                        String date=dob.getText().toString();
                        Intent intent = new Intent(dmenu.this.getActivity(), result.class);
                        intent.putExtra("rkey",rollno);
                        intent.putExtra("dkey",date);
                        startActivity(intent);
                    }

                    });

        regno=view.findViewById(R.id.regno);
        dob=view.findViewById(R.id.date);

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            Listener=(dialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"enter");
        }
    }

    public interface dialogListener{
        void applyTexts(String rollno,String dob);
    }
}
