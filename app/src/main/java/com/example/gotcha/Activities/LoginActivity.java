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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.units.qual.Current;

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
        //1. get current user
        //2. check if the user is in the DB
        //3. if not : add him to the DB and return


        // 1
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            //2
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference usersRef = database.getReference("Users").child(currentUser.getUid());

            // Check if the user's UID exists in the "Users" node
            usersRef.child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // User exists in the database
                        // You can perform any necessary actions here
                        Log.d("Database", "User exists in the database");

                        // Optionally, you can retrieve and process user data here
                        // User user = dataSnapshot.getValue(User.class);
                    } else {
                        // User does not exist in the database
                        // Add the user to the database
                        //3
                        createNewUser();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle potential error
                    Log.e("FirebaseError", "Error checking user data: " + databaseError.getMessage());
                }
            });

        }
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

        databaseRef = FirebaseDatabase.getInstance().getReference("Users");
        DatabaseReference userRef = databaseRef.child(firebaseUser.getUid());
        int x = 2;
        // Add a listener to retrieve the user data
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                // Check if the user data exists
                if (dataSnapshot.exists()) {

                    // Retrieve user data
                    User user = dataSnapshot.getValue(User.class);
                    // Do something with the user object (e.g., display user details)
                    if (user != null) {
                        CurrentUser.getInstance().setUserProfile(user);
                    }
                } else {
                    Log.d("UserDetails", "User not found");
                    createNewUser();
                }
                goToMainActivity();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e("FirebaseError", "Error retrieving user: " + databaseError.getMessage());
            }
        });



    }

    private void createNewUser() {
        User newUser = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(),firebaseUser.getPhotoUrl().toString());
        CurrentUser.getInstance().setUserProfile(newUser);
        databaseRef = FirebaseDatabase.getInstance().getReference("Users");
        databaseRef.child(firebaseUser.getUid()).setValue(newUser);
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