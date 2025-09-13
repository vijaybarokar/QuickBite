package com.example.finalyearproject;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.example.finalyearproject.common.Urls;

import java.util.List;

public class AdapterGetAllCategoryDetails extends BaseAdapter {

    List<POJOGetAllCategoryDetails> pojoGetAllCategoryDetails;
    Activity activity;

    public AdapterGetAllCategoryDetails(List<POJOGetAllCategoryDetails> list, Activity activity) {
        this.pojoGetAllCategoryDetails = list;
        this.activity = activity;
    }

    @Override
    public int getCount() {

        return pojoGetAllCategoryDetails.size();
    }

    @Override
    public Object getItem(int position) {

        return pojoGetAllCategoryDetails.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (view==null)
        {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.rv_get_all_category,null);
            holder.ivcategoryimage = view.findViewById(R.id.ivcategoryimage);
            holder.tvcategoryname = view.findViewById(R.id.tvcategoryname);
            holder.cvcategorylist = view.findViewById(R.id.cvcategorylist);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();

        }

        final POJOGetAllCategoryDetails obj = pojoGetAllCategoryDetails.get(position);

        holder.tvcategoryname.setText(obj.getCategoryName());


        Glide.with(activity)
                .load(Urls.getImages+obj.getCategoryImage())
                .skipMemoryCache(true)
                .error(R.drawable.image_not_found)
                .into(holder.ivcategoryimage);

        holder.cvcategorylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, CategoryWiseDishActivity.class);
                i.putExtra("categoryname",obj.getCategoryName());
                activity.startActivity(i);
            }
        });



        return view;
    }

    class ViewHolder
    {

        CardView cvcategorylist;
        ImageView ivcategoryimage;
        TextView tvcategoryname;
    }

    //baseAdapter = multiple view load show


}
