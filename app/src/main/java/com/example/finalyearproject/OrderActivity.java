package com.example.finalyearproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        ImageView dishImage = findViewById(R.id.dishImage);
        TextView dishTitle = findViewById(R.id.dishTitle);

        // Get data from intent
        int imageId = getIntent().getIntExtra("image_id", 0);
        String title = getIntent().getStringExtra("title");

        dishImage.setImageResource(imageId);
        dishTitle.setText(title);
    }
}
