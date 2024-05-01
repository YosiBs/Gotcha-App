package com.example.gotcha.Models;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gotcha.Logics.FirebaseManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CurrentUser {

    private static CurrentUser currentUser = null;
    private User userProfile = null;
    private FirebaseUser user;

    private CurrentUser(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            FirebaseManager.getInstance().checkUserExistence(user.getUid(), new FirebaseManager.OnUserExistenceListener() {
                @Override
                public void onUserExists(boolean exists) {
                    if (exists) {
                        FirebaseManager.getInstance().loadUserDetails(user.getUid(), new FirebaseManager.OnUserLoadListener() {
                            @Override
                            public void onUserLoaded(User user) {
                                Log.d("ddd", "User Loaded");
                            }

                            @Override
                            public void onUserLoadFailed(String errorMessage) {
                                Log.d("ddd", "User Failed to Load");

                            }
                        });
                    } else {
                        // User does not exist in the database
                        Log.d(TAG, "User does not exist in the database");
                        userProfile = new User(user.getUid(), user.getDisplayName(), String.valueOf(user.getPhotoUrl()));
                        FirebaseManager.getInstance().addNewUser(user.getUid(), userProfile, new FirebaseManager.OnUserAddListener() {
                            @Override
                            public void onUserAdded() {
                                // User added successfully
                                Log.d(TAG, "User added to the database");
                            }

                            @Override
                            public void onUserExists() {
                                // User already exists in the database
                                Log.d(TAG, "User already exists in the database");
                            }

                            @Override
                            public void onUserAddFailed(String errorMessage) {
                                // Error occurred while adding user
                                Log.e(TAG, "Error adding user: " + errorMessage);
                            }
                        });
                    }
                }
            });
        }
    }

    public static CurrentUser getInstance(){
        if(currentUser == null){
            currentUser = new CurrentUser();
        }
        return currentUser;
    }

    public User getUserProfile(){
        return userProfile;
    }

    public CurrentUser setUserProfile(User userProfile) {
        this.userProfile = userProfile;
        return this;
    }

    public String getUid() {
        if(getUserProfile() != null)
            return getUserProfile().getUid();
        return null;
    }

    @Override
    public String toString() {
        return "CurrentUser{" +
                " \nuserProfile=" + userProfile +
                ",\n user=" + user +
                "\n}";
    }



}
