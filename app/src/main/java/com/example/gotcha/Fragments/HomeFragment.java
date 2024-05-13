package com.example.gotcha.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gotcha.Activities.MainActivity;
import com.example.gotcha.Activities.ProductPreviewActivity;
import com.example.gotcha.Adapters.ProductAdapter;
import com.example.gotcha.Interfaces.CallBack_Product;
import com.example.gotcha.Logics.FirebaseManager;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
import com.example.gotcha.Models.User;
import com.example.gotcha.R;
import com.example.gotcha.databinding.FragmentHomeBinding;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        loadProductList();


    }

    @Override
    public void onPause() {
        super.onPause();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        initViews();

        return binding.getRoot();
    }

    private void initViews() {
        changeUserNameUI(CurrentUser.getInstance().getUserProfile().getName());
        changeUserImageUI(CurrentUser.getInstance().getUserProfile().getImage());

    }

    private void itemClicked(String serialNumber) {
        goToProductPreviewActivity(serialNumber);
    }

    private void goToProductPreviewActivity(String serialNumber) {
        // Create an Intent to switch to the ProductPreviewActivity
        Intent intent = new Intent(getContext(), ProductPreviewActivity.class);
        // Pass the serial number of the product to the ProductPreviewActivity
        intent.putExtra("serial_number", serialNumber);
        // Start the ProductPreviewActivity
        startActivity(intent);
    }

    public void loadProductList() {
        //CycleView: Product List
        ProductAdapter productAdapter = new ProductAdapter(this.getContext(), CurrentUser.getInstance().getUserProfile().getProductList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.homeLSTProducts.setLayoutManager(linearLayoutManager);
        binding.homeLSTProducts.setAdapter(productAdapter);
        productAdapter.setProductCallback(new CallBack_Product() {
            @Override
            public void productPreviewClicked(Product product, int position) {
                itemClicked(product.getSerialNumber());
            }
        });
    }


    private void changeUserNameUI(String fullName) {
        String[] firstName = fullName.split(" ");
        binding.homeFragTextUserName.setText("Hello " + firstName[0]);
    }
    private void changeUserImageUI(String imageUrl) {
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.image_placeholder) // Placeholder image while loading
                .error(R.drawable.loading_error_image) // Error image if loading fails
                .into(binding.profileImage);
    }

}