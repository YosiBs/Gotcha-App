package com.example.gotcha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gotcha.Interfaces.CallBack_Category;
import com.example.gotcha.Interfaces.CallBack_Product;
import com.example.gotcha.Models.Category;
import com.example.gotcha.Models.Product;
import com.example.gotcha.R;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private Context context;
    private ArrayList<Category> categories;
    private CallBack_Category categoryCallback;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
        this.context = context;
        this.categories = categories;

    }

    public CategoryAdapter setCategoryCallback(CallBack_Category categoryCallback) {
        this.categoryCallback = categoryCallback;
        return this;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_catagory_item, parent, false);
        CategoryViewHolder categoryViewHolder = new CategoryViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = getItem(position);
        holder.category_CARD_circle_dot.setImageResource(getCategoryColor(category.getType()));
        holder.category_LBL_name.setText(String.valueOf(category.getType()));
        holder.category_Card_counter.setText(String.valueOf(category.getProductCount()));


    }

    @Override
    public int getItemCount() {
        return categories == null ? 0 : categories.size();
    }
    private Category getItem(int position) {
        return categories.get(position);
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        private CardView category_CARD_data ;
        private CircleImageView category_CARD_circle_dot;
        private MaterialTextView category_LBL_name;
        private MaterialTextView category_Card_counter;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            category_CARD_data = itemView.findViewById(R.id.category_CARD_data);
            category_CARD_circle_dot = itemView.findViewById(R.id.category_CARD_circle_dot);
            category_LBL_name = itemView.findViewById(R.id.category_LBL_name);
            category_Card_counter = itemView.findViewById(R.id.category_Card_counter);
            category_CARD_data.setOnClickListener(v->{
                if(categoryCallback != null){
                    categoryCallback.categoryCardClicked(getItem(getAdapterPosition()), getAdapterPosition());
                }

            });

        }
    }

    private int getCategoryColor(Product.CategoryType type) {
        switch (type) {
            case ELECTRONICS:
                return R.color.electronics;
            case APPLIANCES:
                return R.color.appliances;
            case CLOTHING:
                return R.color.clothing;
            case FURNITURE:
                return R.color.furniture;
            case SPORTING_GOODS:
                return R.color.sporting_goods;
            case TOYS:
                return R.color.toys;
            case BEAUTY:
                return R.color.beauty;
            case BOOKS:
                return R.color.books;
            case JEWELRY:
                return R.color.jewelry;
            case AUTOMOTIVE:
                return R.color.automotive;
            case HOME_AND_GARDEN:
                return R.color.home_and_garden;
            case HEALTH_AND_WELLNESS:
                return R.color.health_and_wellness;
            case OFFICE_SUPPLIES:
                return R.color.office_supplies;
            case FOOD_AND_DRINK:
                return R.color.food_and_drink;
            case OTHER:
                return R.color.other;
            default:
                return R.color.grey;
        }
    }



}
