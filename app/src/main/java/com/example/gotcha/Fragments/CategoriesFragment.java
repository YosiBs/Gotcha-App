package com.example.gotcha.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gotcha.Activities.CategoryPreviewActivity;
import com.example.gotcha.Activities.ProductPreviewActivity;
import com.example.gotcha.Adapters.CategoryAdapter;
import com.example.gotcha.Adapters.ProductAdapter;
import com.example.gotcha.Interfaces.CallBack_Category;
import com.example.gotcha.Interfaces.CallBack_Product;
import com.example.gotcha.Models.Category;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
import com.example.gotcha.Models.User;
import com.example.gotcha.R;
import com.example.gotcha.databinding.FragmentCategoriesBinding;

import java.util.ArrayList;


public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        initViews();
        Log.d("ddd", "Here 1");
        initCategoryList();
        Log.d("ddd", "Here 2");
        loadCategoryList();
        Log.d("ddd", "Here 3");
        Log.d("ddd", "Here, Category list: " + CurrentUser.getInstance().getUserProfile().getCategoryList());

        return binding.getRoot();
    }

    private void initCategoryList() {
        // Assuming 'currentUser' is an instance of User, properly initialized.
        User currentUser = CurrentUser.getInstance().getUserProfile();

        // Clearing existing categories to avoid duplicates if this method is called multiple times.
        currentUser.getCategoryList().clear();

        // Iterate over all enum values of Product.CategoryType
        for (Product.CategoryType type : Product.CategoryType.values()) {
            // Create a new Category object for each type
            Category newCategory = new Category(type);

            initProductsInCategory(newCategory);

            // Add the new Category object to the categoryList of the currentUser
            currentUser.getCategoryList().add(newCategory);
        }
    }

    private void initProductsInCategory(Category category) {
        // Assuming 'currentUser' is an instance of User, properly initialized.
        User currentUser = CurrentUser.getInstance().getUserProfile();

        // If the category is found, iterate over all products and add matching products to the category's product list
        if (category != null) {
            ArrayList<Product> allProducts = currentUser.getProductList();
            for (Product product : allProducts) {
                if (product.getCategory() == category.getType()) {  // Assuming Product has a getCategoryType method
                    category.getProductList().add(product);
                    category.setProductCount(category.getProductCount() + 1);
                }
            }
        }
    }



    private void initViews() {

    }


    public void loadCategoryList() {
        //CycleView: Category List
        CategoryAdapter categoryAdapter = new CategoryAdapter(this.getContext(), CurrentUser.getInstance().getUserProfile().getCategoryList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        binding.categoriesFragLSTCaterories.setLayoutManager(linearLayoutManager);
        binding.categoriesFragLSTCaterories.setAdapter(categoryAdapter);
        categoryAdapter.setCategoryCallback(new CallBack_Category() {
            @Override
            public void categoryCardClicked(Category category, int position) {
                itemClicked(category.getType());
            }
        });
    }

    private void itemClicked(Product.CategoryType categoryType) {
        goToCategoryPreviewActivity(categoryType);
    }

    private void goToCategoryPreviewActivity(Product.CategoryType categoryType) {
        // Create an Intent to switch to the ProductPreviewActivity
        Intent intent = new Intent(getContext(), CategoryPreviewActivity.class);
        // Pass the serial number of the product to the ProductPreviewActivity
        intent.putExtra("categoryType", categoryType);
        // Start the ProductPreviewActivity
        startActivity(intent);
    }
}