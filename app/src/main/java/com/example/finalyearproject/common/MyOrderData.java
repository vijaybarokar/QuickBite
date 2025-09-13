package com.example.finalyearproject.common;

import static com.example.finalyearproject.common.Urls.webserviceAddress;
import static com.example.finalyearproject.common.Urls.webserviceAddressPort;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyOrderData {

    private static List<OrderModel> myOrders = new ArrayList<>();

    public static List<OrderModel> getMyOrders() {
        return myOrders;
    }

    public static void addOrder(String dishName, String dishPrice, String imageUrl) {
        long now = System.currentTimeMillis();
        myOrders.add(new OrderModel(dishName, dishPrice, imageUrl, now));
    }

    public static void removeExpiredOrders() {
        List<OrderModel> valid = new ArrayList<>();
        for (OrderModel order : myOrders) {
            if (!order.isExpired()) {
                valid.add(order);
            }
        }
        myOrders = valid;
    }

    public static List<OrderModel> getActiveOrders() {
        List<OrderModel> active = new ArrayList<>();
        for (OrderModel order : myOrders) {
            if (!order.isExpired()) {
                active.add(order);
            }
        }
        return active;
    }

    // ✅ New: Fetch orders from server for current user
    public static void fetchOrdersFromServer(Context context, String userEmail, Runnable onComplete) {
        String url = webserviceAddress +webserviceAddressPort+"get_user_orders.php?email=" + userEmail;

        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        myOrders.clear();

                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject obj = arr.getJSONObject(i);
                            myOrders.add(new OrderModel(
                                    obj.getString("dish_name"),
                                    obj.getString("dish_price"),
                                    obj.getString("image_url"),
                                    System.currentTimeMillis()
                            ));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // ✅ Call callback on UI thread
                    new Handler(Looper.getMainLooper()).post(onComplete);
                },
                error -> new Handler(Looper.getMainLooper()).post(onComplete)
        );

        queue.add(request);
    }
}
