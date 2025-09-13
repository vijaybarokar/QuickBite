package com.example.finalyearproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    boolean doubletap = false;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    HomeFragment1 homeFragment1 = new HomeFragment1();
    CategoryFragment categoryFragment = new CategoryFragment();
    MyOrderFragment myOrderFragment = new MyOrderFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Quick Bite");

        bottomNavigationView = findViewById(R.id.homeBottomNevigationMenuHome);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        boolean openMyOrderWithFeedback = getIntent().getBooleanExtra("openMyOrderWithFeedback", false);

        if (openMyOrderWithFeedback) {
            // ✅ Load MyOrderFragment with feedback argument
            MyOrderFragment fragment = new MyOrderFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean("showFeedback", true);
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeFrameLayout, fragment)
                    .commit();

            bottomNavigationView.setSelectedItemId(R.id.homeBottomNevigationMenuMyOrder);
        } else {
            // 👇 Old default load home fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.homeFrameLayout, homeFragment1)
                    .commit();

            bottomNavigationView.setSelectedItemId(R.id.homeBottomNevigationMenuSubHome);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.homeMyQRCode) {
            Intent intent = new Intent(HomeActivity.this, MyQRCodeActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.homeMyoffers) {

            Intent intent = new Intent(HomeActivity.this, MyOffersActivity.class);
            startActivity(intent);

        } else if (item.getItemId() == R.id.homeMyProfile) {
            Intent intent = new Intent(HomeActivity.this, MyProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.homeBottomNevigationMenuSubHome) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, homeFragment1).commit();
        } else if (item.getItemId() == R.id.homeBottomNevigationMenuCategory) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, categoryFragment).commit();
        } else if (item.getItemId() == R.id.homeBottomNevigationMenuMyOrder) {
            getSupportFragmentManager().beginTransaction().replace(R.id.homeFrameLayout, myOrderFragment).commit();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (doubletap) {
            finishAffinity();
        } else {
            Toast.makeText(HomeActivity.this, "press again to exit", Toast.LENGTH_SHORT).show();
            doubletap = true;
            Handler h = new Handler();
            h.postDelayed(() -> doubletap = false, 2000);
        }
    }
}
