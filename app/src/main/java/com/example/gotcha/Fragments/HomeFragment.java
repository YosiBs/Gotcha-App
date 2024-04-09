package com.example.gotcha.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.User;
import com.example.gotcha.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {


    private ShapeableImageView image_user_profile;

    private MaterialTextView frag_text_hello;



    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        setupHomeFragment();
        Log.d("ggg", "In HomeFragment");

        return view;
    }

    private void setupHomeFragment() {
        loadUserFromDB();

    }

    private void loadUserFromDB() {
        FirebaseDatabase database= FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("Users").child(CurrentUser.getInstance().getUid());

        // Add a listener to retrieve the user data
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Check if the user data exists
                if (dataSnapshot.exists()) {
                    // Retrieve user data
                    User user = dataSnapshot.getValue(User.class);

                    // Do something with the user object (e.g., display user details)
                    if (user != null) {
                        // Access other user properties as needed
                        changeUserNameUI(CurrentUser.getInstance().getUserProfile().getName());


                    }
                } else {
                    Log.d("ggg", "User not found");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Log.e("FirebaseError", "Error retrieving user: " + databaseError.getMessage());
            }
        });

    }

    private void findViews(View view) {
        image_user_profile = view.findViewById(R.id.image_user_profile);
        frag_text_hello = view.findViewById(R.id.frag_text_hello);
    }

    private void changeUserNameUI(String fullName) {
        String[] firstName = fullName.split(" ");
        frag_text_hello.setText("Hello " + firstName[0]);
    }
    private void changeUserImageUI(String Url) {
        //TODO
    }

}