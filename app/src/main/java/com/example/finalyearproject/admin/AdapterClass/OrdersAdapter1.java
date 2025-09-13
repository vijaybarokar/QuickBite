package com.example.finalyearproject.common;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;

import java.util.List;

public class OrdersAdapter1 extends RecyclerView.Adapter<OrdersAdapter1.OrderVH> {

    private List<OrderModel> orderList;
    private Context context;

    public OrdersAdapter1(List<OrderModel> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {
        OrderModel order = orderList.get(position);
        holder.tvDishName.setText(order.dishName);
        holder.tvPrice.setText("₹" + order.dishPrice);
        holder.tvUser.setText(order.userEmail + " | " + order.userPhone);
        holder.tvLocation.setText(order.userLocation);
        holder.tvTime.setText("Ordered at: " + order.orderTime);
        holder.tvStatus.setText("Status: " + order.status);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    static class OrderVH extends RecyclerView.ViewHolder {
        TextView tvDishName, tvPrice, tvUser, tvLocation, tvTime, tvStatus;

        public OrderVH(@NonNull View itemView) {
            super(itemView);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvUser = itemView.findViewById(R.id.tvUser);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
