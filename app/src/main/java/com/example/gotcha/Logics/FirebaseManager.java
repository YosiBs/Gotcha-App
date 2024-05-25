package com.example.gotcha.Logics;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.gotcha.Models.Category;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
import com.example.gotcha.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseManager {
    private static final String USERS_NODE = "Users";
    private static final String PRODUCTS_NODE = "Products";
    private static final String CATEGORIES_NODE = "categoryList";

    private static FirebaseManager instance;
    private final DatabaseReference databaseReference;

    private FirebaseManager() {
        // Initialize Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
    }

    public static synchronized FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }


    // Example method to add a product to the database
    public void addNewProduct(String userId, Product product) {
        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(userId);
        DatabaseReference productsRef = userRef.child(PRODUCTS_NODE);
        String productId = product.getSerialNumber();
        if (productId != null) {
            productsRef.child(productId).setValue(product);
        }
    }


    // Example method to update a product in the database
    public void updateProduct(String userId, String productId, Product updatedProduct) {
        DatabaseReference productRef = databaseReference.child(USERS_NODE)
                .child(userId)
                .child(PRODUCTS_NODE)
                .child(productId);
        productRef.setValue(updatedProduct);
    }

    public void deleteProduct(String userId, String productId) {
        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(userId);
        DatabaseReference productsRef = userRef.child(PRODUCTS_NODE);
        // Remove the product with the given productId
        productsRef.child(productId).removeValue();
    }



    public void addCategory(String userId, String categoryId, Category category) {
        DatabaseReference categoryRef = databaseReference.child(USERS_NODE)
                .child(userId)
                .child(CATEGORIES_NODE)
                .child(categoryId);
        categoryRef.setValue(category);
    }

    public void removeCategory(String userId, String categoryId) {
        DatabaseReference categoryRef = databaseReference.child(USERS_NODE)
                .child(userId)
                .child(CATEGORIES_NODE)
                .child(categoryId);
        categoryRef.removeValue();
    }

    public void addProductToCategory(String userId, String categoryId, Product product) {
        DatabaseReference categoryRef = databaseReference.child(USERS_NODE)
                .child(userId)
                .child(CATEGORIES_NODE)
                .child(categoryId)
                .child(PRODUCTS_NODE);
        String productId = product.getSerialNumber();
        if (productId != null) {
            categoryRef.child(productId).setValue(product);
        }
    }












    public void checkUserExistence(String userId, final OnUserExistenceListener listener) {

        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(userId);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists in the database
                    listener.onUserExists(true);
                } else {
                    // User does not exist in the database
                    listener.onUserExists(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Log.e(TAG, "Error checking user existence: " + databaseError.getMessage());
            }
        });
    }
    // Interface to handle user existence result
    public interface OnUserExistenceListener {
        void onUserExists(boolean exists);
    }
    public void loadUserDetails(String userId, OnUserLoadListener listener) {
        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // User exists, parse the data and pass it to the listener
                    User user = dataSnapshot.getValue(User.class);
                    CurrentUser.getInstance().setUserProfile(user);

                    // Check if the user has productList node
                    if (dataSnapshot.hasChild(PRODUCTS_NODE)) {
                        // User has productList, retrieve it
                        ArrayList<Product> productList = new ArrayList<>();
                        for (DataSnapshot productSnapshot : dataSnapshot.child(PRODUCTS_NODE).getChildren()) {
                            Product product = productSnapshot.getValue(Product.class);
                            productList.add(product);
                        }
                        CurrentUser.getInstance().getUserProfile().setProductList(productList);
                    }
                    listener.onUserLoaded(user);
                } else {
                    // User does not exist
                    listener.onUserLoadFailed("User does not exist in the database");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while loading user details
                listener.onUserLoadFailed(databaseError.getMessage());
            }
        });
    }
    public interface OnUserLoadListener {
        void onUserLoaded(User user);
        void onUserLoadFailed(String errorMessage);
    }
    public void addNewUser(String userId, User user, OnUserAddListener listener) {
        DatabaseReference userRef = databaseReference.child(USERS_NODE).child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    // User does not exist, add new user to the database
                    userRef.setValue(user)
                            .addOnSuccessListener(aVoid -> {
                                // User added successfully
                                listener.onUserAdded();
                            })
                            .addOnFailureListener(e -> {
                                // Error occurred while adding user
                                listener.onUserAddFailed(e.getMessage());
                            });
                } else {
                    // User already exists in the database
                    listener.onUserExists();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Error occurred while checking user existence
                listener.onUserAddFailed(databaseError.getMessage());
            }
        });
    }
    public interface OnUserAddListener {
        void onUserAdded();
        void onUserExists();
        void onUserAddFailed(String errorMessage);
    }


}
