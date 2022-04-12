package com.example.android.hammersystemsapp.data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.hammersystemsapp.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private ArrayList<CategoryItem> categoryItems;
    private int rowIndex = -1;

    public CategoryAdapter(Context context, ArrayList<CategoryItem> categoryItems) {
        this.context = context;
        this.categoryItems = categoryItems;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        CategoryItem currentCategoryItem = categoryItems.get(position);
        holder.categoryTextView.setText(currentCategoryItem.getCategory());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowIndex = position;
                notifyDataSetChanged();
            }
        });

        if (rowIndex == position) {
            holder.categoryTextView.setTextColor(Color.parseColor("#FD3A69"));
            holder.categoryTextView.setBackgroundColor(Color.parseColor("#F4CDD6"));
        } else {
            holder.categoryTextView.setTextColor(Color.parseColor("#A3A3A3"));
            holder.categoryTextView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        return categoryItems.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        TextView categoryTextView;
        CardView cardView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryTextView = itemView.findViewById(R.id.categoryTextView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
