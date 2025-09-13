package com.example.finalyearproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.finalyearproject.common.Urls;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CategoryWiseDishActivity extends AppCompatActivity {

    SearchView searchCategoryWiseDish;
    ListView lvcategorywisedish;
    TextView tvnodishavailable;
    AppCompatButton btnOrderNow;

    String strCategoryName;

    List<POJOCategoryWiseDish> pojoCategoryWiseDishList;
    AdapterCategoryWiseDish adapterCategoryWiseDish;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_wise_dish);

        searchCategoryWiseDish = findViewById(R.id.svcategorywisesearchDish);
        lvcategorywisedish = findViewById(R.id.lvCategorywiseDishListofDish);
        tvnodishavailable = findViewById(R.id.tvCategoryWiseDishNoDishAvailable);

        pojoCategoryWiseDishList = new ArrayList<>();

        strCategoryName = getIntent().getStringExtra("categoryname");

        getCategoryWiseDishList();








        searchCategoryWiseDish.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchDishbycategory(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchDishbycategory(query);

                return false;
            }
        });




    }

    private void searchDishbycategory(String query) {
        List<POJOCategoryWiseDish> templist = new ArrayList<>();
        templist.clear();

        for (POJOCategoryWiseDish obj:pojoCategoryWiseDishList) {
            if (obj.getCategoryname().toUpperCase().contains(query.toUpperCase()) ||
                    obj.getDishname().toUpperCase().contains(query.toUpperCase()) ||
                    obj.getRestaurantname().toUpperCase().contains(query.toUpperCase()) ||
                    obj.getDishprice().toUpperCase().contains(query.toUpperCase()) ||
                    obj.getDishcategory().toUpperCase().contains(query.toUpperCase())) {
                templist.add(obj);
            }

            adapterCategoryWiseDish = new AdapterCategoryWiseDish(templist,this);
            lvcategorywisedish.setAdapter(adapterCategoryWiseDish);
        }
    }

    private void getCategoryWiseDishList() {
        AsyncHttpClient client = new AsyncHttpClient(); //client-server communication
        RequestParams params = new RequestParams();  //put the data

        params.put("categoryname",strCategoryName);

        client.post(Urls.getCategoryWiseDish,params,new
                JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        try {
                            JSONArray jsonArray = response.getJSONArray("getCategorywiseDish");
                            if (jsonArray.isNull(0))
                            {
                                lvcategorywisedish.setVisibility(View.GONE);
                                tvnodishavailable.setVisibility(View.VISIBLE);
                            }

                            for (int i=0;i<jsonArray.length();i++)
                            {
                               JSONObject jsonObject = jsonArray.getJSONObject(i);
                               String strid = jsonObject.getString("id");
                               String strcategoryname = jsonObject.getString("categoryname");
                               String strrestaurantname = jsonObject.getString("restaurantname");
                               String strdishcategory = jsonObject.getString("dishcategory");
                               String strdishimage = jsonObject.getString("dishimage");
                               String strdishname = jsonObject.getString("dishname");
                               String strdishprice = jsonObject.getString("dishprice");
                               String strdishrating = jsonObject.getString("dishrating");
                               String strdishoffer = jsonObject.getString("dishoffer");
                               String strdishdiscription = jsonObject.getString("dishdiscription");

                               pojoCategoryWiseDishList.add(new POJOCategoryWiseDish(strid,strcategoryname,
                                       strrestaurantname,strdishcategory,strdishimage,strdishname,
                                       strdishprice,strdishrating,strdishoffer,strdishdiscription));

                            }


                            adapterCategoryWiseDish = new AdapterCategoryWiseDish(pojoCategoryWiseDishList,
                                    CategoryWiseDishActivity.this);

                            lvcategorywisedish.setAdapter(adapterCategoryWiseDish);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(CategoryWiseDishActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}