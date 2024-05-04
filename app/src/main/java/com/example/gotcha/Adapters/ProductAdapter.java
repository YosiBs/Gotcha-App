package com.example.gotcha.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcha.Interfaces.CallBack_Product;
import com.example.gotcha.Models.Product;
import com.example.gotcha.R;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
// Import Glide library
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context context;
    private ArrayList<Product> products;
    private CallBack_Product productCallback;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }
    public ProductAdapter setProductCallback(CallBack_Product productCallback) {
        this.productCallback = productCallback;
        return this;
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_product_item, parent,false);
        ProductViewHolder playerViewHolder = new ProductViewHolder(view);
        return playerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = getItem(position);

        if(!product.getImageUriString().isEmpty()){
            Uri imageUri = Uri.parse(product.getImageUriString());
            Log.d("ddd", "imageUri = " + imageUri);

            Glide.with(context)
                    .load(product.getImageUriString())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.image_placeholder) // Placeholder image while loading
                            .error(R.drawable.no_photo) // Error image if loading fails
                            .diskCacheStrategy(DiskCacheStrategy.ALL)) // Cache image to improve performance
                    .into(holder.product_IMG);

        }else{
            holder.product_IMG.setImageResource(R.drawable.image_placeholder);
        }
        holder.product_LBL_name.setText(product.getProductName());
        holder.product_LBL_cost.setText("Price: " + String.valueOf(product.getPrice()));



        if(product.isHasWarranty()){
            holder.PB_exp_time.setMax((int)product.getWarranty().getWarrantyLength());
            LocalDate currentDate = LocalDate.now();
            int deltaWarrantyLen =  (int)product.getWarranty().calcWarrantyReminder(currentDate);
            holder.PB_exp_time.setProgress(deltaWarrantyLen, true);
            holder.product_LBL_warranty_exp_date.setText("Warranty Exp Date: " + product.getWarranty().getEndDate() + " (" + deltaWarrantyLen + " Days)");
        }else{
            holder.PB_exp_time.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }
    private Product getItem(int position){
        return products.get(position);
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private CardView product_CARD_data ;
        private ShapeableImageView product_IMG;
        private MaterialTextView product_LBL_name;
        private MaterialTextView product_LBL_cost;
        private MaterialTextView product_LBL_warranty_exp_date;
        private ProgressBar PB_exp_time;


        public ProductViewHolder(@NonNull View itemView){
            super(itemView);

            product_CARD_data = itemView.findViewById(R.id.product_CARD_data);
            product_IMG = itemView.findViewById(R.id.product_IMG);
            product_LBL_name = itemView.findViewById(R.id.product_LBL_name);
            product_LBL_cost = itemView.findViewById(R.id.product_LBL_cost);
            product_LBL_warranty_exp_date = itemView.findViewById(R.id.product_LBL_warranty_exp_date);
            PB_exp_time = (ProgressBar)itemView.findViewById(R.id.PB_exp_time);

            product_CARD_data.setOnClickListener(v -> {

                if(productCallback!=null){
                    productCallback.productPreviewClicked(getItem(0),"AAA");
                }

            });



        }

    }



}
