package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.common.MyOrderData;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OrderFoodNowActivity extends AppCompatActivity implements PaymentResultListener {

    TextView txtDishName, txtDishPrice;
    ImageView dishImage;
    Button btnConfirmOrder;

    String name, price, imageUrl;
    String userEmail, userPhone, userLocation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food_now);

        txtDishName = findViewById(R.id.txtDishName);
        txtDishPrice = findViewById(R.id.txtDishPrice);
        dishImage = findViewById(R.id.dishImage);
        btnConfirmOrder = findViewById(R.id.btnConfirmOrder);

        // ✅ Get dish details
        name = getIntent().getStringExtra("dishname");
        price = getIntent().getStringExtra("dishprice");
        imageUrl = getIntent().getStringExtra("dishimage");

        txtDishName.setText(name);
        txtDishPrice.setText("₹" + price);
        Glide.with(this).load(com.example.finalyearproject.common.Urls.getImages + imageUrl).into(dishImage);

        // ✅ Get user details from SharedPreferences
        SharedPreferences prefs = getSharedPreferences("UserData", MODE_PRIVATE);
        userEmail = prefs.getString("email", "testuser@gmail.com");
        userPhone = prefs.getString("phone", "9876543210");
        userLocation = prefs.getString("location", "Unknown Location");

        btnConfirmOrder.setOnClickListener(v -> startRazorpayPayment(price));
    }

    // ✅ Start Razorpay payment
    private void startRazorpayPayment(String amount) {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_kiNlUmG1LXG8el");  // 🔑 Replace with your Razorpay Test Key
        checkout.setImage(R.drawable.app_logo_without_bg);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Food Delivery");
            options.put("description", "Payment for " + name);
            options.put("currency", "INR");

            double finalAmount = Double.parseDouble(amount) * 100;
            options.put("amount", finalAmount);

            // ✅ Dynamic user details
            JSONObject prefill = new JSONObject();
            prefill.put("email", userEmail);
            prefill.put("contact", userPhone);
            options.put("prefill", prefill);

            // ✅ Notes for test
            JSONObject notes = new JSONObject();
            notes.put("Test UPI", "Use success@razorpay to simulate success");
            options.put("notes", notes);

            checkout.open(activity, options);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_LONG).show();

        // ✅ Save locally
        MyOrderData.addOrder(name, price, imageUrl);

        // ✅ Send order to server
        sendOrderToServer(razorpayPaymentID, name, price, userEmail, userPhone, userLocation);

        // ✅ Show feedback dialog
        showRatingDialog();
    }

    @Override
    public void onPaymentError(int code, String response) {
        Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_LONG).show();
    }

    // ✅ Send order details to PHP Webservice
    private void sendOrderToServer(String paymentID, String dishName, String dishPrice,
                                   String email, String phone, String location) {
        new Thread(() -> {
            try {
                URL url = new URL("https://yourserver.com/save_order.php"); // 🔄 Replace with your server URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                String postData = "payment_id=" + URLEncoder.encode(paymentID, "UTF-8") +
                        "&dish_name=" + URLEncoder.encode(dishName, "UTF-8") +
                        "&dish_price=" + URLEncoder.encode(dishPrice, "UTF-8") +
                        "&email=" + URLEncoder.encode(email, "UTF-8") +
                        "&phone=" + URLEncoder.encode(phone, "UTF-8") +
                        "&location=" + URLEncoder.encode(location, "UTF-8");

                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                writer.write(postData);
                writer.flush();
                writer.close();

                int responseCode = conn.getResponseCode();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();

                String serverResponse = sb.toString();

                runOnUiThread(() -> {
                    if (responseCode == 200) {
                        Toast.makeText(this, "Order saved: " + serverResponse, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Failed to save order!", Toast.LENGTH_LONG).show();
                    }
                });

            } catch (Exception e) {
                runOnUiThread(() ->
                        Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
            }
        }).start();
    }

    // ✅ Rating Dialog
    private void showRatingDialog() {
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_feedback, null);
        RatingBar ratingBar = dialogView.findViewById(R.id.feedbackRatingBar);
        TextView tvMessage = dialogView.findViewById(R.id.tvRatingMessage);

        ratingBar.setOnRatingBarChangeListener((rb, rating, fromUser) -> {
            if (rating == 5) tvMessage.setText("Awesome! 🌟");
            else if (rating >= 4) tvMessage.setText("Great!");
            else if (rating >= 3) tvMessage.setText("Good");
            else if (rating >= 2) tvMessage.setText("Could be better");
            else tvMessage.setText("We'll improve it!");
        });

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setPositiveButton("Submit", null)
                .setCancelable(false)
                .create();

        dialog.setOnShowListener(dlg -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(btn -> {
                float rating = ratingBar.getRating();
                if (rating == 0) {
                    Toast.makeText(this, "Please rate your experience!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Thanks for " + (int) rating + "★", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(OrderFoodNowActivity.this, HomeActivity.class));
                    finish();
                    dialog.dismiss();
                }
            });
        });

        dialog.show();
    }
}
