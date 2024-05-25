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

import com.example.gotcha.Models.Category;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
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

        initViews();
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
}