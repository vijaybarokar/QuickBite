package com.example.finalyearproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.finalyearproject.common.MyOrderData;
import com.example.finalyearproject.common.OrderModel;

import java.util.List;

public class MyOrderFragment extends Fragment {

    ListView lvMyOrders;
    TextView tvNoOrders;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_order, container, false);

        lvMyOrders = view.findViewById(R.id.lvMyOrders);
        tvNoOrders = view.findViewById(R.id.tvNoOrders);

        SharedPreferences prefs = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE);
        String userEmail = prefs.getString("email", "testuser@gmail.com");

        tvNoOrders.setVisibility(View.GONE);
        lvMyOrders.setVisibility(View.GONE);

        // ✅ Fetch orders from server
        MyOrderData.fetchOrdersFromServer(getContext(), userEmail, () -> {
            List<OrderModel> orders = MyOrderData.getMyOrders();

            if (orders.isEmpty()) {
                tvNoOrders.setVisibility(View.VISIBLE);
                lvMyOrders.setVisibility(View.GONE);
            } else {
                tvNoOrders.setVisibility(View.GONE);
                lvMyOrders.setVisibility(View.VISIBLE);

                AdapterMyOrders adapter = new AdapterMyOrders(getContext(), orders);
                lvMyOrders.setAdapter(adapter);
            }
        });

        return view;
    }
}
