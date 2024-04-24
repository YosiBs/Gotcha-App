package com.example.gotcha.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.gotcha.R;
import com.example.gotcha.databinding.ActivityMainBinding;
import com.example.gotcha.databinding.FragmentAddItemBinding;

public class AddItemFragment extends Fragment {

    private FragmentAddItemBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);

        binding = FragmentAddItemBinding.inflate(inflater, container, false);

        findViews(view);
        initCategoryDropBox();

        return binding.getRoot();
    }

    private void findViews(View view) {
        //TODO
    }

    private void initCategoryDropBox() {
        final String[] categories = getResources().getStringArray(R.array.categories);
        final ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, categories);
        binding.autoCompleteTextView.setAdapter(arrayAdapter);

    }
}