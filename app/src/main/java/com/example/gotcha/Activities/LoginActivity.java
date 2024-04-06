package com.example.gotcha.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.User;
import com.example.gotcha.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private MaterialButton login_BTN_signOut;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseRef = mDatabase.getReference();
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ggg", "------------START------------ (func: onCreate)");

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        findviews();
        initViews();

        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        if(firebaseUser == null){
            //no user is logged in
            Log.d("ggg", "no user is logged in (func: onCreate)");
            login();
        }else{
            //user already logged in
            Log.d("ggg", "user already logged in (func: onCreate)");
            loadLoggedInUser();
        }
        Log.d("ggg", "User: " + firebaseUser.getEmail() + " (func: onCreate)");
       //initCurrentUser();
        checkIfUserInDatabase();

        goToMainActivity();

    }

    private void initCurrentUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getPhotoUrl().toString());
        if(currentUser != null){
            CurrentUser.getInstance().setUserProfile(user);
        }
    }

    private boolean checkIfUserInDatabase() {
        //get current user
        //check if his uid is in the DB
        //if true : return true
        //if false : add him to the DB and return


        //createNewUserInDatabase();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        User user = new User(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getPhotoUrl().toString());
        CurrentUser.getInstance().setUserProfile(user);
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");
        databaseRef.child(firebaseUser.getUid()).setValue(user);
        return true;
    }


    private void logOutUser() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }


    private void initViews() {
        login_BTN_signOut.setOnClickListener(v -> {
                logOutUser();
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void goToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void findviews() {
        login_BTN_signOut = findViewById(R.id.login_BTN_signOut);

    }

    private void login() {

        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setLogo(R.drawable.app_icon_1)
                .build();
        signInLauncher.launch(signInIntent);
    }

    private void loadLoggedInUser() {
        //TODO



    }


    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            new ActivityResultCallback<FirebaseAuthUIAuthenticationResult>() {
                @Override
                public void onActivityResult(FirebaseAuthUIAuthenticationResult result) {
                    onSignInResult(result);
                }
            }
    );

    private void onSignInResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            Log.d("ggg", "Successfully signed in (func: onSignInResult)");
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            //getUserDataFromFirebase();
            goToMainActivity();

        } else {
            Log.d("ggg", "Error in Sign in (func: onSignInResult)");
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}