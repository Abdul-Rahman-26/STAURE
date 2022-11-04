package com.example.staure;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.staures.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import java.util.Collections;

public class stature extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//Used to add the buttons wants login into firebase using authentication
        final ImageView signinbtn = findViewById(R.id.signinbtn);


        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        GoogleSignInClient googleSignInClient= GoogleSignIn.getClient(this,googleSignInOptions);

        GoogleSignInAccount googleSignInAccount=GoogleSignIn.getLastSignedInAccount(this);

        //checking the user already signed in
        if(googleSignInAccount!=null){

            //open home
            startActivity(new Intent(stature.this,home.class));
            finish();
        }
        ActivityResultLauncher<Intent>activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {


            @Override


            public void onActivityResult(ActivityResult result) {
                //getting sign in account after user selected an account google accounts dialog
                Task<GoogleSignInAccount>task=GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInTask(task);
            }
        });

        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent signInIntent=googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);

            }
        });
    }


    private void handleSignInTask(Task<GoogleSignInAccount>task){
        try {
            GoogleSignInAccount account=task.getResult(ApiException.class);

            //getting account data
            final String getFullname=account.getDisplayName();
            final String geyEmail=account.getEmail();
            final Uri getPhotoUrl=account.getPhotoUrl();
            //opening home
            startActivity(new Intent(stature.this,home.class));
            finish();

        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this,"LOGIN FAILED",Toast.LENGTH_SHORT).show();
                    }

    }
}