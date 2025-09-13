package com.example.finalyearproject.admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.common.OrderModel;

import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<OrderModel> orderList;
    private Context context;

    public OrdersAdapter(List<OrderModel> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_admin, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = orderList.get(position);

        holder.tvDishName.setText(order.getDishName());
        holder.tvPrice.setText("₹" + order.getDishPrice());
        holder.tvEmail.setText("Email: " + order.getUserEmail());
        holder.tvPhone.setText("Phone: " + order.getUserPhone());
        holder.tvLocation.setText("Location: " + order.getUserLocation());
        holder.tvPaymentId.setText("Payment ID: " + order.getPaymentId());
        holder.tvTime.setText("Time: " + order.getOrderTime());
        holder.tvStatus.setText("Status: " + order.getStatus());
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView tvDishName, tvPrice, tvEmail, tvPhone, tvLocation, tvPaymentId, tvTime, tvStatus;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvPaymentId = itemView.findViewById(R.id.tvPaymentId);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
    }
}
