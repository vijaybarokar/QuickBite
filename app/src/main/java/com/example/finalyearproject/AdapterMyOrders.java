package com.example.finalyearproject;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.common.OrderModel;
import com.example.finalyearproject.common.Urls;

import java.util.List;

public class AdapterMyOrders extends BaseAdapter {

    Context context;
    List<OrderModel> orderList;
    LayoutInflater inflater;

    public AdapterMyOrders(Context context, List<OrderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView imgDish;
        TextView txtName, txtPrice, txtTimer;
        CountDownTimer timer;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            view = inflater.inflate(R.layout.item_my_order, parent, false);
            holder = new ViewHolder();
            holder.imgDish = view.findViewById(R.id.imgDish);
            holder.txtName = view.findViewById(R.id.txtDishName);
            holder.txtPrice = view.findViewById(R.id.txtDishPrice);
            holder.txtTimer = view.findViewById(R.id.txtTimer);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
            if (holder.timer != null) {
                holder.timer.cancel();
            }
        }

        OrderModel order = orderList.get(position);

        holder.txtName.setText(order.dishName);
        holder.txtPrice.setText("₹" + order.dishPrice);
        Glide.with(context).load(Urls.getImages + order.imageUrl).into(holder.imgDish);

        long remaining = order.getRemainingMillis();

        holder.timer = new CountDownTimer(remaining, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long mins = (millisUntilFinished / 1000) / 60;
                long secs = (millisUntilFinished / 1000) % 60;
                holder.txtTimer.setText(String.format("Arriving in %02d:%02d", mins, secs));
            }

            @Override
            public void onFinish() {
                holder.txtTimer.setText("Arrived");
            }
        }.start();

        return view;
    }
}
