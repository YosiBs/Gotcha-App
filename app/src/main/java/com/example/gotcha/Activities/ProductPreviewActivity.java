package com.example.gotcha.Activities;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.gotcha.Fragments.HomeFragment;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
import com.example.gotcha.R;
import com.example.gotcha.databinding.ActivityProductPreviewBinding;

import java.util.ArrayList;

public class ProductPreviewActivity extends AppCompatActivity {


    private ActivityProductPreviewBinding binding;
    private String currentSerialNumber;
    private Product currentProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityProductPreviewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        currentSerialNumber = getSerialNumberFromIntent();
        currentProduct = getCurrentProduct(currentSerialNumber);
        Log.d("fff", "PPP= "+ currentProduct);

        initViews();


    }

    private Product getCurrentProduct(String serialNumber) {
        ArrayList<Product> productList = CurrentUser.getInstance().getUserProfile().getProductList();
        for (Product product : productList) {
            if (product.getSerialNumber().equals(serialNumber)) {
                return product; // Return the product if found
            }
        }
        return null; // Return null if no product with the given serialNumber is found
    }


    private void initViews() {
        ImageView backArrow = findViewById(R.id.back_arrow_icon);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!currentProduct.getImageUriString().isEmpty()) {
            Uri imageUri = Uri.parse(currentProduct.getImageUriString());
            Log.d("fff", "ifff\nimageUri = " + imageUri);

            Glide.with(this)
                    .load(currentProduct.getImageUriString())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.image_placeholder) // Placeholder image while loading
                            .error(R.drawable.no_photo) // Error image if loading fails
                            .diskCacheStrategy(DiskCacheStrategy.ALL)) // Cache image to improve performance
                    .into(binding.ppIMGProduct);
        }else{
            Log.d("fff", "elseeee");
            binding.ppIMGProduct.setImageResource(R.drawable.image_placeholder);
        }

        binding.ppName.setText(currentProduct.getProductName());
        binding.ppCost.setText(String.valueOf(currentProduct.getPrice()));
        binding.ppSerialNumber.setText(currentProduct.getSerialNumber());
        if(currentProduct.getCategory() != Product.CategoryType.NA){
            binding.ppCategory.setText(String.valueOf(currentProduct.getCategory()));
        }else{
            binding.ppCategory.setText("-");
        }
        if(currentProduct.getPurchaseDate() != null){
            binding.ppPurchaseDate.setText(String.valueOf(currentProduct.getPurchaseDate()));
        }else{
            binding.ppPurchaseDate.setText("-");
        }
        if(!currentProduct.getNotes().isEmpty()){
            binding.ppNotes.setText(String.valueOf(currentProduct.getNotes()));
        }else{
            binding.ppNotes.setText("-");
        }
        binding.ppNotes.setText(String.valueOf(currentProduct.getNotes()));
        Log.d("fff", "SSS= "+ currentSerialNumber);

        //Warranty:
        if(currentProduct.isHasWarranty()){

            binding.ppWProvider.setText(String.valueOf(currentProduct.getWarranty().getWarrantyProvider()));
            binding.ppWIDNumber.setText(String.valueOf(currentProduct.getWarranty().getWarrantyNumber()));
            binding.ppWStartDate.setText(String.valueOf(currentProduct.getWarranty().getStartDate()));
            binding.ppWEndDate.setText(String.valueOf(currentProduct.getWarranty().getEndDate()));
            binding.ppWCoverage.setText(String.valueOf(currentProduct.getWarranty().getCoverageDetails()));
            binding.ppWContact.setText(String.valueOf(currentProduct.getWarranty().getWarrantyContact()));
        }else{
            binding.ppWarrantyCaption.setText("Warranty (Not Provided)");
            binding.ppWProvider.setText("-");
            binding.ppWIDNumber.setText("-");
            binding.ppWStartDate.setText("-");
            binding.ppWEndDate.setText("-");
            binding.ppWCoverage.setText("-");
            binding.ppWContact.setText("-");
        }





    }
    private String getSerialNumberFromIntent() {
        // Check if the intent that started this activity contains the serial number extra
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("serial_number")) {
            // Retrieve the serial number from the intent extras
            return intent.getStringExtra("serial_number");
        } else {
            // Handle case where serial number extra is not found
            return null;
        }
    }


}