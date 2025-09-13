package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class HomeFragment1 extends Fragment {

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home1, container, false);

        // 🔹 Special Offers Slider
        ViewFlipper viewFlipper = view.findViewById(R.id.imageFlipper);
        int[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};

        for (int i = 0; i < images.length; i++) {
            final int index = i;
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(images[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // 👉 Click listener for slider images
            imageView.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("image_id", images[index]);
                intent.putExtra("title", "Special Offer " + (index + 1));
                startActivity(intent);
            });

            viewFlipper.addView(imageView);
        }

        // 🔹 Trending Now Section (Quick links)
        LinearLayout trendingLayout = view.findViewById(R.id.trendingLayout);
        String[] trendingNames = {"Summer Specials", "Best Sellers", "Chef's Picks"};
        int[] trendingIcons = {
                android.R.drawable.ic_menu_compass,
                android.R.drawable.ic_dialog_dialer,
                android.R.drawable.ic_menu_camera
        };

       // int[] trendingIcons = {R.drawable.summer_special, R.drawable.best_seller, R.drawable.chef_pick};

        for (int i = 0; i < trendingNames.length; i++) {
            View itemView = inflater.inflate(R.layout.item_trending, trendingLayout, false);
            ImageView icon = itemView.findViewById(R.id.trendingIcon);
            TextView title = itemView.findViewById(R.id.trendingTitle);

            icon.setImageResource(trendingIcons[i]);
            title.setText(trendingNames[i]);

            int index = i;
            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra("title", trendingNames[index]);
                startActivity(intent);
            });

            trendingLayout.addView(itemView);
        }

        return view;
    }
}


//package com.example.finalyearproject;
//
//import android.annotation.SuppressLint;
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.ViewFlipper;
//
//public class HomeFragment1 extends Fragment {
//
//    @SuppressLint("MissingInflatedId")
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout
//        View view = inflater.inflate(R.layout.fragment_home1, container, false);
//
//        // 🔹 Offers Slider
//        ViewFlipper viewFlipper = view.findViewById(R.id.imageFlipper);
//        int[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
//
//        for (int i = 0; i < images.length; i++) {
//            final int index = i;
//            ImageView imageView = new ImageView(getContext());
//            imageView.setImageResource(images[i]);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            // 👉 Click to open OrderActivity
//            imageView.setOnClickListener(v -> {
//                Intent intent = new Intent(getContext(), OrderActivity.class);
//                intent.putExtra("image_id", images[index]);
//                intent.putExtra("title", "Special Offer " + (index + 1));
//                startActivity(intent);
//            });
//
//            viewFlipper.addView(imageView);
//        }
//
//        // 🔹 Popular Dishes
//        ViewFlipper dishFlipper = view.findViewById(R.id.dishFlapper);
//        int[] dishImages = {R.drawable.choco_icecream, R.drawable.butter_cake, R.drawable.cheese_cake, R.drawable.maggy};
//        String[] dishNames = {"Choco Ice Cream", "Butter Cake", "Cheese Cake", "Maggi"};
//
//        for (int i = 0; i < dishImages.length; i++) {
//            final int index = i;
//            ImageView imageView = new ImageView(getContext());
//            imageView.setImageResource(dishImages[i]);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            // 👉 Click to open OrderActivity
//            imageView.setOnClickListener(v -> {
//                Intent intent = new Intent(getContext(), OrderActivity.class);
//                intent.putExtra("image_id", dishImages[index]);
//                intent.putExtra("title", dishNames[index]);
//                startActivity(intent);
//            });
//
//            dishFlipper.addView(imageView);
//        }
//
//        return view;
//    }
//}
