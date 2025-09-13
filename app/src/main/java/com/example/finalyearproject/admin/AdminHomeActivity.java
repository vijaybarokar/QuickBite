package com.example.finalyearproject.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalyearproject.POJOGetAllCategoryDetails;
import com.example.finalyearproject.R;
import com.example.finalyearproject.admin.AdapterClass.AdapterGetAllCategoryDetailsRV;
import com.example.finalyearproject.common.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    RecyclerView rvgetallcategory;
    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails;
    AdapterGetAllCategoryDetailsRV adapterGetAllCategoryDetailsRV;

    CardView cvAllCustomerLocationInMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        setTitle("Quick Bite Admin");

        Toast.makeText(this, "Admin Home Activity", Toast.LENGTH_SHORT).show();

        rvgetallcategory = findViewById(R.id.rvcategoryShowMultipleCategory);
        rvgetallcategory.setLayoutManager(new GridLayoutManager(AdminHomeActivity.this,2,
                GridLayoutManager.HORIZONTAL,false));

        cvAllCustomerLocationInMap = findViewById(R.id.cvAdminHomeCustomerLocation);
        cvAllCustomerLocationInMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHomeActivity.this, ViewAllCustomerLocationInMapActivity.class);
                startActivity(intent);
            }
        });




        pojoGetAllCategoryDetails = new ArrayList<>();
        adapterGetAllCategoryDetailsRV = new AdapterGetAllCategoryDetailsRV(pojoGetAllCategoryDetails,this);
        rvgetallcategory.setAdapter(adapterGetAllCategoryDetailsRV);


        getAllCategoryRV();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater ();
        inflater.inflate (R.menu.home_menu_admin, menu);

        return true;
    }

    private void getAllCategoryRV() {
        RequestQueue requestQueue = Volley.newRequestQueue(AdminHomeActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Urls.getAllcategoryDetailswebservice,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("getAllcategory");

                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                String strID = jsonObject1.getString("id");
                                String strCategoryimage = jsonObject1.getString("categoryimage");
                                String strCategoryName = jsonObject1.getString("categoryname");

                                pojoGetAllCategoryDetails.add(new POJOGetAllCategoryDetails(strID,strCategoryimage,strCategoryName));
                            }

                            adapterGetAllCategoryDetailsRV.notifyDataSetChanged();





                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AdminHomeActivity.this, "server Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == R.id.homeMyQRCode) {
            Intent intent = new Intent (AdminHomeActivity.this, ScanQRCodeActivity.class);
            startActivity (intent);
        }
        return true;

    }
}

