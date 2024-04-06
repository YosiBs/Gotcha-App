package com.example.gotcha.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;


public class HomeFragment extends Fragment {


    private ShapeableImageView image_user_profile;

    private MaterialTextView frag_text_hello;
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
        setUserhello();
        Log.d("ggg", "In HomeFragment");


        return view;
    }

    private void findViews(View view) {
        image_user_profile = view.findViewById(R.id.image_user_profile);
        frag_text_hello = view.findViewById(R.id.frag_text_hello);
    }

    private void setUserhello() {
        CurrentUser currentUser = CurrentUser.getInstance();
        Log.d("ggg", "In HomeFragment func: setUserhello");
        String name = currentUser.getUserProfile().getName();
        Log.d("ggg", "name : " + name);

        //frag_text_hello.setText(currentUser.getUserProfile().getName());
    }
}