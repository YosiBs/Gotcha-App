package com.example.gotcha.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.gotcha.Activities.LoginActivity;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.R;
import com.example.gotcha.databinding.ActivityMainBinding;
import com.example.gotcha.databinding.FragmentHomeBinding;
import com.example.gotcha.databinding.FragmentSettingsBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;


public class SettingsFragment extends Fragment {

    private FragmentSettingsBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false);

        initViews();


        return binding.getRoot();
    }


    private void initViews() {
        changeUserNameUI(CurrentUser.getInstance().getUserProfile().getName());
        changeUserImageUI(CurrentUser.getInstance().getUserProfile().getImage());

    }



    private void changeUserImageUI(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder) // Placeholder image while loading
                .error(R.drawable.loading_error_image) // Error image if loading fails
                .into(binding.profileImage);
    }
    private void changeUserNameUI(String fullName) {
        binding.userNameTag.setText(fullName);
    }
}