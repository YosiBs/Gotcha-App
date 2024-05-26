package com.example.gotcha.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcha.Adapters.ProductAdapter;
import com.example.gotcha.Interfaces.CallBack_Product;
import com.example.gotcha.Logics.FirebaseManager;
import com.example.gotcha.Models.Category;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
import com.example.gotcha.Models.User;
import com.example.gotcha.R;
import com.example.gotcha.databinding.ActivityCategoryPreviewBinding;

import java.util.ArrayList;

public class CategoryPreviewActivity extends AppCompatActivity {

    private ActivityCategoryPreviewBinding binding;
    private Product.CategoryType categoryType;
    private Category currCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityCategoryPreviewBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        categoryType = getCategoryTypeFromIntent();
        currCategory = getCurrentCategory(categoryType);
        loadProductList(currCategory);

        initViews();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void initViews() {
        ImageView backArrow = findViewById(R.id.back_arrow_icon);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        TextView category_TITLE_details = findViewById(R.id.category_TITLE_details);
        category_TITLE_details.setText(String.valueOf(currCategory.getType()));

    }

    private Product.CategoryType getCategoryTypeFromIntent() {
        // Check if the intent that started this activity contains the category Type (Enum) extra
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("categoryType")) {
            // Retrieve the category Type (Enum) from the intent extras
            return (Product.CategoryType) intent.getSerializableExtra("categoryType");
        } else {
            // Handle case where category Type (Enum) extra is not found
            return null;
        }
    }

    private Category getCurrentCategory(Product.CategoryType categoryType) {
        ArrayList<Category> categoryList = CurrentUser.getInstance().getUserProfile().getCategoryList();
        for(Category c : categoryList){
            if(c.getType().equals(categoryType)){
                return c;
            }
        }
        return null;
    }

    public void loadProductList(Category currCategory) {
        //CycleView: Product List
        FirebaseManager.getInstance().loadUserDetails(CurrentUser.getInstance().getUid(), new FirebaseManager.OnUserLoadListener() {
            @Override
            public void onUserLoaded(User user) {

            }

            @Override
            public void onUserLoadFailed(String errorMessage) {

            }
        });
        if(currCategory.getProductList() == null){
            throw new RuntimeException("currCategory.getProductList() is null");
        }
        ProductAdapter productAdapter = new ProductAdapter(this, currCategory.getProductList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.categoryLSTProducts.setLayoutManager(linearLayoutManager);
        binding.categoryLSTProducts.setAdapter(productAdapter);
        productAdapter.setProductCallback(new CallBack_Product() {
            @Override
            public void productPreviewClicked(Product product, int position) {
                itemClicked(product.getSerialNumber());
            }
        });

    }
    private void itemClicked(String serialNumber) {
        goToProductPreviewActivity(serialNumber);
    }

    private void goToProductPreviewActivity(String serialNumber) {
        // Create an Intent to switch to the ProductPreviewActivity
        Intent intent = new Intent(this, ProductPreviewActivity.class);
        // Pass the serial number of the product to the ProductPreviewActivity
        intent.putExtra("serial_number", serialNumber);
        // Start the ProductPreviewActivity
        startActivity(intent);
    }
}