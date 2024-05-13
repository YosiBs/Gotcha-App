package com.example.gotcha.Activities;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcha.Adapters.ProductAdapter;
import com.example.gotcha.Fragments.AddItemFragment;
import com.example.gotcha.Fragments.CategoriesFragment;
import com.example.gotcha.Fragments.HomeFragment;
import com.example.gotcha.Interfaces.CallBack_Product;
import com.example.gotcha.Logics.FirebaseManager;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
import com.example.gotcha.Models.User;
import com.example.gotcha.R;
import com.example.gotcha.Fragments.SettingsFragment;
import com.example.gotcha.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });
        //init CurrentUser:
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if(currentUser != null){
            User user = new User(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getPhotoUrl().toString());
            CurrentUser.getInstance().setUserProfile(user);
        }
        FirebaseManager.getInstance().loadUserDetails(CurrentUser.getInstance().getUid(), new FirebaseManager.OnUserLoadListener() {
            @Override
            public void onUserLoaded(User user) {

            }

            @Override
            public void onUserLoadFailed(String errorMessage) {

            }
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.home){
                replaceFragment(new HomeFragment());
            }else if(item.getItemId() == R.id.categories){
                replaceFragment(new CategoriesFragment());
            }else if(item.getItemId() == R.id.add_item){
                replaceFragment(new AddItemFragment());
            }else{
                replaceFragment(new SettingsFragment());
            }
            return true;
        });

    }
    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager= getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout , fragment);
        fragmentTransaction.commit();
    }



}