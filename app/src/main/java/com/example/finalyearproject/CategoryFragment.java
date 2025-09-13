package com.example.finalyearproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

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

public class CategoryFragment extends Fragment {

    SearchView searchcategory;

    ListView lvShowAllCategory;
    TextView tvNoCategoryAvailable;

    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails;
    AdapterGetAllCategoryDetails adapterGetAllCategoryDetails;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        pojoGetAllCategoryDetails = new ArrayList<>();
        searchcategory = view.findViewById(R.id.svcategoryfragmentsearchcategory);
        lvShowAllCategory = view.findViewById(R.id.lvcategoryShowMultipleCategory);
        tvNoCategoryAvailable = view.findViewById(R.id.tvcategoryNoCategoryAvailable);

        searchcategory.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchcategory(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchcategory(query);
                return false;
            }
        });

        getAllCategory();

        return view;
    }

    private void searchcategory(String query) {

        List<POJOGetAllCategoryDetails> tempcategory = new ArrayList<>();
        tempcategory.clear();

        for (POJOGetAllCategoryDetails obj:pojoGetAllCategoryDetails)
        {
            if (obj.getCategoryName().toUpperCase().contains(query.toUpperCase()))
            {
                tempcategory.add(obj);
            }
            else
            {
                tvNoCategoryAvailable.setVisibility(View.VISIBLE);
            }

            adapterGetAllCategoryDetails = new AdapterGetAllCategoryDetails(tempcategory,
                    getActivity());
            lvShowAllCategory.setAdapter(adapterGetAllCategoryDetails);


        }
    }

    private void getAllCategory() {
        //client server communication passing datamover network
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams(); //put the data
        client.post(Urls.getAllcategoryDetailswebservice,
                params,
                new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            JSONArray jsonArray = response.getJSONArray("getAllcategory");
                            if (jsonArray.isNull(0))
                            {
                                tvNoCategoryAvailable.setVisibility(View.VISIBLE);
                            }

                            for (int i=0;i<jsonArray.length();i++)
                            {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strId = jsonObject.getString("id");
                                String strcategoryImage = jsonObject.getString("categoryimage");
                                String strcategoryName = jsonObject.getString("categoryname");

                                pojoGetAllCategoryDetails.add(new POJOGetAllCategoryDetails(strId,strcategoryImage,strcategoryName));
                            }

                            adapterGetAllCategoryDetails = new AdapterGetAllCategoryDetails(pojoGetAllCategoryDetails,getActivity());

                            lvShowAllCategory.setAdapter(adapterGetAllCategoryDetails);

                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Toast.makeText(getActivity(), "Server Error", Toast.LENGTH_SHORT).show();
                    }
                }

                );

    }
}