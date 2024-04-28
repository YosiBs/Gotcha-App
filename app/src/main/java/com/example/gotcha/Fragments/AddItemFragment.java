package com.example.gotcha.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.gotcha.Models.Product;
import com.example.gotcha.R;
import com.example.gotcha.databinding.ActivityMainBinding;
import com.example.gotcha.databinding.FragmentAddItemBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AddItemFragment extends Fragment {

    private FragmentAddItemBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        initCategoryDropBox();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_item, container, false);
        binding = FragmentAddItemBinding.inflate(inflater, container, false);

        initViews(view);
        return binding.getRoot();
    }



    private void initViews(View view) {
        binding.BtnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(binding.dateText);
            }
        });
        binding.BtnPickStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(binding.startDateText);
            }
        });
        binding.BtnPickEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePickerDialog(binding.endDateText);
            }
        });
        binding.hasWarrantyCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Show the form elements
                    binding.formWarrantyProvider.setVisibility(View.VISIBLE);
                    binding.formWarrantyStartDate.setVisibility(View.VISIBLE);
                    binding.formWarrantyEndDate.setVisibility(View.VISIBLE);
                    binding.formCoverageDetails.setVisibility(View.VISIBLE);
                    binding.formWarrantyIDNumber.setVisibility(View.VISIBLE);
                    binding.formWarrantyProviderNumber.setVisibility(View.VISIBLE);
                } else {
                    // Hide the form elements
                    binding.formWarrantyProvider.setVisibility(View.GONE);
                    binding.formWarrantyStartDate.setVisibility(View.GONE);
                    binding.formWarrantyEndDate.setVisibility(View.GONE);
                    binding.formCoverageDetails.setVisibility(View.GONE);
                    binding.formWarrantyIDNumber.setVisibility(View.GONE);
                    binding.formWarrantyProviderNumber.setVisibility(View.GONE);
                }
            }
        });
        binding.BTNsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

    }

    private void submitForm() {
        createNewProduct();
        // add product to productList (array)
        // update Database
        // go to product preview
    }

    private void createNewProduct() {
        Product newProduct = new Product();


//<!--ITEM NAME-->
        String itemName = String.valueOf(binding.formItemName.getText().toString().trim());
        if (itemName.isEmpty()) {
            binding.formItemName.setError("Item name is required");
        } else {
            binding.formItemName.setError(null); // Clear error if field is filled
            // Proceed with form submission
            newProduct.setProductName(itemName);
            Log.d("ddd", "Item Name: " + itemName);
        }

        newProduct.getWarranty().setStartDate(new Date(2023, 5,1));
        newProduct.getWarranty().setEndDate(new Date(2024, 5,1));
        newProduct.getWarranty().calcWarrantyLenInDays();


    }

    private void initCategoryDropBox() {
        final String[] categories = getResources().getStringArray(R.array.categories);
        final ArrayAdapter<CharSequence> arrayAdapter = new ArrayAdapter<>(requireContext(), R.layout.dropdown_item, categories);
        binding.autoCompleteTextView.setAdapter(arrayAdapter);
    }
    private void openDatePickerDialog(final TextView textView){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                textView.setText(String.valueOf(dayOfMonth)+"."+String.valueOf(month+1)+"."+String.valueOf(year));
            }
        }, currentYear, currentMonth, currentDay);
        datePickerDialog.show();

    }



}