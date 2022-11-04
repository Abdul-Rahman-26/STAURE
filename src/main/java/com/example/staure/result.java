package com.example.staure;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.example.staures.R;


import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.firebase.database.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;

public class result extends AppCompatActivity {
    public WebView webview;
    MenuItem save;
    String FolderName;
    //for get permission
    private  static final int PERMISSION_REQUEST_CODE = 7;
    private static final int REQUEST_EXTERNAL_STORAGe = 1;
    private static final String[] permissionstorage = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public  String Dpath;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.savemenu,menu);
        return true;
    }
//Implement the web View
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        save=findViewById(R.id.save);
        verifystoragepermissions(this);
        FolderName="STAURE";
        WebView webview =findViewById(R.id.webview);
        webview.setWebViewClient(new WebViewClient());
        webview.loadUrl("http://coe1.annauniv.edu/home/");
        //webview.getSettings().setDomStorageEnabled(true);
        WebSettings webSettings=webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        //The string get from alertDialogue box
        //String regnum=getIntent().getStringExtra("rkey");
        //String dofb=getIntent().getStringExtra("dkey");
    }
    public class webclient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view,String url){
            view.loadUrl(url);
            return true;
        }
    }
//to back
    @Override
    public void onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack();
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.save:{
                //Screenshot code
                if (ContextCompat.checkSelfPermission(result.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                createDirectory(FolderName);
                Toast.makeText(result.this,"saved",Toast.LENGTH_SHORT).show();

                return true;
                }
                else {

                askPermission();
            }}
            case R.id.ab: {
                AlertDialog.Builder builder = new AlertDialog.Builder(result.this);
                builder.setMessage("Are you really want to upload the files?")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(result.this, topostindrive.class));
                                finish();

                            }
                        })
                        .setNegativeButton("Cancel", null);
                AlertDialog alert = builder.create();
                alert.show();
                return true;

            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void askPermission() {
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {

        if (requestCode == PERMISSION_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createDirectory(FolderName);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    public void createDirectory(String folderName) {
        java.io.File file = new java.io.File(Environment.getExternalStorageDirectory(),folderName);
        screenshot(getWindow().getDecorView().getRootView(), "STAURE");

        if (!file.exists()){
            file.mkdir();
            Toast.makeText(result.this,"Successful",Toast.LENGTH_SHORT).show();}

    }


    public static  File screenshot(View view, String filename) {
        Date date = new Date();
        // Here we are initialising the format of our image name
        CharSequence format = android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", date);
        try {
            // Initialising the directory of storage
            String dirpath = Environment.getExternalStorageDirectory() + "/STAURE";
            final java.io.File file=new java.io.File(dirpath);
            file.mkdir();
            if (!file.exists()) {
                boolean mkdir = file.mkdir();
            }
            // File name
            String fpath = dirpath + "/" + filename + "-" + format + ".JPEG";
            String Dpath=fpath;
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            java.io.File imageurl = new java.io.File(fpath);
            FileOutputStream outputStream = new FileOutputStream(imageurl);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            outputStream.flush();
            outputStream.close();
           return imageurl;

        } catch (FileNotFoundException io) {
            io.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void verifystoragepermissions(Activity activity) {
        int permissions = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        // If storage permission is not given then request for External Storage Permission
        if (permissions != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, permissionstorage, REQUEST_EXTERNAL_STORAGe);
        }
    }

    }