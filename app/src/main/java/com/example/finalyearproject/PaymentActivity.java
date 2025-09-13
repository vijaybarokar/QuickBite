package com.example.finalyearproject;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.finalyearproject.common.Urls;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_payment);


                startPayment();
            }

            private void startPayment() {
                Checkout checkout = new Checkout();
                checkout.setKeyID("rzp_test_kiNlUmG1LXG8elb"); // Your Razorpay Test Key ID

                try {
                    JSONObject options = new JSONObject();
                    options.put("name", "My App");
                    options.put("description", "Order Payment");
                    options.put("currency", "INR");
                    options.put("amount", "5000"); // 5000 paise = ₹50

                    options.put("prefill.email", "test@example.com");
                    options.put("prefill.contact", "9999999999");

                    checkout.open(this, options);
                } catch (Exception e) {
                    Toast.makeText(this, "Error in Payment: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onPaymentSuccess(String razorpayPaymentID) {
                Toast.makeText(this, "Payment Success: " + razorpayPaymentID, Toast.LENGTH_LONG).show();

                // ✅ Send payment ID to PHP backend for verification
                new Thread(() -> {
                    try {
                        URL url = new URL (Urls.Rayorpay); // your XAMPP IP
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setDoOutput(true);

                        String postData = "razorpay_payment_id=" + razorpayPaymentID;
                        conn.getOutputStream().write(postData.getBytes());

                        BufferedReader reader = new BufferedReader(new InputStreamReader (conn.getInputStream()));
                        StringBuilder result = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);
                        }

                        runOnUiThread(() ->
                                Toast.makeText(this, "Server Response: " + result.toString(), Toast.LENGTH_LONG).show());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onPaymentError(int code, String response) {
                Toast.makeText(this, "Payment Failed: " + response, Toast.LENGTH_LONG).show();


    }
}