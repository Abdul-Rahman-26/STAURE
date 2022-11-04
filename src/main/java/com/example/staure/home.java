package com.example.staure;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.staures.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.File;
import java.util.Scanner;

public class home extends AppCompatActivity implements dmenu.dialogListener{
    ImageButton button;
    Button btn1;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.mainmenu,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button=findViewById(R.id.result);
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                openDialog();

            }

            public void openDialog() {
                dmenu dialog=new dmenu();
                dialog.show(getSupportFragmentManager(),"Info");

            }
        });

        button=findViewById(R.id.saved);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                AlertDialog.Builder bu = new AlertDialog.Builder(home.this);
                bu.setMessage("See you Storage")
                        .setPositiveButton("Local disk", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String files = Environment.getExternalStorageDirectory() + "/STAURE" + "/";
                                Uri uri = Uri.parse(files);
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setDataAndType(uri, "*/*");
                                startActivity(intent);
                            }
                        }).setNegativeButton("Drive", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            String o=Environment.getExternalStorageDirectory()+"/"+"STAURE"+"/";
                            Uri uri= Uri.parse(o);
                            Intent intent=new Intent(Intent.ACTION_PICK);
                            startActivity(intent);
                    }
                });AlertDialog a = bu.create();
                a.show();




            }
        });
    }






    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final AppCompatButton signOutBtn=findViewById(R.id.item1);

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);

        switch (item.getItemId()) {
            case R.id.item1:{

                AlertDialog.Builder bui = new AlertDialog.Builder(home.this);
                bui.setMessage("Are you really want to logout?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                googleSignInClient.signOut();
                                startActivity(new Intent(home.this, stature.class));
                                finish();

                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog al = bui.create();
                al.show();
                return true; }
            }

                return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder=new AlertDialog.Builder(home.this);
        builder.setTitle("Exit")
                .setMessage("Are you want exit?")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        home.super.onBackPressed();
                    }
                })
                .setNegativeButton("Cancel",null);
        AlertDialog alert=builder.create();
        alert.show();
    }

    @Override
    public void applyTexts(String rollno, String dob) {
    }
}