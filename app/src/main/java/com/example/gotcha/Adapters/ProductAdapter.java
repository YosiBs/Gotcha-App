package com.example.gotcha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcha.Interfaces.CallBack_Product;
import com.example.gotcha.Models.Product;
import com.example.gotcha.R;
import com.example.gotcha.databinding.HorizontalProductItemBinding;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

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
        /*
        holder.player_LBL_rank.setText("#"+String.valueOf(position + 1));
        holder.player_LBL_name.setText(player.getName());
        holder.player_LBL_score.setText(String.valueOf(player.getScore()));
         */


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
        /*
        private MaterialTextView player_LBL_name;
        private MaterialTextView player_LBL_score;
        private ShapeableImageView player_IMG_location;

         */

        public ProductViewHolder(@NonNull View itemView){
            super(itemView);

            product_CARD_data = itemView.findViewById(R.id.product_CARD_data);
            /*
            player_LBL_rank = itemView.findViewById(R.id.player_LBL_rank);
            player_LBL_name = itemView.findViewById(R.id.player_LBL_name);
            player_LBL_score = itemView.findViewById(R.id.player_LBL_score);
            player_IMG_location = itemView.findViewById(R.id.player_IMG_location);
            */
            product_CARD_data.setOnClickListener(v -> {

                if(productCallback!=null){
                    productCallback.productPreviewClicked(getItem(0),"AAA");
                }

            });



        }

    }



}
