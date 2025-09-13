package com.example.finalyearproject.common;

public class OrderModel {
    public String id, dishName, dishPrice, userEmail, userPhone, userLocation, paymentId, orderTime, status, imageUrl;
    public long timestamp; // for local cache expiry check

    // ✅ Constructor for Admin Orders (9 parameters)
    public OrderModel(String id, String dishName, String dishPrice,
                      String userEmail, String userPhone, String userLocation,
                      String paymentId, String orderTime, String status) {
        this.id = id;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.userLocation = userLocation;
        this.paymentId = paymentId;
        this.orderTime = orderTime;
        this.status = status;
    }

    // ✅ Constructor for MyOrderData (Local Orders)
    public OrderModel(String dishName, String dishPrice, String imageUrl, long timestamp) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
    }

    // ✅ Check if order expired (24h)
    public boolean isExpired() {
        long now = System.currentTimeMillis();
        long twentyFourHours = 24 * 60 * 60 * 1000;
        return (now - timestamp) > twentyFourHours;
    }

    // ✅ Remaining time for countdown (fixed 30 min delivery window)
    public long getRemainingMillis() {
        long deliveryWindow = 30 * 60 * 1000; // 30 minutes in milliseconds
        long endTime = timestamp + deliveryWindow;
        long now = System.currentTimeMillis();
        return Math.max(endTime - now, 0);
    }

    // ------------------- ✅ Add these getters -------------------
    public String getId() { return id; }
    public String getDishName() { return dishName; }
    public String getDishPrice() { return dishPrice; }
    public String getUserEmail() { return userEmail; }
    public String getUserPhone() { return userPhone; }
    public String getUserLocation() { return userLocation; }
    public String getPaymentId() { return paymentId; }
    public String getOrderTime() { return orderTime; }
    public String getStatus() { return status; }
    public String getImageUrl() { return imageUrl; }
    public long getTimestamp() { return timestamp; }
}
