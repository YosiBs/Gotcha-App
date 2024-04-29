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
import android.widget.Toast;

import com.example.gotcha.Logics.FirebaseManager;
import com.example.gotcha.Models.CurrentUser;
import com.example.gotcha.Models.Product;
import com.example.gotcha.R;
import com.example.gotcha.databinding.ActivityMainBinding;
import com.example.gotcha.databinding.FragmentAddItemBinding;
import com.google.android.material.textfield.TextInputEditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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
                openDatePickerDialog(binding.purchaseDateText);
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
        Product product = createNewProduct();

        // update Database
        FirebaseManager firebaseManager = FirebaseManager.getInstance();
        // go to product preview
        String userId = CurrentUser.getInstance().getUserProfile().getUid();
        firebaseManager.addNewProduct(userId, product);

    }

    private Product createNewProduct() {
        Product newProduct = new Product();

        boolean areProductEssentialsFilled = false;
        boolean areWarrantyEssentialsFilled = false;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//<!--ITEM NAME-->
        String itemName = String.valueOf(binding.formItemName.getText().toString().trim());
        if (itemName.isEmpty()) {
            binding.formItemName.setError("Item name is required");
        } else {
            binding.formItemName.setError(null); // Clear error if field is filled
            // Proceed with form submission
            newProduct.setProductName(itemName);
        }
//<!--ITEM Serial Number-->
        String itemSerialNumber = String.valueOf(binding.formSerialNumber.getText().toString().trim());
        if (itemSerialNumber.isEmpty()) {
            binding.formSerialNumber.setError("Item Serial Number is required");
        } else {
            binding.formSerialNumber.setError(null); // Clear error if field is filled
            // Proceed with form submission
            newProduct.setSerialNumber(itemSerialNumber);
        }
//<!--ITEM Cost-->
        String priceString = binding.formItemPrice.getText().toString();
        double price = 0.0; // Default value if parsing fails
        try {
            if(!priceString.isEmpty()){
                price = Double.parseDouble(priceString);
                newProduct.setPrice(price);
            }
        } catch (NumberFormatException e) {
            binding.formItemPrice.setError("Item price should be x.y");
        }
//<!--ITEM Category-->
        String chosenCategory = binding.autoCompleteTextView.getText().toString();
        if(!chosenCategory.isEmpty()){
            newProduct.setCategory(Product.CategoryType.valueOf(chosenCategory));
        }
//<!--ITEM Purchase Date-->
        String purchaseDateText = binding.purchaseDateText.getText().toString();
        if(!purchaseDateText.isEmpty()){
            LocalDate purchaseDate = LocalDate.parse(purchaseDateText, formatter);
            newProduct.setPurchaseDate(purchaseDate);
        }

//<!--ITEM Notes-->
        String notes = String.valueOf(binding.formItemNotes.getText().toString().trim());
        if (notes.isEmpty()) {
            notes = "N/A";
        }
        newProduct.setNotes(notes);
//<!--ITEM Warranty CheckBox-->
        newProduct.setHasWarranty(binding.hasWarrantyCheckBox.isChecked());
// Check if any of the essential fields are empty
        areProductEssentialsFilled = !itemName.isEmpty() && !itemSerialNumber.isEmpty();

//<!--===================== ITEM Warranty CheckBox ===================-->
        if(newProduct.isHasWarranty()){
            //Has Warranty
//<!--Warranty Provider-->
            String warrantyProvider = String.valueOf(binding.formWarrantyProviderText.getText().toString().trim());
            if (warrantyProvider.isEmpty()) {
                binding.formWarrantyProviderText.setError("Warranty Provider Name is required");
            } else {
                binding.formWarrantyProviderText.setError(null); // Clear error if field is filled
                newProduct.getWarranty().setWarrantyProvider(warrantyProvider);
            }
//<!--Warranty Start Date-->
            String startDateText = binding.startDateText.getText().toString();
            if(!startDateText.isEmpty()){
                LocalDate startDate = LocalDate.parse(startDateText, formatter);
                newProduct.getWarranty().setStartDate(startDate);
            }
//<!--Warranty End Date-->
            String endDateText = binding.endDateText.getText().toString();
            if(!endDateText.isEmpty()){
                LocalDate endDate = LocalDate.parse(endDateText, formatter);
                newProduct.getWarranty().setEndDate(endDate);
            }
//<!--Warranty Remaining Length in Days-->
            if(newProduct.getWarranty().getEndDate() != null){
                newProduct.getWarranty().setWarrantyLength(newProduct.getWarranty().calcWarrantyLenInDays());
            }
//<!--Warranty Coverage Details-->
            String coverageDetails = String.valueOf(binding.formCoverageDetailsText.getText().toString().trim());
            if (coverageDetails.isEmpty()) {
                coverageDetails = "N/A";
            }
            newProduct.getWarranty().setCoverageDetails(coverageDetails);
//<!--Warranty ID Number-->
            String warrantyIdNumber = String.valueOf(binding.formWarrantyIDNumberText.getText().toString().trim());
            if (warrantyIdNumber.isEmpty()) {
                binding.formWarrantyIDNumberText.setError("Warranty ID Number is required");
            } else {
                binding.formWarrantyIDNumberText.setError(null); // Clear error if field is filled
                // Proceed with form submission
                newProduct.getWarranty().setWarrantyNumber(warrantyIdNumber);
            }
//<!--Warranty Provider Phone Number-->
            String warrantyProviderPhoneNumber = String.valueOf(binding.formWarrantyProviderNumberText.getText().toString().trim());
            if (warrantyProviderPhoneNumber.isEmpty()) {
                warrantyProviderPhoneNumber = "N/A";
            }
            newProduct.getWarranty().setWarrantyContact(warrantyProviderPhoneNumber);

            areWarrantyEssentialsFilled = !warrantyProvider.isEmpty() && !warrantyIdNumber.isEmpty();
        }//if Has Warranty

        if (!areProductEssentialsFilled || (newProduct.isHasWarranty() && !areWarrantyEssentialsFilled)) {
            // Show error message or toast indicating that all essential fields must be filled
            Toast.makeText(requireContext(), "Please fill all essential fields", Toast.LENGTH_SHORT).show();
        } else {
            CurrentUser.getInstance().getUserProfile().getProductList().add(newProduct);
            Log.d("ddd", "Output:\n" + newProduct.toString());
            Log.d("ddd", "Output2:\n" + CurrentUser.getInstance().getUserProfile().getProductList().toString());
        }

        return newProduct;
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
                String formattedDay = (dayOfMonth < 10) ? "0" + dayOfMonth : String.valueOf(dayOfMonth);
                String formattedMonth = ((month + 1) < 10) ? "0" + (month + 1) : String.valueOf(month + 1);
                textView.setText(String.valueOf(formattedDay)+"-"+String.valueOf(formattedMonth)+"-"+String.valueOf(year));
            }
        }, currentYear, currentMonth, currentDay);
        datePickerDialog.show();
    }



}