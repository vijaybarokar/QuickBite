package com.example.finalyearproject.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.R;

import java.util.List;

public class PopularDishAdapter extends RecyclerView.Adapter<PopularDishAdapter.PopularDishViewHolder> {

    Context context;
    List<DishModel> dishList;

    public PopularDishAdapter(Context context, List<DishModel> dishList) {
        this.context = context;
        this.dishList = dishList;
    }

    @NonNull
    @Override
    public PopularDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popular_dish, parent, false);
        return new PopularDishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularDishViewHolder holder, int position) {
        DishModel dish = dishList.get(position);
        holder.tvDishName.setText(dish.getName());

        Glide.with(context)
                .load(dish.getImageUrl())
                .placeholder(R.drawable.categoryloading)
                .into(holder.ivDishImage);
    }

    @Override
    public int getItemCount() {
        return dishList.size();
    }

    public class PopularDishViewHolder extends RecyclerView.ViewHolder {
        ImageView ivDishImage;
        TextView tvDishName;

        public PopularDishViewHolder(@NonNull View itemView) {
            super(itemView);
            ivDishImage = itemView.findViewById(R.id.ivDishImage);
            tvDishName = itemView.findViewById(R.id.tvDishName);
        }
    }
}
